package simulation;

import java.net.URL;

public class Car extends CarType{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2222;
	
	public Car()
	{
		MaxSpeed = 8;
		slowdownTime = 1;
		speedupTime = 2;
		imgWidth = 50;
		imgHeight = 50;
		carWidth = 50;
		
	}
	protected URL getImgURL()
	{
		try{
			return new URL("http://w.csie.org/~b99902022/images/car.png");
		}catch(Exception e){
			return null;
		}
	}
	protected URL getCrashImgURL()
	{
		try{
			return new URL("http://w.csie.org/~b99902022/images/crashcar.png");
		}catch(Exception e){
			return null;
		}
	}
}
