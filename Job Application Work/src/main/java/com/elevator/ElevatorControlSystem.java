package com.elevator;

import com.google.inject.BindingAnnotation;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Set;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class ElevatorControlSystem {

    @BindingAnnotation
    @Target({ PARAMETER, METHOD })
    @Retention(RUNTIME)
    public @interface ElevatorsSet {}

    @BindingAnnotation
    @Target({ PARAMETER, METHOD })
    @Retention(RUNTIME)
    public @interface RequestsSet {}

    private Set<Elevator> elevators;
    private Set<FloorRequest> requests;
    private int minFloor = 1;
    private int maxFloor = 20;
    private AbstractScheduler scheduler;
    private boolean started;

    @Inject
    public ElevatorControlSystem(AbstractScheduler as, @ElevatorsSet Set<Elevator> elevators,
                                 @RequestsSet Set<FloorRequest> requests) {
        scheduler = as;
        this.elevators = elevators;
        this.requests = requests;
        for (int i = 0; i < 10; i ++) {
            elevators.add(new Elevator(minFloor, maxFloor));
        }
    }

    public void setProperties(int numElevators, int minFloor, int maxFloor) {
        if (numElevators < 1 || minFloor >= maxFloor) {
            System.out.println("Invalid properties.");
            return;
        }
        if (!started) {
            this.minFloor = minFloor;
            this.maxFloor = maxFloor;
            for (int i = 0; i < numElevators; i ++) {
                elevators.add(new Elevator(minFloor, maxFloor));
            }
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
        started = true;
        scheduler.schedule(elevators, requests);
        for (Elevator elevator : elevators) {
            elevator.move();
        }
        printStats();
    }

    private void printStats() {
        for (Elevator elevator : elevators) {
            System.out.print(elevator.id() + ": on floor " + elevator.floor() + " with the following floors enqueued: ");
            for (Integer floor : elevator.getQueue()) {
                System.out.print(floor + " ");
            }
            System.out.println();
        }
    }

    private boolean isValidFloor(int floor) {
        if (minFloor <= floor && floor <= maxFloor) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new SimpleSchedulerModule());
        ElevatorControlSystem ecs = injector.getInstance(ElevatorControlSystem.class);
        ecs.requestAtFloor(2, 10);
        ecs.requestAtFloor(3, 5);
        ecs.simulateNextMove();
    }
}
