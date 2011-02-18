package affrayoeuvre.util;

public final class Utilities {

	public static double AngleToA180(double degree){
		while(degree>180){
			degree-=360;
		}
		
		while(degree<=-180){
			degree+=360;
		}
		return degree;
	}
	
}
