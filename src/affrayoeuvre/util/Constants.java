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
	
	/*
	 * 
	 * Constants related to AntiGravity Movement
	 * 
	 * 
	 */
	public static final double FORCE_OBSTACLE=3000;
	public static final double REDUCTION_OBSTACLE=3;
	public static final double FORCE_WALL=2;
	public static final double REDUCTION_WALL=0.80;
	public static final double FORCE_POINT=1000;
	public static final double REDUCTION_POINT=1.87;
	public static final double WALL_ALLOWANCE=50;
	
	/*
	 * 
	 * Target choosing constants
	 * 
	 */
	public static final double TARGET_MAX_DISTANCE=600;
	public static final double TARGET_WEIGHT=3;
	public static final double AIMING_WEIGHT=1;
	public static final double GUN_TURN_WEIGHT=1;
	public static final double TARGETTED_BY_COUNT_WEIGHT=-0.5;	//preferred to -ve
	
	
	
	/*
	 * 
	 * Angle
	 * 
	 */
	public static final double A_360=360;
	public static final double A_180=180;
	public static final double A_90=90;
	public static final double A_0=0;

    public static final double  SCAN_ARC                = 45.0;
    public static final double  HALF_SCAN_ARC           = SCAN_ARC / 2;
    public static final long    MAX_SCAN_AGE            = 8;
    public static final long    TARGET_SCAN_AGE            = 8;
    
    public static final long TARGET_SHOOT_AGE	=	2;
    
    public static final double CLOSE_DISTANCE = 10;
    
    public static final double DIFFICULTY_TO_FIRE_POWER=0.25;
    

    public static final double  MAX_BULLET_SPEED        = 20;
    public static final double  MAX_BULLET_POWER        = 3.0;
    public static final double  MIN_BULLET_POWER        = 0.1;
    
    public static final double IS_ANGLE_DEGREE_ZERO = 1.5;
    public static final double IS_DISTANCE_ZERO = 1;
    
    public static final double MOVE_DISTANCE=25;
}

