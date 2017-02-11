package osbot;

import battlecode.common.Direction;
import battlecode.common.*;

public class Gardener extends Bot {

	public static void runGardener() throws GameActionException {

		while (true) {

			Direction dir = randomDirection();
			try {
				if (rc.canPlantTree(dir)) {
					rc.plantTree(dir);
				} else if (rc.canBuildRobot(RobotType.LUMBERJACK, dir)) {
					rc.buildRobot(RobotType.LUMBERJACK, dir);
				}
				Clock.yield();
			} catch(Exception e) {
				e.printStackTrace();
			}
			

		}

	}
}