package affrayoeuvre.gun;

import affrayoeuvre.robot.Enemy;
import affrayoeuvre.util.Constants;
import affrayoeuvre.util.Utilities;

public class EstimateAimShoot implements Constants{

	public static double aimAndFire(Enemy target , FireSolution fireSolution , double x , double y , long currentTime){
		double tx=target.x;
		double ty=target.y;
		double heading=Utilities.AngleToA180(target.heading);
		double velocity=target.velocity;
		long time=target.time;
		double distance=Utilities.getDistance(tx-x, ty-y);
		
		double impactTimeExtra=distance/fireSolution.bulletSpeed;
		double disMoved=(impactTimeExtra+currentTime-time)*velocity;
		double newtx=tx+disMoved*Math.sin(Math.toRadians(heading));
		double newty=ty+disMoved*Math.cos(Math.toRadians(heading));
		/*
		System.out.println("Bot Distance : "+distance);
		System.out.println("Bullet Speed : "+fireSolution.bulletSpeed);
		System.out.println("REcord time : "+time);
		System.out.println("Current Time : "+currentTime);
		System.out.println("Extra impact time : "+impactTimeExtra);
		System.out.println("Velocity opponent "+velocity);
		System.out.println("New position : "+newtx+" , "+newty);
		*/
		return Utilities.getAngle(newtx-x, newty-y);
	}
}
