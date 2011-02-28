package affrayoeuvre.util;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public final class Utilities implements Constants{

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
				}
				else
					if(dy<0 && dx<0){
						deg=-180+deg;
					}
		return deg;
		

	}
	
	public static boolean isNearTo(Point2D point1 , Point2D point2){
		return point1.distance(point2)<CLOSE_DISTANCE;
	}


	public static double getAngle(Point2D pointInit , Point2D pointDest) {
		return getAngle(pointDest.getX()-pointInit.getX(), pointDest.getY()-pointInit.getY());
	}


    public static double calcBulletSpeed(double firePower) {
        return (MAX_BULLET_SPEED - (3 * firePower));
    }
    
    public static double getDistance(double dx , double dy){
    	return Math.sqrt(dx*dx+dy*dy);
    }
    public static double getDistance(Point2D point1 , Point2D point2){
    	return getDistance(point1.getX()-point2.getX(), point1.getY()-point2.getY());
    }

}
