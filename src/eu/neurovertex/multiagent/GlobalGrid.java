package eu.neurovertex.multiagent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import java.util.*;

import static eu.neurovertex.multiagent.Grid.StaticElement.AGENT;
import static eu.neurovertex.multiagent.Grid.StaticElement.EMPTY;
/**
 * @author Neurovertex
 *         Date: 19/12/14
 *         Time: 20:22
 */
public class GlobalGrid implements Grid {
	private final Element[][] grid;
	private final Map<Agent, Position> agents = new HashMap<>();
	private final int width, height;

	private EventHandler<? super CellChanged> onCellChanged;

	public GlobalGrid(int width, int height) {
		this.width = width;
		this.height = height;
		grid = new Element[width][height];
		for (Element[] line : grid)
			Arrays.fill(line, EMPTY);
	}

	public Position getPosAgent(Agent a) {
		return agents.get(a);
	}

	public ArrayList<String> getStaticElements() {
		ArrayList<String> elements = new ArrayList<String>();
		for (int i = 0; i < StaticElement.values().length; i++) {
			elements.add(StaticElement.values()[i].name());
		}
		return elements;
	}

	@Override
	public Element get(int x, int y) {
		return grid[x][y];
	}

	public void set(int x, int y, Element e) {
		grid[x][y] = e;
	}

	@Override
	public List<GridAgent> getAgents() {
		return agents.entrySet().stream().map(e -> new GridAgent(this, e.getValue(), e.getKey()))
				.collect(Collectors.toList());
	}

	public void addAgent(Agent a, Position pos) {
		if (agents.containsKey(a))
			throw new IllegalArgumentException("Agent already in collection");
		if (grid[pos.getX()][pos.getY()] != EMPTY)
			throw new IllegalArgumentException("Position not empty");
		agents.put(a, pos);
		grid[pos.getX()][pos.getY()] = AGENT;
	}

	public void moveAgent(Agent a, Position pos) {
		if (!agents.containsKey(a))
			throw new IllegalArgumentException("Agent not in collection");
		if (grid[pos.getX()][pos.getY()] != EMPTY)
			throw new IllegalArgumentException("Position not empty");
		Position old = agents.get(a);
		grid[old.getX()][old.getY()] = EMPTY;
		agents.put(a, pos);
		grid[pos.getX()][pos.getY()] = AGENT;
	}

	public void removeAgent(Agent a) {
		Position pos = agents.remove(a);
		grid[pos.getX()][pos.getY()] = EMPTY;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public EventHandler<? super CellChanged> getOnCellChanged() {
		return onCellChanged;
	}

	public void setOnCellChanged(EventHandler<? super CellChanged> onCellChanged) {
		this.onCellChanged = onCellChanged;
	}

	public void set(Position pos, Element element) {
		if (element == null)
			throw new IllegalArgumentException("Can't pass null element to set()");
		grid[pos.getX()][pos.getY()] = element;
	}

	public static class CellChanged extends Event {
		public static final EventType<CellChanged> CELL_CHANGED = new EventType<CellChanged>("CELL_CHANGED");
		private int x, y;
		private boolean live;

		public CellChanged(int x, int y, boolean live) {
			super(CELL_CHANGED);
			this.x = x;
			this.y = y;
			this.live = live;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public boolean isLive() {
			return live;
		}
	}
}
