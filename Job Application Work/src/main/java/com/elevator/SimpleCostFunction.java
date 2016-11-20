package com.elevator;

public class SimpleCostFunction extends AbstractCostFunction {

    @Override
    public double calculate(Elevator elevator, FloorRequest request) {
        int sum = 0;
        if (elevator.getQueue().contains(request.requestFloor())) {
            sum ++;
        }
        if (elevator.getQueue().contains(request.targetFloor())) {
            sum ++;
        }
        sum += elevator.getQueue().size();
        return sum;
    }
}
