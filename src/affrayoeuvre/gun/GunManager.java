package affrayoeuvre.gun;

import robocode.Condition;
import affrayoeuvre.AbstractCTFRobot;
import affrayoeuvre.robot.Enemy;
import affrayoeuvre.thinker.Philosopher;
import affrayoeuvre.util.AbstractManager;
import affrayoeuvre.util.Constants;
import affrayoeuvre.util.Utilities;

public class GunManager extends AbstractManager implements Constants{
	

	public GunManager(AbstractCTFRobot robot) {
		super(robot);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doJob() {
		Enemy target=robot.philosopher.currentTarget;
		if(target==null){
			return;
		}
		
		if(robot.getTime()-target.time>TARGET_SHOOT_AGE)
			return;
		
		//System.out.println("About to shoot at : "+target.name);
		aimAndShoot(target);
		
	}

	@Override
	public void reinitialize() {
		// TODO Auto-generated method stub
		
	}
	
	public void aimAndShoot(Enemy target){
		FireSolution fs=new FireSolution(target, robot.philosopher);
		double angle=EstimateAimShoot.aimAndFire(target, fs , robot.getX() , robot.getY() , robot.getTime());
		double gunTurn=Utilities.AngleToA180(angle-robot.getGunHeading());
		Fire(gunTurn, fs.firePower);
	}
	
	private void Fire(double gunTurn , double power){
		robot.setTurnGunRight(gunTurn/2);
		robot.setFire(power/2);
		robot.setTurnGunRight(gunTurn/2);
		robot.setFire(power/2);
	}
	
	
}
