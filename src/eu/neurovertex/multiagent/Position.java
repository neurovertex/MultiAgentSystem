package eu.neurovertex.multiagent;

import java.util.Optional;

/**
* @author Neurovertex
*         Date: 19/12/14
*         Time: 20:20
*/
public class Position {
	private final int x, y;
	private final Optional<Cardinal> direction;

	public Position(int x, int y, Cardinal direction) {
		this.x = x;
		this.y = y;
		this.direction = Optional.ofNullable(direction);
	}

	/**
	 * Directionless constructor. In some cases, direction doesn't matter or is irrelevant.
	 * @param x    X coordinate
	 * @param y    Y coordinate
	 */
	public Position(int x, int y) {
		this(x, y, null);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Position forward(Cardinal direction, int distance) {
		Direction dir = Direction.values()[direction.ordinal()];
		int dx = dir.rotateX(distance, 0), dy = dir.rotateY(distance, 0);
		return new Position(getX() + dx, getY() + dy);
	}

	public Position forward(int distance) {
		return forward(direction.get(), distance);
	}

	public Optional<Cardinal> getDirection() {
		return direction;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Position && ((Position)obj).x == this.x && ((Position)obj).y == this.y;
	}

	@Override
	public int hashCode() {
		return (x<<16) + y;
	}
}
