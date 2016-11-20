package com.elevator;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import java.util.NavigableSet;

class Elevator {

    public enum Direction {
        UP, DOWN, NONE, WAITING_UP, WAITING_DOWN;
    }

    private static int count = 0;

    private int id, floor, minFloor, maxFloor;
    private Direction direction = Direction.NONE;
    @Inject private NavigableSet<Integer> floorsToStopAt;

    public Elevator(int minFloor, int maxFloor) {
        id = count ++;
        floor = minFloor;
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
        Injector injector = Guice.createInjector(new SimpleSchedulerModule());
        injector.injectMembers(this);
    }

    public void move() {

    }

    public int id() {
        return id;
    }

    public int floor() {
        return floor;
    }

    public Direction direction() {
        return direction;
    }

    public NavigableSet<Integer> getQueue() {
        return floorsToStopAt;
    }

    public void addFloorToQueue(int floor) {
        floorsToStopAt.add(floor);
    }
}
