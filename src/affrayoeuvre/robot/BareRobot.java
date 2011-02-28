package affrayoeuvre.robot;

import java.awt.geom.Rectangle2D;
import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.Map;

import affrayoeuvre.node.Node;
import affrayoeuvre.node.Point;
import CTFApi.CaptureTheFlagApi;

public class BareRobot extends CaptureTheFlagApi {
	
	/*
	 * 
	 * Static Data related to Map . . ..
	 */
	public static Node[][] mainMap=null;
	public static int mainMapWidth=0;
	public static int mainMapHeight=0;

	public static Node[][] smallMap=null;
	public static int smallMapWidth=0;
	public static int smallMapHeight=0;
	
	public static ArrayList<Point> obstacleList=null;
	
	public static boolean enemyFlagCaptured=false;
	public static boolean ownFlagCaptured=false;
	
	public static Map<Rectangle2D, Double> baseEntryMap = null;
	
	public boolean isBusy=false;
	public boolean isLeader=false;
	
	public Map<String, Enemy> enemyMap=null;
	
	public Enemy currentTarget=null;

}
