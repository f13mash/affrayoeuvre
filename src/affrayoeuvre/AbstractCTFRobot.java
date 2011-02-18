package affrayoeuvre;

import affrayoeuvre.radar.*;
import affrayoeuvre.robot.BareRobot;
import affrayoeuvre.util.*;
import robocode.*;
import CTFApi.CaptureTheFlagApi;

public abstract class AbstractCTFRobot extends BareRobot implements Constants{

	public static RadarManager radarManager = null;
	public static MapManager mapManager = null;
	
	public void run(){
		

		
		initialize();
		System.out.println("About to print the map . . .");
		mapManager.printMap();
		while(true){
			radarManager.doJob();
			execute();
		}
	}
	
	private void initialize(){
		
		registerMe();

        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        
		if (getRoundNum() == 0) {
            initializeBattle();
        } else {
            reinitialize();
        }
		
		
		execute();

	}

	private void reinitialize() {

		mapManager.reinitialize();
		radarManager.reinitialize();
		
	}

	public void initializeBattle() {
		
		initializeMapManager();
		initializeRadarManager();
		
	}

	private void initializeRadarManager() {
		
		radarManager = new RadarManager(this);
		
	}
	
	private void initializeMapManager(){
		mapManager = new MapManager(this);
	}
	
	public void onScannedObject(ScannedObjectEvent e){
		if(e.getObjectType().equals("flag"))
			System.out.println("seen the flag at : "+e.getBearing());
		mapManager.onScannedObject(e);
	}
}
