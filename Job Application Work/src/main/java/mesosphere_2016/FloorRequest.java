package mesosphere_2016;

public class FloorRequest {

    private int requestFloor, targetFloor;

    public FloorRequest(int requestFloor, int targetFloor) {
        this.requestFloor = requestFloor;
        this.targetFloor = targetFloor;
    }

    public int requestFloor() {
        return requestFloor;
    }

    public int targetFloor() {
        return targetFloor;
    }

    @Override
    public String toString() {
        return requestFloor + ":" + targetFloor;
    }

    @Override
    public int hashCode() {
        return 17*31 + 31*requestFloor + targetFloor;
    }

    @Override
    public boolean equals(Object other) {
        if (other != null && other.getClass() == FloorRequest.class) {
            FloorRequest check = (FloorRequest) other;
            return this.requestFloor == check.requestFloor && this.targetFloor == check.targetFloor;
        }
        return false;
    }
}
