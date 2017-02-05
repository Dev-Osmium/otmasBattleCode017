package otmasbot;
import battlecode.common.*;

public class RobotPlayer {
	static RobotController rc;
	
	
	public static void run(RobotController rc) throws GameActionException {
		switch (rc.getType()) {
		case ARCHON:
			Archon.runArchon();
			break;
		case GARDENER:
			Gardener.runGardener();
			break;
		case SOLDIER:
			Soldier.runSoldier();
			break;
		case TANK:
			break;
		case LUMBERJACK:
			Lumberjack.runLumberjack();
			break;
		case SCOUT:
			break;
		default:
			break;
		}
	}
	
}
