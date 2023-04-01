package elevators;

public record Passenger(Floor currentFloor, Floor destinationFloor) {
    public Floor getCurrentFloor() {
        return this.currentFloor;
    }

    public Floor getDestinationFloor() {
        return this.destinationFloor;
    }
}
