package affrayoeuvre.radar;

import robocode.CustomEvent;
import robocode.ScannedObjectEvent;
import robocode.ScannedRobotEvent;
import affrayoeuvre.AbstractCTFRobot;
import affrayoeuvre.util.AbstractManager;
import affrayoeuvre.util.Constants;

public class RadarManager extends AbstractManager implements Constants{

	public RadarManager(AbstractCTFRobot robot) {
		super(robot);
		scanForMap();
		
	}

	@Override
	public void doJob() {
		
		//TODO add the job here
	}

	@Override
	public void reinitialize() {
	}
	
	void onScannedRobot(ScannedRobotEvent e){
		
	}
	
	public void onScannedObject(ScannedObjectEvent e){
		//add content here if interested in handling
	}
	
	
	void scanForMap(){
		
		for(int turnCount=0 ; turnCount<FULL_SCAN/MAP_SCAN_DEGREE ; turnCount++){
			scan(MAP_SCAN_DEGREE);
		}
			
	}
	
	void scan(double degrees){
		robot.setTurnRadarRight(degrees);
		robot.execute();
	}

	
}
