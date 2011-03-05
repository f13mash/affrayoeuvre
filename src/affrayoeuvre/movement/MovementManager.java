package affrayoeuvre.movement;

import java.awt.geom.Point2D;

import javax.swing.JTable.PrintMode;

import affrayoeuvre.AbstractCTFRobot;
import affrayoeuvre.node.Node;
import affrayoeuvre.util.AbstractManager;
import affrayoeuvre.util.Utilities;

public class MovementManager extends AbstractManager {

	public Dijkstra dij=null;
	public AntiGravity ag=null;
	private Node[][] smallMap=null;
	private Point2D destination=null;
	private boolean toMove=false;
	public MovementManager(AbstractCTFRobot robot) {
		super(robot);
		ag=new AntiGravity(robot);
		dij=new Dijkstra();
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doJob() {
		if(this.reachedDestination()){
			System.out.println("reached the destination ");
			return;
		}
		stepAhead();

	}

	@Override
	public void reinitialize() {
		// TODO Auto-generated method stub

	}
	
	private void stepAhead(){
		int x=(int) Math.round(robot.getX()/MAP_LARGE_BLOCK_SIZE);
		int y=(int) Math.round(robot.getY()/MAP_LARGE_BLOCK_SIZE);
		int xf=(int) Math.round(destination.getX()/MAP_LARGE_BLOCK_SIZE);
		int yf=(int) Math.round(destination.getY()/MAP_LARGE_BLOCK_SIZE);
		
		Node node=smallMap[x][y];
		Point2D nextStop = null;
		System.out.println("Current Position : "+x+" , "+y);
		dij.printMap(smallMap);
		

		switch(node.parent){
		case 0:
			nextStop=new Point2D.Double(robot.getX()+0*MAP_LARGE_BLOCK_SIZE , robot.getY()+1*MAP_LARGE_BLOCK_SIZE);
			
			break;
		case 1:
			nextStop=new Point2D.Double(robot.getX()+1*MAP_LARGE_BLOCK_SIZE , robot.getY()+1*MAP_LARGE_BLOCK_SIZE);
			
			break;
		case 2:
			nextStop=new Point2D.Double(robot.getX()+1*MAP_LARGE_BLOCK_SIZE , robot.getY()+0*MAP_LARGE_BLOCK_SIZE);
			
			break;
		case 3:
			nextStop=new Point2D.Double(robot.getX()+1*MAP_LARGE_BLOCK_SIZE , robot.getY()-1*MAP_LARGE_BLOCK_SIZE);
			
			break;
		case 4:
			nextStop=new Point2D.Double(robot.getX()+0*MAP_LARGE_BLOCK_SIZE , robot.getY()-1*MAP_LARGE_BLOCK_SIZE);
			
			break;
		case 5:
			nextStop=new Point2D.Double(robot.getX()-1*MAP_LARGE_BLOCK_SIZE , robot.getY()-1*MAP_LARGE_BLOCK_SIZE);
			
			break;
		case 6:
			nextStop=new Point2D.Double(robot.getX()-1*MAP_LARGE_BLOCK_SIZE , robot.getY()+0*MAP_LARGE_BLOCK_SIZE);
			
			break;
		case 7:
			nextStop=new Point2D.Double(robot.getX()-1*MAP_LARGE_BLOCK_SIZE , robot.getY()+1*MAP_LARGE_BLOCK_SIZE);
			
			break;
		case 8:
			moveToFreePoint();
			smallMap=dij.goTowards(robot, destination.getX(), destination.getY());
			x=(int) Math.round(robot.getX()/MAP_LARGE_BLOCK_SIZE);
			y=(int) Math.round(robot.getX()/MAP_LARGE_BLOCK_SIZE);
			nextStop=null;
			break;
		case 9:{
			System.out.println("set the destination to null : old one "+destination);
			//goAntiGravity(destination);
			//finishingMove(destination);
			smallMap=null;
			destination=null;
			return;
		}
		
		
		}
		
		if(nextStop!=null){
			double dis=Utilities.getDistance(destination , new Point2D.Double(robot.getX(), robot.getY()));
			double dis2=Utilities.getDistance(nextStop, new Point2D.Double(robot.getX() , robot.getY()));
			if(dis2<=dis)
				goAntiGravity(nextStop);
			else
				finishingMove(destination);
		}
		
	}
	
	
	public Node[][] goTowards(Point2D destination){
		this.smallMap=dij.goTowards(robot, destination.getX(), destination.getY());
		this.destination=destination;
		
		
		
		
		
		int x=(int) Math.round(robot.getX()/MAP_LARGE_BLOCK_SIZE);
		int y=(int) Math.round(robot.getY()/MAP_LARGE_BLOCK_SIZE);
		int xf=(int) Math.round(destination.getX()/MAP_LARGE_BLOCK_SIZE);
		int yf=(int) Math.round(destination.getY()/MAP_LARGE_BLOCK_SIZE);
		
		//this.toMove=reachedDestination();
		System.out.println("Destination : "+xf+" , "+yf);
		System.out.println("Current Location : "+x+" , "+y);
		
		
		System.out.println("about to print the final map : "+destination);
		dij.printMap(smallMap);
		
		return smallMap;
		
		
	}
	
	//moves using only the Antigravity . . .  without the point force
	public void moveToFreePoint() {
		
		System.out.println("moving to free point ");
		
		Force ObstacleForce=ag.getAntiGravityForceFromObstacle();
		Force WallForce=ag.getAntiGravityForceFromWall();
		Force Resultant=new Force(ObstacleForce.xComponent+WallForce.xComponent, ObstacleForce.yComponent+WallForce.yComponent);
		System.out.println("Free move angle resultant : "+Resultant.getAngleResultant());
		double angleTurn=Utilities.AngleToA180(Resultant.getAngleResultant()-robot.getHeading());
		robot.setTurnRight(angleTurn);
		robot.setAhead(MOVE_DISTANCE);
		
		
	}

	private void goAntiGravity(Point2D dest){
		
		int xf=(int) Math.round(dest.getX()/MAP_LARGE_BLOCK_SIZE);
		int yf=(int) Math.round(dest.getY()/MAP_LARGE_BLOCK_SIZE);
		if(robot.smallMap[xf][yf].isBlocked)
			this.goTowards(destination);
		
		Force ObstacleForce=ag.getAntiGravityForceFromObstacle();
		Force WallForce=ag.getAntiGravityForceFromWall();
		Force DestinationForce=ag.getGravityForceForPoint(dest.getX(), dest.getY());
		Force Resultant=new Force(ObstacleForce.xComponent+WallForce.xComponent+DestinationForce.xComponent, ObstacleForce.yComponent+WallForce.yComponent+DestinationForce.yComponent);
		//Force Resultant=ag.getGravityForceForPoint(dest.getX(), dest.getY());
		double angleTurn=Utilities.AngleToA180(Resultant.getAngleResultant()-robot.getHeading());
		double distance=Utilities.getDistance(dest, new Point2D.Double(robot.getX() , robot.getY()));
		
		robot.turnRight(angleTurn);
		robot.setAhead(distance);
		
	}
	
	private void ahead(double distance){
		robot.ahead(distance);
	}

	public boolean reachedDestination(){
		
		if(destination==null){
			System.out.println("dest is null");
			return true;
		}
		int x=(int) Math.round(robot.getX()/MAP_LARGE_BLOCK_SIZE);
		int y=(int) Math.round(robot.getY()/MAP_LARGE_BLOCK_SIZE);
		int xf=(int) Math.round(destination.getX()/MAP_LARGE_BLOCK_SIZE);
		int yf=(int) Math.round(destination.getY()/MAP_LARGE_BLOCK_SIZE);
		
		return (x==xf && y==yf);
	}
	
	public void finishingMove(Point2D destination ){
		Force ObstacleForce=ag.getAntiGravityForceFromObstacle();
		Force WallForce=ag.getAntiGravityForceFromWall();
		Force DestinationForce=ag.getGravityForceForPoint(destination.getX(), destination.getY());
		//Force Resultant=new Force(ObstacleForce.xComponent+WallForce.xComponent+DestinationForce.xComponent, ObstacleForce.yComponent+WallForce.yComponent+DestinationForce.yComponent);
		Force Resultant=ag.getGravityForceForPoint(destination.getX(), destination.getY());
		double angleTurn=Utilities.AngleToA180(Resultant.getAngleResultant()-robot.getHeading());
		double distance=Utilities.getDistance(destination, new Point2D.Double(robot.getX() , robot.getY()));
		System.out.println("finishing with angle "+angleTurn);
		robot.turnRight(angleTurn);
		robot.setAhead(distance);
		
	}

}
