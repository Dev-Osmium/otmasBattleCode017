package osbot;
import battlecode.common.*;

public class RobotPlayer {
	static RobotController rc;
	static int noLumber = 0;
	static int noSoldier = 0;
	//static Direction dir = randomDirection();

	public static void run(RobotController rc) throws GameActionException {
		RobotPlayer.rc = rc;
		switch (rc.getType()) {
		case ARCHON:
			Archon.runArchon();
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



			try {
                while (true) {
                    Direction dir = randomDirection();
                    if (rc.canBuildRobot(RobotType.GARDENER, dir)) {
                        rc.buildRobot(RobotType.GARDENER, dir);
                    } else {
                        move(dir);
                    }
                    Clock.yield();
                }
			}
			catch(Exception e) {
				System.out.println("Archon Exception!");
				e.printStackTrace();
			}
		}


	public static void runGardener() throws GameActionException {

		while (true) {

			Direction dir = randomDirection();
			try {
				TreeInfo[] treeLoc = rc.senseNearbyTrees(rc.getType().sensorRadius, rc.getTeam().NEUTRAL);
				TreeInfo[] myTreeLoc = rc.senseNearbyTrees(rc.getType().sensorRadius, rc.getTeam());
				RobotInfo[] robotLoc = rc.senseNearbyRobots(rc.getType().sensorRadius, rc.getTeam().opponent());
				if (rc.canBuildRobot(RobotType.LUMBERJACK, dir) && treeLoc.length > 0 /* noLumber <= 10*/ ) {
					rc.buildRobot(RobotType.LUMBERJACK, dir);
					//noLumber = noLumber++;
				} 
				if (rc.canBuildRobot(RobotType.SOLDIER, dir) /*&& robotLoc.length > 0 && noSoldier <= 10 */) {
					rc.buildRobot(RobotType.SOLDIER, dir);
				} 
				if (rc.canPlantTree(dir)) {
					rc.plantTree(dir);
					if (rc.canShake()) {
						rc.shake(myTreeLoc[0].location);
					}
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
				RobotInfo[] robots = rc.senseNearbyRobots(rc.getType().sensorRadius, rc.getTeam().opponent());

				if (robots.length > 0) {
					MapLocation myLoc = rc.getLocation();
					MapLocation robotLoc = robots[0].getLocation();
					Direction fireDir = myLoc.directionTo(robotLoc);
					rc.setIndicatorDot(robotLoc, 0, 255, 0);
					if (rc.canFirePentadShot()) {
						rc.firePentadShot(fireDir);
					} else if (rc.canFireTriadShot()){
						rc.fireTriadShot(fireDir);
					} else if (rc.canFireSingleShot()) {
						rc.fireSingleShot(fireDir);
					} 
					move(randomDirection());
					Clock.yield();

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
			RobotInfo[] robotLocations = rc.senseNearbyRobots(rc.getType().sensorRadius, rc.getTeam().opponent());
			try {
				if (treeLocations.length > 0) {

					MapLocation myLocation = rc.getLocation();
					MapLocation treeLocation = treeLocations[0].getLocation();
					Direction toTree = myLocation.directionTo(treeLocation);
					if (rc.canMove(toTree)){
						move(toTree);
					}	
					if (rc.canChop(treeLocations[0].ID)) {
						rc.chop(treeLocations[0].ID);
					}
				}/* else if (rc.canBuildRobot(RobotType.SOLDIER, dir)) {
					rc.buildRobot(RobotType.SOLDIER, dir);
				} */if (/*treeLocations.length == 0 &&*/ robotLocations.length > 0) {
					MapLocation myLocation = rc.getLocation();
					MapLocation robotLoc = robotLocations[0].getLocation();
					Direction toBot = myLocation.directionTo(robotLoc);
					
					if (rc.canMove(toBot)) {
						rc.setIndicatorLine(myLocation, robotLoc, 255, 0, 0);
						move(toBot);
						if (rc.canStrike()) {
							System.out.println("I strike!");
							rc.strike();
						}
					}
					//rc.disintegrate();
				}
				else {
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

