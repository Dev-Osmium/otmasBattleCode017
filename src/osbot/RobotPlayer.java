package osbot;
import battlecode.common.*;

public class RobotPlayer {
	static RobotController rc;

	//static Direction dir = randomDirection();

	public static void run(RobotController rc) throws GameActionException {
		RobotPlayer.rc = rc;
		switch (rc.getType()) {
		case ARCHON:

			runArchon();
			break;
		case GARDENER:
			runGardener();
			break;
		case SOLDIER:
			runSoldier();
			break;
		case LUMBERJACK:
			runLumberjack();
			break;

		}

	}
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
	public static void runGardener() throws GameActionException {

		while (true) {

			Direction dir = randomDirection();
			try {

				if (rc.canBuildRobot(RobotType.LUMBERJACK, dir)) {
					rc.buildRobot(RobotType.LUMBERJACK, dir);
				} else if (rc.canPlantTree(dir)) {
					rc.plantTree(dir);
				} 
				Clock.yield();
			} catch(Exception e) {
				e.printStackTrace();
			}


		}
	}
	public static void runSoldier() throws GameActionException {
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
	public static void runLumberjack() {

		Direction dir = randomDirection();

		while (true) {

			TreeInfo[] treeLocations = rc.senseNearbyTrees(rc.getType().sensorRadius, rc.getTeam().NEUTRAL);
			try {
				if (treeLocations.length > 0) {

					MapLocation myLocation = rc.getLocation();
					MapLocation treeLocation = treeLocations[0].getLocation();
					Direction toTree = myLocation.directionTo(treeLocation);
					move(toTree);
					if (rc.canChop(treeLocations[0].ID)) {
						rc.chop(treeLocations[0].ID);
					}

				} else {
					move(dir);
				}
				Clock.yield();

			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	} 

	public static Direction randomDirection() {
		return new Direction((float)Math.random() * 2 * (float)Math.PI);
	}
	public static boolean move(Direction dir) throws GameActionException {
		return tryMove(dir /*,20,3*/);
	}
	static boolean tryMove(Direction dir/*, float degreeOffset, int checksPerSide */) throws GameActionException {

		// First, try intended direction
		if (rc.canMove(dir)) {
			rc.move(dir);
			return true;
		} else {
			return false;
		}

	}
}

