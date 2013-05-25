package simulation;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Car extends JButton implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1111;
	
	private TransparentIcon Ticon;
	private ImageIcon icon;
	private int PositionX;
	private int Destination;
	private int curSpeed;
	private int slow = 0;
	private int speedup = 0;
	
	// state 0: stop, 1: run, -1: crash
	private int State;
	
	protected static int imgWidth = 50, imgHeight = 50;
	protected static int CarWidth = 50;
	protected static int MaxSpeed = 5;
	protected static double slowdownTime = 4;	// unit: timeStamp
	protected static double speedupTime = 4;
	
	public static final int timeStamp = 50; 
	private Highway highway;
	
	public Car(int Destination, Highway highway)
	{
		this.Destination = Destination;
		this.highway = highway;
		try{
			Ticon = new TransparentIcon(getImgURL());
			icon = Ticon.getIcon();
		}catch(Exception e){System.out.println("loading car image error");}
		
		this.setIcon(icon);
		this.setContentAreaFilled(false);
		this.setBorder(null);
		
		curSpeed = 0;
		PositionX = 0;
		State = 1;
		this.setBounds(PositionX, 0, imgWidth, imgHeight);
	}
	public void setPosition(int x)
	{
		PositionX = x;
		this.setBounds(PositionX, 0, imgWidth, imgHeight);
	}
	public int getCarWidth()
	{
		return CarWidth; 
	}
	public void setState(int state)
	{
		this.State = state;
	}
	// need be override
	protected URL getImgURL()
	{
		try{
			return new URL("http://w.csie.org/~b99902022/car.png");
		}catch(Exception e){
			return null;
		}
	}
	protected URL getCrashImgURL()
	{
		try{
			return new URL("http://w.csie.org/~b99902022/crashcar.png");
		}catch(Exception e){
			return null;
		}
	}
	@Override
	public void run() {
		while(PositionX < Destination)
		{
			if(highway.frontCarDistance(PositionX, this) >= 0 && State == 1)
			{
				if(highway.checkCrash(PositionX, this))
				{
					State = -1;
					continue;
				}
				highway.CarRun(this, PositionX, curSpeed);
				if(curSpeed < MaxSpeed)
				{
					speedup ++;
					if(speedup == speedupTime)
					{
						curSpeed += 1;
						speedup = 0;
					}
					slow = 0;
				}
				int frontCarDistance = highway.frontCarDistance(PositionX, this);
				if(frontCarDistance < curSpeed)
				{
					slow ++;
					if(slow == slowdownTime)
					{
						curSpeed -= 1;
						slow = 0;
					}
					speedup = 0;
				}
				this.setBounds(PositionX, 0, imgWidth, imgHeight);
				repaint();
			}else if(State == -1)
			{
				try{
					Ticon = new TransparentIcon(getCrashImgURL());
					icon = Ticon.getIcon();
				}catch(Exception e){System.out.println("loading car crash image error");}
				this.setIcon(icon);
				if(!highway.checkCrash(PositionX, this))
				{
					for(int i=0; i<getCarWidth(); i++)
					{
						highway.fixCrash(i);
						highway.clearState(PositionX, this);
					}
					break;
				}
			}else if(State == 0)
			{
				highway.CarGoOnInterchange(this, PositionX);
			}
			try{
				Thread.sleep(timeStamp);
			}catch(Exception e){}
		}
		this.setVisible(false);
		System.out.println("finish");
	}

}
