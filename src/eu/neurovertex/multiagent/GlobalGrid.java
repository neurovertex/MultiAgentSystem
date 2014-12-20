package eu.neurovertex.multiagent;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Neurovertex
 *         Date: 19/12/14
 *         Time: 20:22
 */
public class GlobalGrid implements Grid {
	private final Map<Position, Element> map;
	private final int width, height;

	public GlobalGrid(int width, int height) {
		this.width = width;
		this.height = height;
		map = new HashMap<>();
	}


	@Override
	public Optional<Element> get(int x, int y) {
		return (x >= 0 && x < width && y >= 0 && y < height) ?
				Optional.ofNullable(map.get(new Position(x, y))) : Optional.of(StaticElement.UNKNOWN);
	}

	@Override
	public List<GridElement> getAgents() {
		return map.entrySet().stream().filter(entry -> entry.getValue() instanceof Agent)
				.map(entry -> new GridElement(this, entry.getKey(), entry.getValue())).collect(Collectors.toList());
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void set(Position pos, Optional<Element> element) {
		element.map(e -> map.put(pos, e)).orElse(map.remove(pos));
	}
}
