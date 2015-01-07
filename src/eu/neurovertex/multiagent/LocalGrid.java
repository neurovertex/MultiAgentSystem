package eu.neurovertex.multiagent;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import static eu.neurovertex.multiagent.Grid.StaticElement.UNKNOWN;

/**
 * @author Neurovertex
 *         Date: 20/12/14
 *         Time: 22:05
 */
public class LocalGrid implements Grid {
	private final GlobalGrid global;
	private final int xoff, yoff;
	private final Direction rotation;
	private final BiPredicate<Integer, Integer> area;
	private boolean valid = true;

	public LocalGrid(GlobalGrid global, int xoff, int yoff, Direction rotation, BiPredicate<Integer, Integer> area) {
		this.global = global;
		this.xoff = xoff;
		this.yoff = yoff;
		this.rotation = rotation;
		this.area = area;
	}

	public LocalGrid(GlobalGrid global, int xoff, int yoff, Direction rotation, int radius) {
		this(global, xoff, yoff, rotation, (i, j) -> (Math.hypot(i, j)) < radius + 0.5);
	}

	@Override
	public Element get(int x, int y) {
		if (!valid)
			throw new IllegalStateException("Grid was invalidated");
		if (area.test(x, y))
			return getGlobal(x, y);
		else
			return UNKNOWN;
	}

	@Override
	public List<GridAgent> getAgents() {
		return global.getAgents().stream().map(a -> new GridAgent(this, getLocal(a.getPosition()), a.getAgent()))
				.filter(a -> test(a.getPosition())).collect(Collectors.toList());
	}

	public boolean canSee(int x, int y) {
		return area.test(x, y);
	}

	private Element getGlobal(int x, int y) {
		x = rotation.rotateX(x, y) + xoff;
		y = rotation.rotateY(x, y) + yoff;
		return (x >= 0 && x < global.getWidth() && y >= 0 && y < global.getHeight()) ?
				global.get(x, y) : UNKNOWN;
	}

	private boolean test(Position p) {
		return area.test(p.getX(), p.getY());
	}

	private Position getLocal(Position pos) {
		int x = rotation.invert().rotateX(pos.getX() - xoff, pos.getY() - yoff);
		int y = rotation.invert().rotateY(pos.getX() - xoff, pos.getY() - yoff);
		return pos.getDirection().map(d -> new Position(x, y, d.rotate(rotation.invert())))
									.orElse(new Position(x, y));
	}

	public void invalidate() {
		valid = false;
	}
}
