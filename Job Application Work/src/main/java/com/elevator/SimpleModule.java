package com.elevator;

import com.google.inject.*;

import java.util.*;

public class SimpleModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(AbstractScheduler.class).to(InstantStreamScheduler.class).in(Singleton.class);
        bind(AbstractCostFunction.class).to(SimpleCostFunction.class).in(Singleton.class);
    }

    @Provides
    public Set<FloorRequest> providesRequestsSet() {
        return new LinkedHashSet<>();
    }

    @Provides
    public NavigableSet<Integer> providesFloorsToStopAtSet() {
        return new TreeSet<>();
    }
}
