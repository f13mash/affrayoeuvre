package affrayoeuvre.movement;

import java.util.ArrayList;

import affrayoeuvre.AbstractCTFRobot;
import affrayoeuvre.node.Node;

public interface Movement{

	Node[][] goTowards(AbstractCTFRobot robot, double x, double y);
	Node[][] goTowardsForAngle(AbstractCTFRobot robot, double distance, double angle);
}
