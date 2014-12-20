package eu.neurovertex.multiagent;

import java.util.Optional;

/**
 * @author Neurovertex
 *         Date: 19/12/14
 *         Time: 20:22
 */
public class GlobalGrid implements Grid {
	private final Element[][] grid;
	private final int width, height;

	public GlobalGrid(int width, int height) {
		this.width = width;
		this.height = height;
		grid = new Element[width][height];
	}


	@Override
	public Optional<Element> get(int x, int y) {
		return Optional.ofNullable(grid[x][y]);
	}

	@Override
	public void set(Position pos, Optional<Element> element) {

	}
}
