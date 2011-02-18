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
			setAhead(100);
			radarManager.doJob();
			execute();
			mapManager.printMap();
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
		mapManager.onScannedObject(e);
	}
	
	public void onHitObstacle(HitObstacleEvent e){
		if(e.getObstacleType().equals("box")){
			handleBoxHit(e);
		}
		
	}

	private void handleBoxHit(HitObstacleEvent e) {
		double degree=getHeading()+e.getBearing();
		degree=Utilities.AngleToA180(degree);
		double x=getX()+getWidth()*BOT_AVG_HIT*Math.sin(Math.toRadians(degree));
		double y=getY()+getWidth()*BOT_AVG_HIT*Math.cos(Math.toRadians(degree));
		mapManager.markMap(x, y, BOX);
	}
	
	
}
