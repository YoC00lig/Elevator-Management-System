package elevators;

public class Passenger {
    private Floor currentFloor, destinationFloor;

    public Passenger(Floor currentFloor, Floor destinationFloor){
        this.currentFloor = currentFloor;
        this.destinationFloor = destinationFloor;
    }

    public Floor getCurrentFloor() {
        return this.currentFloor;
    }

    public int getCurrentFloorID() {
        return this.currentFloor.getFloorID();
    }

    public Floor getDestinationFloor() {
        return this.destinationFloor;
    }
}
