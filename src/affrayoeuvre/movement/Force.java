package affrayoeuvre.movement;

import affrayoeuvre.util.Utilities;

public class Force {

	double xComponent , yComponent , angleResultant;
	
	public Force(double xComponent , double yComponent){
		
		this.xComponent=xComponent;
		this.yComponent=yComponent;
		this.angleResultant=Utilities.getAngle(this.xComponent, this.yComponent);
		
	}
		
}
