package eu.neurovertex.multiagent;

/**
* @author Neurovertex
*         Date: 19/12/14
*         Time: 20:20
*/
public enum Cardinal {
	EAST, SOUTH, WEST, NORTH;

	public Cardinal rotate(Direction dir) {
		return values()[(ordinal() + dir.ordinal())%values().length];
	}
}
