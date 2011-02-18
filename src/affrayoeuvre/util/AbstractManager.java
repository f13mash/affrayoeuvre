package affrayoeuvre.util;

import affrayoeuvre.AbstractCTFRobot;

public abstract class AbstractManager implements Constants{
	
	public AbstractCTFRobot robot=null;
	
	public AbstractManager(AbstractCTFRobot robot){
		this.robot=robot;
	}
	
	public abstract void doJob();
	
	public abstract void reinitialize();

}
