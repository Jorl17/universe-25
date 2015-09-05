package universe25.Agents.States;

import universe25.Agents.Agent;
import universe25.GameLogic.Time.Ticks;

/**
 * Created by jorl17 on 05/09/15.
 */
public class TickBasedTimedState<T extends Agent> extends ToggablePriorityState<T> {
    private long ticksToSleep;
    private long startTicks;
    //FIXME: Add the possibility of firing an event or a function when the timer runs out?
    protected TickBasedTimedState(T agent, String name, int priorityWhenToggled, long ticksToSleep) {
        super(agent, name, priorityWhenToggled);
        this.ticksToSleep = ticksToSleep;
        this.startTicks = -1;
    }

    // Note that start doesn't really start the state, it only makes it reachable.
    // This means that only after it has been enter()-ed, can we start counting
    public void start() {
        makeReachable();
    }

    @Override
    public void updatePriority() {
        //Nothing
    }

    @Override
    public String update() {
        if (Ticks.ticksSince(startTicks) >= ticksToSleep )
            makeUnreachable();
        return null;
    }

    @Override
    public void leaveState() {
        startTicks = -1;
    }

    @Override
    public void enterState() {
        startTicks = Ticks.getTicks();
    }
}
