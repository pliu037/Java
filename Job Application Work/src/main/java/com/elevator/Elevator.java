package com.elevator;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import java.util.NavigableSet;

public class Elevator {

    public enum Direction {
        UP,
        DOWN,
        NONE,
    }

    private static int count = 0;

    private int id, floor, minFloor, maxFloor;
    private Direction direction = Direction.NONE;
    private boolean waiting = false;
    @Inject private NavigableSet<Integer> floorsToStopAt;

    public Elevator(int minFloor, int maxFloor) {
        id = count ++;
        floor = minFloor;
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
        Injector injector = Guice.createInjector(new SimpleModule());
        injector.injectMembers(this);
    }

    public void move() {
        if (waiting) {
            if (!floorsToStopAt.remove(floor)) {
                waiting = false;
            }
        } else {
            if (direction == Direction.UP) {
                floor++;
            } else if (direction == Direction.DOWN) {
                floor--;
            }
            if (floorsToStopAt.remove(floor)) {
                waiting = true;
            }
        }
        if (floorsToStopAt.isEmpty()) {
            direction = Direction.NONE;
        } else if (floorsToStopAt.headSet(floor, false).isEmpty()) {
            direction = Direction.UP;
        } else if (floorsToStopAt.tailSet(floor, false).isEmpty()) {
            direction = Direction.DOWN;
        }
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
        if (floorsToStopAt.isEmpty() || (floorsToStopAt.size() == 1 && floorsToStopAt.contains(this.floor))) {
            if (this.floor < floor) {
                direction = Direction.UP;
            } else if (this.floor > floor) {
                direction = Direction.DOWN;
            }
        }
        floorsToStopAt.add(floor);
    }

    public int minFloor() {
        return minFloor;
    }

    public int maxFloor() {
        return maxFloor;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id);
        sb.append(": on floor ");
        sb.append(floor);
        sb.append(" in state ");
        sb.append(direction);
        sb.append("_");
        sb.append(waiting ? "Waiting" : "Not_Waiting");
        sb.append(" with the following floors enqueued: ");
        for (Integer floor : floorsToStopAt) {
            sb.append(floor);
            sb.append(" ");
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object other) {
        return other != null && other.getClass() == Elevator.class && this.id == ((Elevator) other).id;
    }
}
