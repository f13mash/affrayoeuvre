package affrayoeuvre.gun;

import affrayoeuvre.robot.Enemy;
import affrayoeuvre.thinker.Philosopher;
import affrayoeuvre.util.Constants;
import affrayoeuvre.util.Utilities;

public class FireSolution implements Constants{

	double firePower=0.0;
	double bulletSpeed=0.0;
	Philosopher philosopher=null;
	
	public FireSolution(Enemy enemy , Philosopher philosopher){
		this.philosopher=philosopher;
		this.firePower=getFirePower(enemy);
		this.bulletSpeed=Utilities.calcBulletSpeed(firePower);
	}
	
	public double getFirePower(Enemy enemy ){
		double firePowerDistance=philosopher.getTargettingDifficulty(enemy);
		double firePowerAiming=philosopher.getAimingDifficulty(enemy);
		double firePowerGunning=philosopher.getGunDfficulty(enemy);
		double firePowerFriends=philosopher.getBeingTargettedBy(enemy);
		
		double difficulty=philosopher.getDifficulty(enemy);
		
		double firePower=MAX_BULLET_POWER-difficulty*DIFFICULTY_TO_FIRE_POWER;
		
		if(firePower<MIN_BULLET_POWER || MAX_BULLET_POWER<=difficulty*DIFFICULTY_TO_FIRE_POWER)
			firePower=MIN_BULLET_POWER;
		
		return firePower;
	}
}
