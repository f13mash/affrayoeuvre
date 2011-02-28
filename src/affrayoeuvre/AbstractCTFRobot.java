package affrayoeuvre;

import java.awt.geom.Point2D;

import affrayoeuvre.gun.GunManager;
import affrayoeuvre.movement.AntiGravity;
import affrayoeuvre.movement.Dijkstra;
import affrayoeuvre.movement.Force;
import affrayoeuvre.movement.MovementManager;
import affrayoeuvre.radar.*;
import affrayoeuvre.robot.BareRobot;
import affrayoeuvre.robot.EnemyManager;
import affrayoeuvre.thinker.Philosopher;
import affrayoeuvre.util.*;
import robocode.*;
import CTFApi.CaptureTheFlagApi;

public abstract class AbstractCTFRobot extends BareRobot implements Constants{

	public RadarManager radarManager = null;
	public MapManager mapManager = null;
	public EnemyManager enemyManager = null;
	public Philosopher philosopher = null;
	public GunManager gunManager =  null;
	public MovementManager movementManager = null;
	public Point2D pnt=null;
	
	public void run(){
		initialize();
		System.out.println("About to print the map . . .");
		pnt=getEnemyFlag();
		mapManager.printMap();
		movementManager.goTowards(getEnemyFlag());
		while(true){
			System.out.println("check check");
			radarManager.doJob();
			enemyManager.doJob();
			mapManager.doJob();
			if(getRoundNum()==0)
				movementManager.doJob();
			philosopher.doJob();
			gunManager.doJob();
			execute();
			
		}
	}
	
	private void initialize(){
		
		registerMe();
		
		if(this.getEnergy()>190){
			isLeader=true;
		}
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
		initializeBattle();
		
		//for now no need to do the separate re-initialization . . .
		/*
		mapManager.reinitialize();
		radarManager.reinitialize();
		enemyManager.reinitialize();
		philosopher.reinitialize();
		*/
		
	}

	public void initializeBattle() {
		
		
		initializeMapManager();
		intializeEnemyManager();
		initializeRadarManager();
		initializePhilosopher();
		intializeGunManager();
		initializeMovementManager();
		
	}

	public void initializeMovementManager(){
		movementManager=new MovementManager(this);
	}
	private void intializeGunManager() {
		gunManager=new GunManager(this);
		
	}

	private void initializePhilosopher() {
		philosopher = new Philosopher(this);
		
	}

	private void intializeEnemyManager() {
		enemyManager=new EnemyManager(this);
		
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
			return;
		}
		
		
	}
	
	public void onHitObject(HitObjectEvent e){
		
		if(e.getType().equals("flag")){
			handleFlagHit(e);
		}
	}

	private void handleFlagHit(HitObjectEvent e) {
		stop();
		//turnRight(180);
		pnt=new Point2D.Double(getOwnBase().getCenterX() , getOwnBase().getCenterY());
		//stop();
		//movementManager.goTowards(pnt);
		
		
	}

	public void onScannedRobot(ScannedRobotEvent e){
		System.out.println("Robot scanned : "+e.getBearing());
		enemyManager.onScannedRobot(e);
	}
	private void handleBoxHit(HitObstacleEvent e) {
		double degree=getHeading()+e.getBearing();
		degree=Utilities.AngleToA180(degree);
		double mostProbableWidth=getWidth()/Math.max(Math.abs(Math.sin(Math.toRadians(e.getBearing()))), Math.abs(Math.cos(Math.toRadians(e.getBearing()))));
		double x=getX()+mostProbableWidth*Math.sin(Math.toRadians(degree));
		double y=getY()+mostProbableWidth*Math.cos(Math.toRadians(degree));
		
		mapManager.markMap(x, y, BOX);
		movementManager.moveToFreePoint();
		//back(MAP_LARGE_BLOCK_SIZE);
		System.out.println("Box hit : "+x+" , "+y);
		//mapManager.markMap(getX(), getY(), BOX);
	}
	
	
}
