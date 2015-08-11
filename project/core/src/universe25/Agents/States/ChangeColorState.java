package universe25.Agents.States;

import com.badlogic.gdx.graphics.Color;
import universe25.Agents.Agent;

/**
 * Created by jorl17 on 09/08/15.
 */
public class ChangeColorState<T extends Agent> extends StateWithPriority<T> {
    private Color c;
    private Color originalColor;
    public ChangeColorState(T agent, String name, int priority, Color c) {
        super(agent, name, priority);
        this.c = c;

    }
    public ChangeColorState(T agent, Color c) {
        super(agent, "ChangeColorState", -1);
        this.c = c;
    }

    @Override
    public String update() {
        return null;
    }

    @Override
    public void leaveState() {
        agent.setColor(originalColor);
    }

    @Override
    public void enterState() {
        this.originalColor = agent.getColor().cpy();
        agent.setColor(c);
    }

    @Override
    public void updatePriority() {

    }
}
