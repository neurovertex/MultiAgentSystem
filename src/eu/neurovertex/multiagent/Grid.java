package eu.neurovertex.multiagent;

import java.util.List;
import java.util.Optional;

/**
 * @author Neurovertex
 *         Date: 19/12/14
 *         Time: 20:04
 */
public interface Grid {

	default Element get(Position pos) {
		return get(pos.getX(), pos.getY());
	}

	Element get(int x, int y);

	default Optional<Agent> getAgent(int x, int y) {
		if (get(x, y) != StaticElement.AGENT)
			return Optional.empty();
		for (GridAgent agent : getAgents())
			if (agent.getPosition().getX() == x && agent.getPosition().getY() == y)
				return Optional.of(agent.getAgent());
		throw new IllegalStateException("Position has an AGENT but no agent is at this position");
	}

	List<GridAgent> getAgents();

	interface Element {}

	enum StaticElement implements Element {
		EMPTY, UNKNOWN, OBSTACLE, AGENT
	}

}
