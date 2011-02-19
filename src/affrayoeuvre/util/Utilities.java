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
	
	
	public static double getAngle(double dx , double dy){
		
		

		double deg=Math.toDegrees(Math.atan2(Math.abs(dx) , Math.abs(dy)));

		if(dy>0 && dx <0){
			deg=-deg;
		}
		else
			if(dy>0 && dx>0){
				//remains same
			}
			else
				if(dy<0 && dx>0){
					deg=180-deg;
					//remains same
				}
				else
					if(dy<0 && dx<0){
						deg=-180+deg;
					}
		return deg;
		

	}

}
