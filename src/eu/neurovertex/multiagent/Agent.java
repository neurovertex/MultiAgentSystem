package eu.neurovertex.multiagent;

import java.util.Map;

/**
 * @author Neurovertex
 *         Date: 20/12/14
 *         Time: 23:08
 */
public interface Agent {
	Decision takeDecision(LocalGrid grid);

	int getPerceptionRadius();

	int getCommunicationRadius();

	Map<String, Object> communicate();

	void moveForward();

	void rotate(Direction dir);

	public class Decision {
		private final Direction rotation;
		private final boolean move;

		public Decision(Direction rotation, boolean move) {
			this.rotation = rotation;
			this.move = move;
		}

		public Direction getRotation() {
			return rotation;
		}

		public boolean getMove() {
			return move;
		}
	}
}
