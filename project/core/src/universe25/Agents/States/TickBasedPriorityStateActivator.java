package universe25.Agents.States;

import universe25.Agents.Agent;
import universe25.GameLogic.NumberProducers.NumberProducer;
import universe25.GameLogic.Time.Ticks;

import java.util.function.BooleanSupplier;

/**
 * Created by jorl17 on 09/08/15.
 */
public class TickBasedPriorityStateActivator<T extends Agent> extends StateWithPriority<T> {
    private State<T> state;
    private int priorityToSet;
    private BooleanSupplier shouldTickFunction;
    private NumberProducer<Long> ticksUntilActivation;
    private NumberProducer<Long> ticksUntilDeActivation;
    private boolean deActiveIfTicks;

    private long lastTimeTicked;
    private boolean currentlyActive;
    private boolean shouldLeave;

    public TickBasedPriorityStateActivator(T agent, String name, int priorityToSet, State<T> state,
                                           BooleanSupplier shouldTickFunction, NumberProducer<Long> ticksUntilActivation,
                                           NumberProducer<Long> ticksUntilDeActivation, boolean deActiveIfTicks) {
        super(agent, name, -1);
        this.state = state;
        this.priorityToSet = priorityToSet;
        this.shouldTickFunction = shouldTickFunction;
        this.ticksUntilActivation = ticksUntilActivation;
        this.ticksUntilDeActivation = ticksUntilDeActivation;
        this.lastTimeTicked = Ticks.getTicks();
        this.shouldLeave = false;
        this.currentlyActive = false;
        this.deActiveIfTicks = deActiveIfTicks;
    }

    @Override
    public void updatePriority() {

        if ( currentlyActive ) {
            if ( ( deActiveIfTicks && shouldTickFunction.getAsBoolean() ) ||
               (Ticks.ticksSince(lastTimeTicked) > ticksUntilDeActivation.produce()) ) {
                setPriority(0);
                lastTimeTicked = Ticks.getTicks();
                shouldLeave = true;
                currentlyActive = false;
            }
        } else {
            if (shouldTickFunction.getAsBoolean()) lastTimeTicked = Ticks.getTicks();

            if (Ticks.ticksSince(lastTimeTicked) > ticksUntilActivation.produce()) {
                currentlyActive = true;
                setPriority(priorityToSet);
                lastTimeTicked = Ticks.getTicks();
            }
        }
    }

    @Override
    public String update() {
        if ( currentlyActive ) state.update();
        return null;
    }

    @Override
    public void leaveState() {
        if ( currentlyActive || shouldLeave ) state.leaveState();
    }

    @Override
    public void enterState() {
        if ( currentlyActive ) state.enterState();
    }

    @Override
    public String toString() {
        return "TickBasedPriorityStateActivator{" +
                "state=" + state +
                ", priorityToSet=" + priorityToSet +
                ", shouldTickFunction=" + shouldTickFunction +
                ", ticksUntilActivation=" + ticksUntilActivation +
                ", ticksUntilDeActivation=" + ticksUntilDeActivation +
                ", lastTimeTicked=" + lastTimeTicked +
                ", currentlyActive=" + currentlyActive +
                ", shouldLeave=" + shouldLeave +
                '}';
    }
}
