package eu.neurovertex.multiagent;

/**
 * Acts as a wrapper in cases where a Grid needs to return an element along with its coordinates.
 * @author Neurovertex
 *         Date: 19/12/14
 *         Time: 20:25
 */
public class GridElement implements Grid.Element {
	private final Grid grid;
	private final Position position;
	private final Grid.Element element;

	public GridElement(Grid grid, Position position, Grid.Element element) {
		this.grid = grid;
		this.position = position;
		this.element = element;
	}

	public Position getPosition() {
		return position;
	}

	public Grid.Element getElement() {
		return element;
	}

	public Grid getGrid() {
		return grid;
	}
}
