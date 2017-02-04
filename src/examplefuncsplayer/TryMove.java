package examplefuncsplayer;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;

public class TryMove {
	static RobotController rc;

	static boolean tryMove(Direction dir) throws GameActionException {
		return examplefuncsplayer.RobotPlayer.tryMove(dir, 20, 3);
}
}
