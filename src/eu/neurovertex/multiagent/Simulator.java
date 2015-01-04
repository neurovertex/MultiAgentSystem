package eu.neurovertex.multiagent;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Neurovertex
 *         Date: 30/12/2014, 08:48
 */
public class Simulator implements Runnable {
	private final GlobalGrid grid;


	public Simulator(GlobalGrid grid) {
		this.grid = grid;
	}

	public void iterate() {
		List<GridAgent> agents = grid.getAgents();
		Map<GridAgent, Agent.Decision> decisions = new HashMap<>();

		for (GridAgent element : agents) {
			Agent agent = element.getAgent();
			Position pos = element.getPosition();
			LocalGrid local = new LocalGrid(grid, pos.getX(), pos.getY(), Direction.values()[pos.getDirection().get().ordinal()], agent.getPerceptionRadius());
			decisions.put(element, agent.takeDecision(local));
		}

		Collections.shuffle(agents);

		for (GridAgent agent : agents) {
			Agent.Decision decision = decisions.get(agent);
			Position pos = agent.getPosition();
			Cardinal newDir = pos.getDirection().get().rotate(decision.getRotation());
			Position newPos = new Position(pos.getX(), pos.getY(), newDir);
			agent.getAgent().rotate(decision.getRotation());
			if (decision.getMove() && grid.get(newPos.forward(1)).isPresent()) {
				newPos = newPos.forward(1);
				agent.getAgent().moveForward();
			}
			grid.moveAgent(agent.getAgent(), newPos);
		}
	}

	@Override
	public void run() {

	}
}
