package affrayoeuvre.thinker;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import affrayoeuvre.AbstractCTFRobot;
import affrayoeuvre.robot.Enemy;
import affrayoeuvre.util.AbstractManager;
import affrayoeuvre.util.Constants;
import affrayoeuvre.util.Utilities;

public class Philosopher extends AbstractManager implements Constants{
	public Enemy currentTarget = null;

	public Philosopher(AbstractCTFRobot robot) {
		super(robot);
		robot.enemyMap=new HashMap<String, Enemy>();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doJob() {
		
		Enemy enem=selectTarget();
		
		robot.radarManager.setRadarTarget(enem);
	}

	public Enemy selectTarget(){
		
		Set<String> enemyName=(Set<String>) robot.enemyMap.keySet();
		Enemy enem=null;
		double diff=Double.MAX_VALUE;
		
		Iterator<String> itr=enemyName.iterator();
		while(itr.hasNext()){
			Enemy enemy=robot.enemyMap.get(itr.next());
			if(!enemy.isAlive)
				continue;
			if(enemy.time==0 || robot.getTime()-enemy.time>TARGET_SCAN_AGE)
				continue;
			if(getDifficulty(enemy)<diff){
				diff=getDifficulty(enemy);
				enem=enemy;
			}	
		}
		if(currentTarget!=null)
			currentTarget.beingTargettedBy--;
		if(enem!=null)
			enem.beingTargettedBy++;
		currentTarget=enem;
		return enem;
		
	}
	
	public double getDifficulty(Enemy enemy){
		/*
		 * bots far away are less preferred
		 * bots with their heading nearly in parallel with the gun heading are preferred
		 * also it'll be preferred to target by more than one member of the team 
		 */
		double targettingDifficulty=getTargettingDifficulty(enemy);
		
		double aimingDifficulty=getAimingDifficulty(enemy);
		
		double gunDifficulty=getGunDfficulty(enemy);
		
		double beingTargetted=getBeingTargettedBy(enemy);
		
		double difficulty=targettingDifficulty*TARGET_WEIGHT+aimingDifficulty*AIMING_WEIGHT+gunDifficulty*GUN_TURN_WEIGHT+beingTargetted*TARGETTED_BY_COUNT_WEIGHT;
		
		return difficulty;
	}
	
	public double getGunDfficulty(Enemy enemy){
		double angle=Math.abs(Utilities.getAngle(enemy.x-robot.getX(), enemy.y-robot.getY()));
		double angDiff=Math.abs(Utilities.AngleToA180(angle-enemy.heading));
		return Math.abs(Utilities.AngleToA180(angle-robot.getGunHeading()))/A_180;
	}
	
	public double getAimingDifficulty(Enemy enemy){
		double angle=Math.abs(Utilities.getAngle(enemy.x-robot.getX(), enemy.y-robot.getY()));
		double angDiff=Math.abs(Utilities.AngleToA180(angle-enemy.heading));
		return Math.min(angDiff, A_180-angDiff)/A_90;

	}
	
	public double getTargettingDifficulty(Enemy enemy){
		return getDistance(enemy)/TARGET_MAX_DISTANCE*TARGET_WEIGHT;
	}
	
	public double getBeingTargettedBy(Enemy enemy){
		return enemy.beingTargettedBy;
	}
	public double getDistance(Enemy enemy){
		double dx=enemy.x-robot.getX();
		double dy=enemy.y-robot.getY();
		return Math.sqrt(dx*dx+dy*dy);
	}
	
	
	private void selectCollectiveTarget(){
		
	}
	@Override
	public void reinitialize() {
		// TODO Auto-generated method stub
		
	}

}
