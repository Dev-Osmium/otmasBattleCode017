package osbot;

import battlecode.common.GameActionException;
import battlecode.common.*;

public class Soldier extends Bot {

	static void runSoldier() throws GameActionException {

		//Code goes here
		while(true) {
			try {
				RobotInfo[] robots = rc.senseNearbyRobots(30, rc.getTeam().opponent());

				if (robots.length > 0) {
					MapLocation myLoc = rc.getLocation();
					MapLocation robotLoc = robots[0].getLocation();
					Direction fireDir = myLoc.directionTo(robotLoc);
					if (rc.canFirePentadShot()) {
						rc.firePentadShot(fireDir);
					} else if (rc.canFireTriadShot()){
						rc.fireTriadShot(fireDir);
					} else if (rc.canFireSingleShot()) {
						rc.fireSingleShot(fireDir);
					} else {
						Clock.yield();
					}
				}
				Clock.yield();
			} catch(Exception e) {
				e.printStackTrace();
			}

		}

	}

}
