package com.elevator;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import java.util.HashMap;
import java.util.Set;

public class ElevatorControlSystem {

    private int round = 1;
    private HashMap<Integer, Elevator> elevators;
    private Set<FloorRequest> requests;
    private int minFloor = 1;
    private int maxFloor = 20;
    private AbstractScheduler scheduler;
    private boolean started;

    @Inject
    public ElevatorControlSystem(AbstractScheduler as, Set<FloorRequest> requests) {
        scheduler = as;
        this.requests = requests;
        getElevators(4);
    }

    public void setProperties(int numElevators, int minFloor, int maxFloor) {
        if (numElevators < 1 || minFloor >= maxFloor) {
            System.out.println("Invalid properties.");
            return;
        }
        if (!started) {
            this.minFloor = minFloor;
            this.maxFloor = maxFloor;
            getElevators(numElevators);
        } else {
            System.out.println("Properties are set once simulation has started.");
        }
    }

    public void requestAtFloor(int requestFloor, int targetFloor) {
        started = true;
        if (isValidFloor(requestFloor) && isValidFloor(targetFloor) && requestFloor != targetFloor) {
            requests.add(new FloorRequest(requestFloor, targetFloor));
        } else {
            System.out.println("Invalid request.");
        }
    }

    public void simulateNextMove() {
        System.out.println("Round: " + round ++);
        started = true;
        scheduler.schedule(elevators.values(), requests);
        elevators.values().forEach(Elevator::move);
        printAllStats();
    }

    public void printElevatorStats(int id) {
        if (elevators.containsKey(id)) {
            System.out.println(elevators.get(id));
        } else {
            System.out.println("An elevator with id " + id + " does not exist.");
        }
    }

    private void getElevators(int numElevators) {
        elevators = new HashMap<>();
        for (int i = 0; i < numElevators; i ++) {
            Elevator elevator = new Elevator(minFloor, maxFloor);
            elevators.put(elevator.id(), elevator);
        }
        printAllIds();
    }

    private void printAllStats() {
        elevators.values().forEach(System.out::println);
    }

    private void printAllIds() {
        System.out.print("The following elevator IDs exist: ");
        elevators.keySet().forEach((e) -> System.out.print(e + " "));
        System.out.println();
    }

    private boolean isValidFloor(int floor) {
        if (minFloor <= floor && floor <= maxFloor) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new SimpleModule());
        ElevatorControlSystem ecs = injector.getInstance(ElevatorControlSystem.class);
        ecs.requestAtFloor(2, 10);
        ecs.requestAtFloor(3, 5);
        ecs.simulateNextMove();
        ecs.simulateNextMove();
        ecs.simulateNextMove();
        ecs.simulateNextMove();
        ecs.requestAtFloor(7, 8);
        ecs.simulateNextMove();
        ecs.requestAtFloor(4, 1);
        ecs.simulateNextMove();
        ecs.simulateNextMove();
        ecs.simulateNextMove();
        ecs.simulateNextMove();
    }
}
