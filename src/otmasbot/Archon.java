package otmasbot;

import battlecode.common.RobotType;
import battlecode.common.*;

public class Archon extends Bot {

	public static void runArchon() throws GameActionException {

		Direction dir = randomDirection();

		while (true) {
			try {			
				if (rc.canBuildRobot(RobotType.GARDENER, dir)) {
					rc.buildRobot(RobotType.GARDENER, dir);
				} else {
					move(dir);
				}
				Clock.yield();

			}
			catch(Exception e) {
				System.out.println("Archon Exception!");
				e.printStackTrace();
			}
		}

	}

}
