package eu.neurovertex.multiagent;

/**
 * Acts as a wrapper in cases where a Grid needs to return an element along with its coordinates.
 * @author Neurovertex
 *         Date: 19/12/14
 *         Time: 20:25
 */
public class GridAgent {
	private final Grid grid;
	private final Position position;
	private final Agent element;

	public GridAgent(Grid grid, Position position, Agent element) {
		this.grid = grid;
		this.position = position;
		this.element = element;
	}

	public Position getPosition() {
		return position;
	}

	public Agent getAgent() {
		return element;
	}

	public Grid getGrid() {
		return grid;
	}
}
