package otmasbot;

import battlecode.common.*;

public class Lumberjack extends Bot {

	@SuppressWarnings("static-access")
	static void runLumberjack() throws GameActionException {

		Direction dir = randomDirection();

		while (true) {

			TreeInfo[] treeLocations = rc.senseNearbyTrees(rc.getType().sensorRadius, rc.getTeam().NEUTRAL);
			try {
				if (treeLocations.length > 0) {

					MapLocation myLocation = rc.getLocation();
					MapLocation treeLocation = treeLocations[0].getLocation();
					Direction toTree = myLocation.directionTo(treeLocation);
					move(toTree);

				} else {
					move(dir);
				}
				Clock.yield();

			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	} 



}