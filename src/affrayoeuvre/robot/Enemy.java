package affrayoeuvre.robot;

import affrayoeuvre.util.Constants;

public class Enemy extends Robot implements Constants{

	public double x=0 , y=0;
	public int beingTargettedBy=0;
	
	//will store the statistics against each robot here . . .
	
	public Enemy(String name , double x , double y , double energy, double heading , double velocity , long time){
		this.name=name;
		this.x=x;
		this.y=y;
		this.energy=energy;
		this.heading=heading;
		if(this.energy>190)
			isLeader=true;
		this.velocity=velocity;
		this.time=time;
		
	}

	public void update(double x, double y, double energy,
			double heading, double velocity, long time) {
		
		this.x=x;
		this.y=y;
		this.energy=energy;
		this.heading=heading;
		if(this.energy>190)
			isLeader=true;
		this.velocity=velocity;
		this.time=time;
		
	}
}
