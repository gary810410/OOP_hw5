package simulation;

import java.net.URL;

public class Bus extends Car{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1111;
	
	protected static int MaxSpeed = 4;
	protected static double slowdownTime = 2;	// unit: timeStamp
	protected static double speedupTime = 2;
	
	protected int getCarImageWidth()
	{
		return 100;
	}
	protected int getCarImageHeight()
	{
		return 50;
	}
	protected int getCarWidth()
	{
		return 100; 
	}
	protected URL getImgURL()
	{
		try{
			return new URL("http://w.csie.org/~b99902022/images/bus.png");
		}catch(Exception e){
			return null;
		}
	}
	protected URL getCrashImgURL()
	{
		try{
			return new URL("http://w.csie.org/~b99902022/images/crashbus.png");
		}catch(Exception e){
			return null;
		}
	}
}
