package simulation;

import java.net.URL;

public class Bus extends CarType{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1111;
	
	public Bus()
	{
		MaxSpeed = 4;
		slowdownTime = 2;
		speedupTime = 2;
		imgWidth = 80;
		imgHeight = 50;
		carWidth = 80;
		
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
