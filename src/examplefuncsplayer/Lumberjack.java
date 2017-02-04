package examplefuncsplayer;

import battlecode.common.Clock;
import battlecode.common.Direction;
import battlecode.common.GameConstants;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import battlecode.common.RobotInfo;
import battlecode.common.RobotType;
import battlecode.common.Team;
import battlecode.common.TreeInfo;
import examplefuncsplayer.*;

public class Lumberjack {
	static RobotController rc;
	static void runLumberjack() {
		Team enemy = rc.getTeam().opponent();

        // The code you want your robot to perform every round should be in this loop
        while (true) {

            // Try/catch blocks stop unhandled exceptions, which cause your robot to explode
            try {

                // See if there are any enemy robots within striking range (distance 1 from lumberjack's radius)
                RobotInfo[] robots = rc.senseNearbyRobots(RobotType.LUMBERJACK.bodyRadius+GameConstants.LUMBERJACK_STRIKE_RADIUS, enemy);
                TreeInfo[] treelocation = rc.senseNearbyTrees(rc.getType().sensorRadius, rc.getTeam().NEUTRAL);
                if(robots.length > 0 && !rc.hasAttacked()) {
                    // Use strike() to hit all nearby robots!
                    rc.strike();
                    
                } else if (treelocation.length > 0) {
                	MapLocation myLocation = rc.getLocation();
                	MapLocation treeLoc = treelocation[0].getLocation();
                	Direction toTree = myLocation.directionTo(treeLoc);
                	TryMove.tryMove(toTree);
                	if (rc.canChop(treelocation[0].location)) {
                		rc.chop(treelocation[0].location);
                	}	
                } else {
                    // No close robots, so search for robots within sight radius
                    robots = rc.senseNearbyRobots(-1,enemy);

                    // If there is a robot, move towards it
                    if(robots.length > 0) {
                        MapLocation myLocation = rc.getLocation();
                        MapLocation enemyLocation = robots[0].getLocation();
                        Direction toEnemy = myLocation.directionTo(enemyLocation);

                        TryMove.tryMove(toEnemy);
                    } else {
                        // Move Randomly
                        TryMove.tryMove(randomMove.randomDirection());
                    }
                }

                // Clock.yield() makes the robot wait until the next turn, then it will perform this loop again
                Clock.yield();

            } catch (Exception e) {
                System.out.println("Lumberjack Exception");
                e.printStackTrace();
            }
        }
    }
	}
	

