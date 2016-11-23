package com.elevator;

public class SimpleCostFunction extends AbstractCostFunction {

    @Override
    public double calculate(Elevator elevator, FloorRequest request) {
        // Elevators that cannot reach the requesting floor are disqualified
        if (elevator.minFloor() > request.requestFloor() || elevator.maxFloor() < request.requestFloor()) {
            return Integer.MAX_VALUE;
        }

        // Weighs the decision against elevators that are further
        double cost = Math.abs(elevator.floor() - request.requestFloor());

        // If the elevator has to go up/down to reach the requesting floor but is currently going down/up, adjusts the
        // cost from the previous step to account for the round-trip
        if (elevator.direction() == Elevator.Direction.UP && elevator.floor() > request.requestFloor()) {
            int highest = elevator.getQueue().pollLast();
            elevator.addFloorToQueue(highest);
            cost += highest - elevator.floor();
        } else if (elevator.direction() == Elevator.Direction.DOWN && elevator.floor() < request.requestFloor()) {
            int lowest = elevator.getQueue().pollFirst();
            elevator.addFloorToQueue(lowest);
            cost += elevator.floor() - lowest;
        }

        // Weighs the decision toward idle elevators
        if (elevator.direction() == Elevator.Direction.NONE) {
            cost -= 1;
        }

        // Weighs the decision against elevators that have more floors queued
        cost += elevator.getQueue().size();

        return cost;
    }
}
