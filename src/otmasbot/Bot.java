package otmasbot;
import battlecode.common.*;

public class Bot {
	static RobotController rc;
	public static Direction randomDirection() {
		return new Direction((float)Math.random() * 2 * (float)Math.PI);
	}
	public static boolean move(Direction dir) throws GameActionException {
		return tryMove(dir,20,3);
	}
	
    static boolean tryMove(Direction dir, float degreeOffset, int checksPerSide) throws GameActionException {

        // First, try intended direction
        if (rc.canMove(dir)) {
            rc.move(dir);
            return true;
        }

        // Now try a bunch of similar angles
        boolean moved = false;
        int currentCheck = 1;

        while(currentCheck<=checksPerSide) {
            // Try the offset of the left side
            if(rc.canMove(dir.rotateLeftDegrees(degreeOffset*currentCheck))) {
                rc.move(dir.rotateLeftDegrees(degreeOffset*currentCheck));
                return true;
            }
            // Try the offset on the right side
            if(rc.canMove(dir.rotateRightDegrees(degreeOffset*currentCheck))) {
                rc.move(dir.rotateRightDegrees(degreeOffset*currentCheck));
                return true;
            }
            // No move performed, try slightly further
            currentCheck++;
        }

        // A move never happened, so return false.
        return false;
    }
    static RobotInfo[] getEnemyLocation(int radius) {
    	Team team = rc.getTeam().opponent();
    	RobotInfo[] enemyLocation;
    	return enemyLocation = rc.senseNearbyRobots(radius, team);
    }
}
