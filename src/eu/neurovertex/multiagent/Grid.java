package eu.neurovertex.multiagent;

import java.util.Optional;

/**
 * @author Neurovertex
 *         Date: 19/12/14
 *         Time: 20:04
 */
public interface Grid {

	default Optional<Element> get(Position pos) {
		return get(pos.getX(), pos.getY());
	}

	Optional<Element> get(int x, int y);

	void set(Position pos, Optional<Element> element);

	interface Element {}

	enum StaticElement implements Element {
		UNKNOWN, OBSTACLE
	}

}
