package affrayoeuvre.radar;

import java.util.ArrayList;

import robocode.CustomEvent;
import robocode.ScannedObjectEvent;
import robocode.ScannedRobotEvent;
import affrayoeuvre.AbstractCTFRobot;
import affrayoeuvre.robot.Enemy;
import affrayoeuvre.util.AbstractManager;
import affrayoeuvre.util.Constants;
import affrayoeuvre.util.Utilities;

public class RadarManager extends AbstractManager implements Constants{

	public RadarManager(AbstractCTFRobot robot) {
		super(robot);
		if(robot.getRoundNum()==0)
			scanForMap();
		
	}

	@Override
	public void doJob() {
		setRadarTarget(robot.philosopher.currentTarget);
	}

	@Override
	public void reinitialize() {
	}
	
	public void onScannedObject(ScannedObjectEvent e){
		//add content here if interested in handling
	}
	
	public void setRadarTarget(ArrayList<Enemy> botList){
		
	}
	
	public void setRadarTarget(Enemy target){
		
		
		
		double angle    = 0.0;
        double heading  = robot.getRadarHeading() % A_360;

        /* There is only one bot, but we don't know where it is. */
        if (target == null) {

            scan(A_360);

        } else {

            /*
             * There is only one bot, but we haven't seen it
             * in a while or the target we have been given is
             * already dead.
             */
            if (target.time == 0 ||
                target.time+
                MAX_SCAN_AGE <
                robot.getTime()||
                target.energy == -1)   {

                scan(A_360);

            /*
             * We have a valid target and the data on it
             * is good.  Scan back and forth in the target's
             * direction.
             */
            } else {

                angle = Utilities.AngleToA180(Utilities.getAngle(target.x-robot.getX(), target.y-robot.getY())-robot.getRadarHeading());

                
                angle += angle > 0 ? +HALF_SCAN_ARC : -HALF_SCAN_ARC;
                scan(angle);
            }
        }
		
	}
	
	void scanForMap(){
		
		for(int turnCount=0 ; turnCount<FULL_SCAN/MAP_SCAN_DEGREE ; turnCount++){
			robot.turnRadarRight(MAP_SCAN_DEGREE);
		}
			
	}
	
	void scan(double degrees){
		robot.setTurnRadarRight(degrees);
	}

	
}
