package affrayoeuvre.util;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;

import robocode.ScannedObjectEvent;
import affrayoeuvre.AbstractCTFRobot;
import affrayoeuvre.node.Node;
import affrayoeuvre.node.Point;
import affrayoeuvre.util.*;

public class MapManager extends AbstractManager implements Constants {

	public MapManager(AbstractCTFRobot robot) {
		super(robot);
		
		if(robot.baseEntryMap==null)
			robot.baseEntryMap=new HashMap<Rectangle2D, Double>();
		
		robot.mainMapWidth=(int) Math.ceil(robot.getBattleFieldWidth()/MAP_SMALL_BLOCK_SIZE);
		robot.mainMapHeight=(int) Math.ceil(robot.getBattleFieldHeight()/MAP_SMALL_BLOCK_SIZE);
		robot.smallMapWidth=(int) Math.ceil(robot.getBattleFieldWidth()/MAP_LARGE_BLOCK_SIZE);
		robot.smallMapHeight=(int) Math.ceil(robot.getBattleFieldHeight()/MAP_LARGE_BLOCK_SIZE);
		
		if(robot.mainMap==null)
			init();
		
	}

	private void init() {
		robot.mainMap=new Node[robot.mainMapWidth+1][robot.mainMapHeight+1];
		robot.smallMap=new Node[robot.smallMapWidth+1][robot.smallMapHeight+1];
		robot.obstacleList=new ArrayList<Point>();
		
		// Marking the walls
		for(int wid=0 ; wid<=robot.mainMapWidth ; wid++){
			for(int hght=0 ; hght<=robot.mainMapHeight ; hght++){
				//if the object is on wall then mark it as WALL else as a free Object . .
				if(wid==0 || hght==0 || wid==(robot.mainMapWidth) || hght==(robot.mainMapHeight))
					robot.mainMap[wid][hght]=new Node(WALL);
				else
					robot.mainMap[wid][hght]=new Node(FREE);
			}
		}
		
		
		//Marking the walls on Small Map too . . .
		for(int wid=0 ; wid<=robot.smallMapWidth ; wid++){
			for(int hght=0 ; hght<=robot.smallMapHeight ; hght++){
				//if the object is on wall then mark it as WALL else as a free Object . .
				if(wid==0 || hght==0 || wid==(robot.smallMapWidth) || hght==(robot.smallMapHeight))
					robot.smallMap[wid][hght]=new Node(WALL);
				else
					robot.smallMap[wid][hght]=new Node(FREE);
				
			}
		}
		
		for(int wid=0 ; wid<=robot.smallMapWidth ; wid++){
			for(int hght=0 ; hght<=robot.smallMapHeight ; hght++){
				int quadrantCode=2;
				updateSmallMap(wid, hght,wid);
			}
		}
		
	}

	public void doJob() {
		// TODO Auto-generated method stub

	}

	@Override
	public void reinitialize() {
		// TODO not much to right now here . .. 

	}
	
	public void onScannedObject(ScannedObjectEvent e){
		if(e.getObjectType().equals("box")){
			handleBoxTypeEvent(e);
			return;
		}
		
		if(e.getObjectType().equals("flag")){
			handleFlagTypeEvent(e);
			return;
		}
		
		if(e.getObjectType().equals("base")){
			handleBaseTypeEvent(e);
			return;
		}
		
	}

	private void handleBoxTypeEvent(ScannedObjectEvent e) {
		markMap(e, BOX);
	}
	
	private void handleFlagTypeEvent(ScannedObjectEvent e) {
		//markMap(e, FLAG);
		seenTheFlag(e);
	}
	
	private void handleBaseTypeEvent(ScannedObjectEvent e) {
		//markMap(e, BASE);
	}
	
	private void seenTheFlag(ScannedObjectEvent e ){
		
		double x=robot.getX()+e.getDistance()*Math.sin(Math.toRadians(e.getBearing()+robot.getHeading()));
		double y=robot.getY()+e.getDistance()*Math.cos(Math.toRadians(e.getBearing()+robot.getHeading()));
		Point2D flagPoint=new Point2D.Double(x,y);
		Point2D robotPoint=new Point2D.Double(robot.getX() , robot.getY());
		double deg=Utilities.getAngle(flagPoint , robotPoint);
		double anglist[]=new double[4];
		int index=0;
		Rectangle2D base= null;
		boolean valFound=false;
		
		if(Utilities.isNearTo(flagPoint, robot.getOwnFlag()) && robot.getOwnBase().contains(flagPoint)){
			base=robot.getOwnBase();
		}
		else
			if(Utilities.isNearTo(flagPoint, robot.getEnemyFlag()) && robot.getEnemyBase().contains(flagPoint)){
				base=robot.getEnemyBase();
			}
		
		//since the data is already present no need to work on it further
		if(robot.baseEntryMap.containsKey(base))
			return;
		
		//avoids the terrible case where robot is create inside the base
		if(base==null || base.contains(robotPoint))
			return;

		anglist[index++]=Utilities.getAngle(flagPoint, new Point2D.Double(base.getMaxX() , base.getMaxY()));
		anglist[index++]=Utilities.getAngle(flagPoint, new Point2D.Double(base.getMaxX() , base.getMinY()));
		anglist[index++]=Utilities.getAngle(flagPoint, new Point2D.Double(base.getMinX() , base.getMinY()));
		anglist[index++]=Utilities.getAngle(flagPoint, new Point2D.Double(base.getMinX() , base.getMaxY()));
		
		
		
		if(deg>=0){
			if(deg<=anglist[0]){
				robot.baseEntryMap.put(base, (double) 0);
			}
			else
				if(deg<anglist[1]){
					robot.baseEntryMap.put(base, (double) 90);
				}
				else{
					robot.baseEntryMap.put(base, (double) 180);
				}
		}
		else{
			if(deg>=anglist[3]){
				robot.baseEntryMap.put(base, (double) 0);
			}
			else
				if(deg>anglist[2]){
					robot.baseEntryMap.put(base, (double) -90);
				}
				else{
					robot.baseEntryMap.put(base, (double) 180);
				}
		}
		
		System.out.println("Flag Entry at "+robot.baseEntryMap.get(base));
		
		
	}
	
	private void markMap(ScannedObjectEvent e, int val){
		
		double degree=robot.getHeading()+e.getBearing();
		degree=Utilities.AngleToA180(degree);
		double x=robot.getX()+e.getDistance()*Math.sin(Math.toRadians(degree));
		double y=robot.getY()+e.getDistance()*Math.cos(Math.toRadians(degree));
		
		int xInd=(int) Math.round(x/MAP_SMALL_BLOCK_SIZE);
		int yInd=(int) Math.round(y/MAP_SMALL_BLOCK_SIZE);
		int quadrantCode=0;
		quadrantCode=10*quadrantCode+((xInd>=(x/MAP_SMALL_BLOCK_SIZE))?1:0);
		quadrantCode=10*quadrantCode+((xInd>=(y/MAP_SMALL_BLOCK_SIZE))?1:0);
		int xSmallInd=(int) Math.round(x/MAP_LARGE_BLOCK_SIZE);
		int ySmallInd=(int) Math.round(y/MAP_LARGE_BLOCK_SIZE);
		
		if(!isInsideBounds(x, y) || !isSmallMapInside(xSmallInd, ySmallInd))
			return;
		
		//all the nodes with value above that of the BASE will be of blocking nature .
		if(val>BASE){
			

			System.out.println("adding one to the list : "+robot.mainMap[xInd][yInd].isBlocked);
			if(!robot.mainMap[xInd][yInd].isBlocked && val==BOX)
				robot.obstacleList.add(new Point(x, y));
			
			robot.mainMap[xInd][yInd].isBlocked=true;
			robot.smallMap[xSmallInd][ySmallInd].isBlocked=true;
			
		}
		
		//if the node type is WALL then there's no need to override with a new value
		if(robot.mainMap[xInd][yInd].type==WALL)
			return;
		robot.mainMap[xInd][yInd].type=val;
		
		if(robot.smallMap[xSmallInd][ySmallInd].type==WALL)
			return;
		robot.smallMap[xSmallInd][ySmallInd].type=val;
		
		robot.smallMap[xSmallInd][ySmallInd].setAllValuesTo(!robot.smallMap[xSmallInd][ySmallInd].isBlocked);
		updateSmallMap(xSmallInd , ySmallInd , quadrantCode);
	}
	
	public void markMap(double x , double y , int val){
		
		if(!isInsideBounds(x, y))
			return;
		
		int xInd=(int) Math.round(x/MAP_SMALL_BLOCK_SIZE);
		int yInd=(int) Math.round(y/MAP_SMALL_BLOCK_SIZE);
		int quadrantCode=0;
		quadrantCode=10*quadrantCode+((xInd>=(x/MAP_SMALL_BLOCK_SIZE))?1:0);
		quadrantCode=10*quadrantCode+((xInd>=(y/MAP_SMALL_BLOCK_SIZE))?1:0);
		int xSmallInd=(int) Math.round(x/MAP_LARGE_BLOCK_SIZE);
		int ySmallInd=(int) Math.round(y/MAP_LARGE_BLOCK_SIZE);
		
		//all the nodes with value above that of the BASE will be of blocking nature .
		if(val>BASE){
			
			if(!robot.mainMap[xInd][yInd].isBlocked && val==BOX)
				robot.obstacleList.add(new Point(x, y));
			
			robot.mainMap[xInd][yInd].isBlocked=true;
			robot.smallMap[xSmallInd][ySmallInd].isBlocked=true;
			updateSmallMap(xSmallInd, ySmallInd, quadrantCode);
		}
		else{

			robot.mainMap[xInd][yInd].isBlocked=false;
			robot.smallMap[xSmallInd][ySmallInd].isBlocked=false;
		}
		
		//if the node type is WALL then there's no need to override with a new value
		if(robot.mainMap[xInd][yInd].type==WALL)
			return;
		//System.out.println("values val : "+xInd+" , "+yInd +" ) ");
		robot.mainMap[xInd][yInd].type=val;
		

		if(robot.smallMap[xSmallInd][ySmallInd].type==WALL)
			return;
		robot.smallMap[xSmallInd][ySmallInd].type=val;
		
		
		
		
		
	}
	
	private void updateSmallMap(int i, int j , int quadrantCode) {
		
		if(!robot.smallMap[i][j].isBlocked)
			return;
		
		if(isSmallMapInside(i, j+1))
			robot.smallMap[i][j+1].connected.set(4, false);
		if(isSmallMapInside(i+1, j+1))
			robot.smallMap[i+1][j+1].connected.set(5, false);
		if(isSmallMapInside(i+1, j))
			robot.smallMap[i+1][j].connected.set(6, false);
		if(isSmallMapInside(i+1, j-1))
			robot.smallMap[i+1][j-1].connected.set(7, false);
		if(isSmallMapInside(i, j-1))
			robot.smallMap[i][j-1].connected.set(0, false);
		if(isSmallMapInside(i-1, j-1))
			robot.smallMap[i-1][j-1].connected.set(1, false);
		if(isSmallMapInside(i-1, j))
			robot.smallMap[i-1][j].connected.set(2, false);
		if(isSmallMapInside(i-1, j+1))
			robot.smallMap[i-1][j+1].connected.set(3, false);
		
		if(isSmallMapInside(i, j+1) && isSmallMapInside(i+1, j)){
			robot.smallMap[i][j+1].connected.set(3, false);
			robot.smallMap[i+1][j].connected.set(7, false);
		}
		
		if(isSmallMapInside(i+1, j) && isSmallMapInside(i, j-1)){
			robot.smallMap[i+1][j].connected.set(5, false);
			robot.smallMap[i][j-1].connected.set(1, false);
		}
		
		if(isSmallMapInside(i, j-1) && isSmallMapInside(i-1, j)){
			robot.smallMap[i][j-1].connected.set(7, false);
			robot.smallMap[i-1][j].connected.set(3, false);
		}
		
		if(isSmallMapInside(i-1, j) && isSmallMapInside(i, j+1)){
			robot.smallMap[i-1][j].connected.set(1, false);
			robot.smallMap[i][j+1].connected.set(5, false);
		}
		
	}

	private boolean isSmallMapInside(int i, int j) {
		return((i>=0 && i<=robot.smallMapWidth) && (j>=0 && j<=robot.smallMapHeight));
	}

	private boolean isInsideBounds(double i , double j){
		return((i>=0 && i<robot.getBattleFieldWidth()) && (j>=0 && j<robot.getBattleFieldHeight()));
	}
	public void printMap(){
		String out=null;
		
		out="Blocked\n";
		for(int i=0 ; i<=robot.smallMapWidth ; i++){
			out+="\n";
			for(int j=0 ; j<=robot.smallMapHeight ; j++){
				out+=" "+robot.smallMap[i][j].type;
			}
		}
		System.out.println(out);
	}

}
