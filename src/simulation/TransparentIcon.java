package simulation;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class TransparentIcon {
	
	private ImageIcon icon;
	
	public TransparentIcon(URL imgurl)
	{
		BufferedImage preImg;
		try{
			preImg = ImageIO.read(imgurl);

		}catch(Exception e){
			preImg = null;
		}
		int target = preImg.getRGB(0, 0);
		Image postImg = makeColorTransparent(preImg, new Color(target));
		BufferedImage transparentImage = imageToBufferedImage(postImg);
		icon = new ImageIcon(transparentImage);
	}
	public ImageIcon getIcon()
	{
		return icon;
	}
	public static Image makeColorTransparent(final BufferedImage im, final Color color)  
	{  
		final ImageFilter filter = new RGBImageFilter()  
		{  
			// the color we are looking for (white)... Alpha bits are set to opaque  
			public int markerRGB = color.getRGB() | 0xFFFFFFFF;  
			public final int filterRGB(final int x, final int y, final int rgb)  
			{  
				if ((rgb | 0xFF000000) == markerRGB)  
				{  
					// Mark the alpha bits as zero - transparent  
					return 0x00000000 & rgb;  
				}  
				else  
				{  
					// nothing to do  
					return rgb;  
				}  
			}  
		};  
	  
		final ImageProducer ip = new FilteredImageSource(im.getSource(), filter);  
		return Toolkit.getDefaultToolkit().createImage(ip);
	}
	private static BufferedImage imageToBufferedImage(final Image image)  
	{  
		final BufferedImage bufferedImage =  
				new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);  
		final Graphics2D g2 = bufferedImage.createGraphics();  
		g2.drawImage(image, 0, 0, null);  
		g2.dispose();  
		return bufferedImage;  
	} 
}

