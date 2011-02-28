package affrayoeuvre.movement;

import affrayoeuvre.util.Utilities;

public class Force {

	public double xComponent , yComponent ;
	private double angleResultant=0;
	
	public Force(double xComponent , double yComponent){
		
		this.xComponent=xComponent;
		this.yComponent=yComponent;
		
		
	}
		
	public double getAngleResultant(){
		return Utilities.getAngle(this.xComponent, this.yComponent);
	}
}
