package affrayoeuvre.node;

import affrayoeuvre.util.Constants;

public class Node implements Constants{

	public boolean isBlocked=false;
	/*
	 * 
	 * type=0 if isBlocked=true;
	 * 
	 */
	public int type=0;
	public double weight=0;
	
	public Node(int type){
		if(type>BASE)
			isBlocked=true;
		this.type=type;
	}
	
}
