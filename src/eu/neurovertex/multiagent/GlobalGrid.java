package eu.neurovertex.multiagent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Neurovertex
 *         Date: 19/12/14
 *         Time: 20:22
 */
public class GlobalGrid implements Grid {
	private final Element[][] map;
	private final Map<Agent, Position> agents = new HashMap<>();
	private final int width, height;

	public GlobalGrid(int width, int height) {
		this.width = width;
		this.height = height;
		map = new Element[width][height];
	}


	@Override
	public Optional<Element> get(int x, int y) {
		try {
			return Optional.ofNullable(map[x][y]);
		} catch (NullPointerException ex) {
			return Optional.ofNullable(StaticElement.UNKNOWN);
		}
	}

	@Override
	public List<GridAgent> getAgents() {
		return agents.entrySet().stream().map(e -> new GridAgent(this, e.getValue(), e.getKey()))
				.collect(Collectors.toList());
	}

	public void addAgent(Agent a, Position pos) {
		if (agents.containsKey(a))
			throw new IllegalArgumentException("Agent already in collection");
		agents.put(a, pos);
	}

	public void moveAgent(Agent a, Position pos) {
		if (!agents.containsKey(a))
			throw new IllegalArgumentException("Agent not in collection");
		agents.put(a, pos);
	}

	public void removeAgent(Agent a) {
		agents.remove(a);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void set(Position pos, Optional<Element> element) {
		map[pos.getX()][pos.getY()] = element.orElse(null);
	}
}
