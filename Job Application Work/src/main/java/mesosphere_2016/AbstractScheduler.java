package mesosphere_2016;

import java.util.Set;

public abstract class AbstractScheduler {

    protected AbstractCostFunction costFunc;

    public AbstractScheduler(AbstractCostFunction costFunc) {
        this.costFunc = costFunc;
    }

    public abstract void schedule(Iterable<Elevator> elevators, Set<FloorRequest> requests);
}
