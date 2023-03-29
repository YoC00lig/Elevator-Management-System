package elevators;

public class Passenger {
    private final Floor currentFloor, destinationFloor;

    public Passenger(Floor currentFloor, Floor destinationFloor){
        this.currentFloor = currentFloor;
        this.destinationFloor = destinationFloor;
    }

    public Floor getCurrentFloor() {
        return this.currentFloor;
    }

    public Floor getDestinationFloor() {
        return this.destinationFloor;
    }
}
