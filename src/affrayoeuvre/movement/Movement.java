package affrayoeuvre.movement;

import java.util.ArrayList;

import affrayoeuvre.AbstractCTFRobot;
import affrayoeuvre.node.Node;

public interface Movement{

	ArrayList<Node> goTowards(AbstractCTFRobot robot, double x, double y);
	ArrayList<Node> goTowardsForAngle(AbstractCTFRobot robot, double distance, double angle);
}
