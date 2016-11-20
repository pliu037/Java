package com.elevator;

import com.google.inject.Inject;

import java.util.Set;

class StreamScheduler extends AbstractScheduler {

    @Inject
    public StreamScheduler(AbstractCostFunction ocf) {
        super(ocf);
    }

    @Override
    public void schedule(Iterable<Elevator> elevators, Set<FloorRequest> requests) {
        for (FloorRequest request : requests) {
            double min = Double.MAX_VALUE;
            Elevator minElevator = null;
            for (Elevator elevator : elevators) {
                double check = costFunc.calculate(elevator, request);
                if (check < min) {
                    min = check;
                    minElevator = elevator;
                }
            }
            minElevator.addFloorToQueue(request.requestFloor());
            minElevator.addFloorToQueue(request.targetFloor());
        }
    }
}
