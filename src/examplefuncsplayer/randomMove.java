package examplefuncsplayer;

import battlecode.common.Direction;

public class randomMove {
	static Direction randomDirection() {
        return new Direction((float)Math.random() * 2 * (float)Math.PI);
    }
}
