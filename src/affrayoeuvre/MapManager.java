package affrayoeuvre;

import robocode.ScannedObjectEvent;
import affrayoeuvre.node.Node;
import affrayoeuvre.util.AbstractManager;
import affrayoeuvre.util.Constants;
import affrayoeuvre.util.Utilities;

public class MapManager extends AbstractManager implements Constants {

	public MapManager(AbstractCTFRobot robot) {
		super(robot);
		robot.mainMapWidth=(int) Math.ceil(robot.getBattleFieldWidth()/MAP_SMALL_BLOCK_SIZE);
		robot.mainMapHeight=(int) Math.ceil(robot.getBattleFieldHeight()/MAP_SMALL_BLOCK_SIZE);
		if(robot.mainMap==null)
			init();
	}

	private void init() {
		robot.mainMap=new Node[robot.mainMapWidth+1][robot.mainMapHeight+1];
		
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
		markMap(e, FLAG);
	}
	
	private void handleBaseTypeEvent(ScannedObjectEvent e) {
		//markMap(e, BASE);
	}
	
	private void markMap(ScannedObjectEvent e, int val){
		
		double degree=robot.getHeading()+e.getBearing();
		degree=Utilities.AngleToA180(degree);
		double x=robot.getX()+e.getDistance()*Math.sin(Math.toRadians(degree));
		double y=robot.getY()+e.getDistance()*Math.cos(Math.toRadians(degree));
		
		int xInd=(int) Math.round(x/MAP_SMALL_BLOCK_SIZE);
		int yInd=(int) Math.round(y/MAP_SMALL_BLOCK_SIZE);
		
		//all the nodes with value above that of the BASE will be of blocking nature .
		if(val>BASE){
			robot.mainMap[xInd][yInd].isBlocked=true;
		}
		
		//if the node type is WALL then there's no need to override with a new value
		if(robot.mainMap[xInd][yInd].type==WALL)
			return;
		robot.mainMap[xInd][yInd].type=val;
	}
	
	public void markMap(double x , double y , int val){
		
		int xInd=(int) Math.round(x/MAP_SMALL_BLOCK_SIZE);
		int yInd=(int) Math.round(y/MAP_SMALL_BLOCK_SIZE);
		
		//all the nodes with value above that of the BASE will be of blocking nature .
		if(val>BASE){
			robot.mainMap[xInd][yInd].isBlocked=true;
		}
		
		//if the node type is WALL then there's no need to override with a new value
		if(robot.mainMap[xInd][yInd].type==WALL)
			return;
		
		robot.mainMap[xInd][yInd].type=val;
		
	}
	
	public void printMap(){
		String out="";
		for(int i=0 ; i<=robot.mainMapWidth ; i++){
			out+="\n";
			for(int j=0 ; j<=robot.mainMapHeight ; j++){
				out+=" "+robot.mainMap[i][j].type;
			}
		}
		System.out.println(out);
	}

}
