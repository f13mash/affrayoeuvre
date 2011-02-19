package affrayoeuvre.util;

public interface Constants {

	/*
	 * 
	 * 		Constants Related to Radar Scanning
	 * 
	 */
	public final double MAP_SCAN_DEGREE=10;
	public final double FULL_SCAN=360;
	
	
	
	/*
	 * 
	 * Constants Related to Map Data
	 * 
	 */
	public final double MAP_SMALL_BLOCK_SIZE=30;	//more finer details of the arena . . .
	public final double MAP_LARGE_BLOCK_SIZE=50;	//larger block size reduce the computation overhead + blocked zones are marked nearly always right
	public final double MAP_BLOCK_MIN_ALLOWANCE=20;
	public final double MAP_45_TURN_WEIGHT_FACTOR=0.2;
	public final int FREE=0;
	public final int FLAG=1;
	public final int BASE=2;
	public final int BOX=3;
	public final int WALL=4;
	
	
	/*
	 * 
	 * Constants related to Obstacle Hit
	 * 
	 */
	public final double BOT_AVG_HIT=0.72;
	
	
	/*
	 * 
	 * Constants related to Dijkstra Movement method
	 * 
	 */
	public final double PATH_LENGTH_MAX_ALLOWED=4;
	public final double POWER_REDUCTION_FACTOR=2;
	
	
}
