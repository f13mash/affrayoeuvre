package affrayoeuvre.movement;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.text.StyleContext.SmallAttributeSet;


import affrayoeuvre.AbstractCTFRobot;
import affrayoeuvre.node.Node;
import affrayoeuvre.util.Constants;
import affrayoeuvre.util.Utilities;

public class Dijkstra implements Constants , Movement{
/*
 * 
 * This class marks all the shortest path to the destination using Dijkstra .
 * 
 */
	//default Value .. . .  Must be set to a lower value for faster computation
	double path_max=Double.MAX_VALUE;
	ArrayList<Node> path=new ArrayList<Node>();

	@Override
	public ArrayList<Node> goTowards(AbstractCTFRobot robot, double x, double y ) {
		
		int xInd=(int) Math.round(x/robot.MAP_LARGE_BLOCK_SIZE);
		int yInd=(int) Math.round(y/robot.MAP_LARGE_BLOCK_SIZE);
		//System.out.println("Stage 1 : ( "+xInd+" , "+yInd+" ) , blocked : "+robot.smallMap[xInd][yInd].isBlocked);
		if(robot.smallMap[xInd][yInd].isBlocked)
			return null;
		
		Node[][] smallMap=getSmallMapClone(robot.smallMap);
		path_max=getTriangularDistance(robot, x, y);
		path_max=Math.ceil(path_max/MAP_LARGE_BLOCK_SIZE);
		//path_max=4;
		//System.out.println("path_max : "+path_max);
		smallMap[xInd][yInd].weight=0;
		smallMap[xInd][yInd].parent=9;
		solveDijsktra(smallMap , xInd , yInd , 0 );
		printMap(smallMap);
		return null;
	}
	
	private void solveDijsktra(Node[][] smallMap , int xInd , int yInd , int pathCount){
		
		
		//System.out.println("Stage 2 : ( "+xInd+" , "+yInd+" ) , pathCount : "+pathCount);
		
		Node node=smallMap[xInd][yInd];
		ArrayList<DijkstraStageData> lst=new ArrayList<DijkstraStageData>();
		
		if(pathCount>path_max)
			return;
		//System.out.println("Stage 3 : "+node.getConnectedCount());
		
		for(int k=0 ; k<node.connected.size() ; k++){
			
			
			if(!node.connected.get(k))
				continue;
			
			if(node.parent==k)
				continue;
			
			boolean blocked=true;
			DijkstraStageData data=new DijkstraStageData();
			
		
			
			switch(k){
			case 0:
				if(isInside(smallMap, xInd, yInd+1)){
					blocked=smallMap[xInd][yInd+1].isBlocked || !(smallMap[xInd][yInd+1].connected.get(4) && smallMap[xInd][yInd].connected.get(0));
					data.xInd=xInd;
					data.yInd=yInd+1;
					data.node=smallMap[xInd][yInd+1];
				}
				break;
			case 1:
				if(isInside(smallMap, xInd+1, yInd+1)){
					blocked=smallMap[xInd+1][yInd+1].isBlocked || !(smallMap[xInd+1][yInd+1].connected.get(5) && smallMap[xInd][yInd].connected.get(1));
					data.xInd=xInd+1;
					data.yInd=yInd+1;
					data.node=smallMap[xInd+1][yInd+1];
				}
				break;
			case 2:
				if(isInside(smallMap, xInd+1, yInd)){
					blocked=smallMap[xInd+1][yInd].isBlocked || !(smallMap[xInd+1][yInd].connected.get(6) && smallMap[xInd][yInd].connected.get(2));
					data.xInd=xInd+1;
					data.yInd=yInd;
					data.node=smallMap[xInd+1][yInd];
				}
				break;
			case 3:
				if(isInside(smallMap, xInd+1, yInd-1)){
					blocked=smallMap[xInd+1][yInd-1].isBlocked || !(smallMap[xInd+1][yInd-1].connected.get(7) && smallMap[xInd][yInd].connected.get(3));
					data.xInd=xInd+1;
					data.yInd=yInd-1;
					data.node=smallMap[xInd+1][yInd-1];
				}
				break;
			case 4:
				if(isInside(smallMap, xInd, yInd-1)){
					blocked=smallMap[xInd][yInd-1].isBlocked || !(smallMap[xInd][yInd-1].connected.get(0) && smallMap[xInd][yInd].connected.get(4));
					data.xInd=xInd;
					data.yInd=yInd-1;
					data.node=smallMap[xInd][yInd-1];
				}
				break;
			case 5:
				if(isInside(smallMap, xInd-1, yInd-1)){
					blocked=smallMap[xInd-1][yInd-1].isBlocked || !(smallMap[xInd-1][yInd-1].connected.get(1) && smallMap[xInd][yInd].connected.get(5));
					data.xInd=xInd-1;
					data.yInd=yInd-1;
					data.node=smallMap[xInd-1][yInd-1];
				}
				break;
			case 6:
				if(isInside(smallMap, xInd-1, yInd)){
					blocked=smallMap[xInd-1][yInd].isBlocked || !(smallMap[xInd-1][yInd].connected.get(2) && smallMap[xInd][yInd].connected.get(6));
					data.xInd=xInd-1;
					data.yInd=yInd;
					data.node=smallMap[xInd-1][yInd];
				}
				break;
			case 7:
				if(isInside(smallMap, xInd-1, yInd+1)){
					blocked=smallMap[xInd-1][yInd+1].isBlocked || !(smallMap[xInd-1][yInd+1].connected.get(3) && smallMap[xInd][yInd].connected.get(7));
					data.xInd=xInd-1;
					data.yInd=yInd+1;
					data.node=smallMap[xInd-1][yInd+1];
				}
				break;
			}
			
			//System.out.println("Stage 5 k : "+k+" , , , "+blocked);
			
			if(data.node.parent!=9)
			if(!blocked){
				int turning=Math.min(Math.abs(node.parent-(k+4)%8), 8-Math.abs(node.parent-(k+4)%8));
				if(node.parent==9)
					turning=0;
				double weight=node.weight+turning*MAP_45_TURN_WEIGHT_FACTOR*MAP_LARGE_BLOCK_SIZE + MAP_LARGE_BLOCK_SIZE*(1+(Math.sqrt(2)-1)*k%2);
				//System.out.println("weight : "+weight+"  old weight  : "+smallMap[data.xInd][data.yInd].weight+"  val : "+(smallMap[data.xInd][data.yInd].weight>weight));
				if(smallMap[data.xInd][data.yInd].weight>weight){
					smallMap[data.xInd][data.yInd].weight=weight;
					smallMap[data.xInd][data.yInd].parent=(k+4)%8;
					data.parent=(k+4)%8;
					lst.add(data);
				}
			}
			
			
			
		}
		
		//System.out.println("Stage 4 : lst size : "+lst.size());
		
		for(int i=0 ; i<lst.size() ; i++){
			solveDijsktra(smallMap, lst.get(i).xInd, lst.get(i).yInd, pathCount+1);
		}
		
		
	}


	@Override
	//angle and distance from the current bot position
	public ArrayList<Node> goTowardsForAngle(AbstractCTFRobot robot, double distance, double angle) {
		// TODO Auto-generated method stub
		double x=robot.getX()+distance*Math.sin(Math.toRadians(angle));
		double y=robot.getY()+distance*Math.cos(Math.toRadians(angle));
		return goTowards(robot, x, y);
	}
	
	private Node[][] getSmallMapClone(Node[][] smallMap){
		Node clonedMap[][]=smallMap.clone();
		for(int i=0 ; i<clonedMap.length ; i++){
			for(int j=0 ; j<clonedMap[i].length ; j++){
				clonedMap[i][j]=nodeCopy(smallMap[i][j]);
			}
		}
		return clonedMap;
	}
	
	private Node nodeCopy(Node node){
		Node elem=new Node(node.type);
		elem.connected=(ArrayList<Boolean>) node.connected.clone();
		elem.parent=8;
		elem.weight=Double.MAX_VALUE;
		return elem;
		
	}
	
	
	private double getTriangularDistance(AbstractCTFRobot robot , double x , double y){
		
		double degree=Utilities.getAngle(x-robot.getX(), y-robot.getY());
		
		double dis=(Math.abs(x-robot.getX())+Math.abs(y-robot.getY()))*PATH_LENGTH_MAX_ALLOWED*Math.pow((Math.max(Math.sin(Math.toRadians(degree)), Math.cos(Math.toRadians(degree)))) , POWER_REDUCTION_FACTOR);
		
		return dis;
		
		
	}
	
	private boolean isInside(Node[][] smallMap , int x , int y){
		return ( x>=0 && x<smallMap.length && y>=0 && y<smallMap[0].length );
	}
	
	public void printMap(Node[][] smallMap){
		String out=null;
		
		out="Way. . . . . .";
		for(int i=0 ; i<smallMap.length ; i++){
			out+="\n";
			for(int j=0 ; j<smallMap[i].length ; j++){
				out+=" "+smallMap[i][j].parent;
			}
		}
		System.out.println(out);
	}
	
}
