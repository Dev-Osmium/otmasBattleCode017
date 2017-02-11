package osbot;

import battlecode.common.*;
import osbot.*;
import battlecode.common.*;

public class Archon extends Bot {
	
	public static void runArchon() throws GameActionException {
		
		
		while (true) {
			try {		
				Direction dir = randomDirection();
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
