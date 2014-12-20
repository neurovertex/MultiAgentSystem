package eu.neurovertex.multiagent;

/**
* @author Neurovertex
*         Date: 19/12/14
*         Time: 20:20
*/
public enum Direction {
	FORWARD, RIGHT, BACKWARD, LEFT;

	public int rotateX(int i, int j) {
		switch (this) {
			case FORWARD:
				return i;
			case RIGHT:
				return -j;
			case BACKWARD:
				return -i;
			case LEFT:
				return j;
			default:
				throw new IllegalStateException();
		}
	}

	public int rotateY(int i, int j) {
		switch (this) {
			case FORWARD:
				return j;
			case RIGHT:
				return i;
			case BACKWARD:
				return -j;
			case LEFT:
				return i;
			default:
				throw new IllegalStateException();
		}
	}

	public Direction invert() {
		return values()[(4-ordinal())%4];
	}
}
