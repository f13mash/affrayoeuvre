package affrayoeuvre.node;

import java.util.ArrayList;

import affrayoeuvre.util.Constants;

public class Node implements Constants{

	public boolean isBlocked=false;
	/*
	 * 
	 * type=0 if isBlocked=true;
	 * 
	 */
	public int type=0;
	public double weight=Double.MAX_VALUE;
	public int parent=8;
	
	public ArrayList<Boolean> connected=new ArrayList<Boolean>();
	
	public Node(int type){
		if(type>BASE){
			isBlocked=true;
			setAllValuesTo(false);
		}
		else{
			isBlocked=false;
			setAllValuesTo(true);
		}
		
		this.type=type;
	}
	
	//private set
	public void setAllValuesTo(boolean val){
		connected=new ArrayList<Boolean>();
		for(int i=0 ; i<8 ; i++)
			connected.add(val);
	}
	
	public int getConnectedCount(){
		int count=0;
		for(int i=0 ; i<8 ; i++)
			if(connected.get(i))
				count++;
		
		return count;
	}
}
