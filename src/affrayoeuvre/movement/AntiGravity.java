package affrayoeuvre.movement;

import java.awt.geom.Point2D;
import java.util.ArrayList;


import affrayoeuvre.AbstractCTFRobot;
import affrayoeuvre.node.Node;
import affrayoeuvre.node.Point;
import affrayoeuvre.util.Constants;
import affrayoeuvre.util.Utilities;

public class AntiGravity implements Constants{

	private AbstractCTFRobot robot=null;
	
	public AntiGravity(AbstractCTFRobot robot){
		this.robot=robot;
	}
	
	public Force getAntiGravityForceFromObstacle(){

		double resultantX=0 , resultantY=0;
		double obstacleX=0 , obstacleY=0;
		
		
		Point2D current=new Point2D.Double(robot.getX() , robot.getY());
		Point2D object=null;
		Point node=null;
		
		//System.out.println("node size : "+robot.obstacleList.size());
		
		//now handling the resultant from the obstacles		
		for(int i=0 ; i<robot.obstacleList.size() ; i++){
			
			node=robot.obstacleList.get(i);
			object=new Point2D.Double(node.x, node.y);
			double distance=current.distance(object);
			double deg=Utilities.getAngle(object.getX()-current.getX() , object.getY()-current.getY());
			double force=(FORCE_OBSTACLE/Math.pow(distance, REDUCTION_OBSTACLE));
			
			obstacleX+=(-Math.sin(Math.toRadians(deg))*force);
			obstacleY+=(-Math.cos(Math.toRadians(deg))*force);
			
		}
		
		
		
		System.out.println("antigravity : "+obstacleX+" , "+obstacleY);
		return new Force(obstacleX, obstacleY);
		
	}
	
	public Force getAntiGravityForceFromWall(){
		double x=robot.getX();
		double y=robot.getY();
		double xComponent=0;
		double yComponent=0;
		
		double xGap=WALL_ALLOWANCE-Math.min(x, robot.getBattleFieldWidth()-x);
		double yGap=WALL_ALLOWANCE-Math.min(y, robot.getBattleFieldHeight()-y);
		
		if(xGap>0){
			xComponent=Math.pow((xGap/WALL_ALLOWANCE) , REDUCTION_WALL)*FORCE_WALL;
		}
		
		if(yGap>0){
			yComponent=Math.pow((yGap/WALL_ALLOWANCE) , REDUCTION_WALL)*FORCE_WALL;
		}
		
		xComponent*=(x<robot.getBattleFieldWidth()/2 ? 1:-1);
		yComponent*=(y<robot.getBattleFieldHeight()/2?1:-1);
		
		System.out.println("wallforce : "+xComponent+" , "+yComponent);
		return new Force(xComponent, yComponent);
	}
	
	public Force getGravityForceForPoint(double x , double y){
		double dx=x-robot.getX();
		double dy=y-robot.getY();
		double distance=Math.sqrt(dx*dx+dy*dy);
		double angle=Utilities.getAngle(dx, dy);
		
		double force=FORCE_POINT/Math.pow(distance, REDUCTION_POINT);
		
		double xComponent=force*dx/distance;
		double yComponent=force*dy/distance;
		
		System.out.println("pointforce : "+xComponent+" , "+yComponent);
		return new Force(xComponent, yComponent);
	}
}
