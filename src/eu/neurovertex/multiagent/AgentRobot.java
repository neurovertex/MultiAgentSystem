package eu.neurovertex.multiagent;

import java.util.Map;

/**
 * Created by benji on 20/01/15.
 */
public class AgentRobot implements Agent {

    public AgentRobot() {
        
    }

    @Override
    public Decision takeDecision(LocalGrid grid) {
        return null;
    }

    @Override
    public int getPerceptionRadius() {
        return 0;
    }

    @Override
    public int getCommunicationRadius() {
        return 0;
    }

    @Override
    public Map<String, Object> communicate() {
        return null;
    }

    @Override
    public void moveForward() {

    }

    @Override
    public void rotate(Direction dir) {

    }
}
