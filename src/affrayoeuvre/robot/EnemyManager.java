package affrayoeuvre.robot;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import robocode.ScannedRobotEvent;
import affrayoeuvre.AbstractCTFRobot;
import affrayoeuvre.util.AbstractManager;
import affrayoeuvre.util.Constants;
import affrayoeuvre.util.Utilities;

public class EnemyManager extends AbstractManager implements Constants{
	
	//will not save history across the rounds

	public EnemyManager(AbstractCTFRobot robot) {
		super(robot);
		if(robot.enemyMap==null)
			robot.enemyMap=new HashMap<String, Enemy>();
	}

	@Override
	public void doJob() {
		
		
	}

	@Override
	public void reinitialize() {
		// TODO Auto-generated method stub
		
	}
	
	public void onScannedRobot(ScannedRobotEvent e){
		this.update(e);
	}

	private void update(ScannedRobotEvent e){
		Point2D loc=getLocation(e);
		String name=e.getName();
		if(robot.enemyMap.get(name)==null){
			robot.enemyMap.put(name, new Enemy(name, loc.getX(), loc.getY(), e.getEnergy() , e.getHeading() , e.getVelocity() , e.getTime()));
		}
		else{
			robot.enemyMap.get(name).update(loc.getX(), loc.getY(), e.getEnergy() , e.getHeading() , e.getVelocity() , e.getTime());
		}
		
	}
	
	public Point2D getLocation(ScannedRobotEvent e){
		double angle=Utilities.AngleToA180(robot.getHeading()+e.getBearing());
		return new Point2D.Double((robot.getX()+e.getDistance()*Math.sin(Math.toRadians(angle))), (robot.getY()+e.getDistance()*Math.cos(Math.toRadians(angle))));
	}
	
}
