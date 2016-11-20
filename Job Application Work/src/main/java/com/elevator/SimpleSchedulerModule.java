package com.elevator;

import com.google.inject.*;

import java.util.*;

public class SimpleSchedulerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(AbstractScheduler.class).to(StreamScheduler.class).in(Singleton.class);
        bind(AbstractCostFunction.class).to(SimpleCostFunction.class).in(Singleton.class);
    }

    @Provides @ElevatorControlSystem.ElevatorsSet
    public Set<Elevator> providesElevatorsSet() {
        return new LinkedHashSet<>();
    }

    @Provides @ElevatorControlSystem.RequestsSet
    public Set<FloorRequest> providesRequestsSet() {
        return new LinkedHashSet<>();
    }

    @Provides
    public NavigableSet<Integer> providesFloorsToStopAtSet() {
        return new TreeSet<>();
    }
}
