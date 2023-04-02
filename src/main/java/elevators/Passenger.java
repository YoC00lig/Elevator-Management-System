package elevators;

// A record represents a passenger and contains information about their current and destination position.
public record Passenger(Floor currentFloor, Floor destinationFloor) {
    public Floor getCurrentFloor() {
        return this.currentFloor;
    }

    public Floor getDestinationFloor() {
        return this.destinationFloor;
    }
}
