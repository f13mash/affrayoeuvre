package affrayoeuvre.robot;

import affrayoeuvre.AbstractCTFRobot;

public class TeamRobot {

	AbstractCTFRobot robot=null;
	
	
	public double getdistance(double x , double y){
		double dx=x-robot.getX();
		double dy=y-robot.getY();
		return Math.sqrt(dx*dx+dy*dy);
	}
}
