package elevators;

import java.util.*;


public class Elevator {
    private Floor currentFloor, destinationFloor;
    private Direction currentDirection;
    private final ElevatorSystem system;
    public ArrayList<Floor> stops;
    /// The flag "updated" indicates whether the elevator changes its position in the current step
    // (needed for visualizing the algorithm), so that we only change elevators that
    // actually changed their position instead of changing all of them every time.
    public boolean updated = false;
    ArrayList<Passenger> waitingPassengers = new ArrayList<>(); // The passengers assigned to a given elevator by the system.


    public Elevator(ElevatorSystem system) {
        this.currentDirection = Direction.IDLE;
        this.currentFloor = new Floor(0);
        this.destinationFloor = this.currentFloor;
        this.stops = new ArrayList<>();
        this.system = system;
    }

    public void addStop(Floor floor) { // The function adds a stop if it is not already on the list.
        if (!this.stops.contains(floor)) this.stops.add(floor);
    }

    // The function handles the next step of the elevator depending on its current direction and the stops it needs to make.
    public void move(){
        Floor currHighestStop = findHighestFloor();
        Floor currLowestStop = findLowestFloor();

        switch (this.currentDirection){
            case DOWN -> moveDown(currLowestStop, currHighestStop);
            case UP -> moveUp(currLowestStop, currHighestStop);
            case IDLE -> {
                this.setNewDestination(this.currentFloor);
                this.updated = false;
            }
        }
    }


    public void moveDown(Floor currLowestStop, Floor currHighestStop) {
        Floor prevFloor = this.system.getPrevFloor(this.currentFloor);
        this.setNewDestination(currLowestStop);

        // If there is no floor below (we are on floor 0) or the floor the elevator is
        // currently on is the lowest stop, then we need to change direction.
        // If we are on floor 0, the elevator is now IDLE, and if there is a stop above,
        // the elevator changes direction to UP.
        if (prevFloor == null || currLowestStop.getFloorID() == this.currentFloor.getFloorID()){
            if (currHighestStop.getFloorID() != this.currentFloor.getFloorID()) {
                this.changeDirection(Direction.UP);
                this.setNewDestination(currHighestStop);
            }
            else this.changeDirection(Direction.IDLE);
            this.updated = false;
        }

        else  {   // If none of these cases occur, we simply go down one floor.
            this.changeFloor(prevFloor);
            this.updated = true;
            // If a lower floor is one of the stops, the elevator moves there - we remove that stop.
            // We check and update passengers list
            if (this.stops.contains(prevFloor)) {
                stops.remove(prevFloor);
                this.handlePassengers(prevFloor.getFloorID());
            }
        }

    }

    public void moveUp(Floor currLowestStop, Floor currHighestStop) {
        Floor nextFloor = this.system.getNextFloor(this.currentFloor);
        this.setNewDestination(currHighestStop);

        // If there is no higher floor (we are on the highest possible floor) or we don't have
        // a planned stop on a higher floor, then the elevator changes direction. If there
        // is a stop below, it changes direction to DOWN, and if there isn't, it changes direction to IDLE.
        if (nextFloor == null || currHighestStop.getFloorID() == this.currentFloor.getFloorID()) {
            if (currLowestStop.getFloorID() != this.currentFloor.getFloorID()) {
                this.changeDirection(Direction.DOWN);
                this.setNewDestination(currLowestStop);
            }
            else this.changeDirection(Direction.IDLE);
            this.updated = false;
        }

        else { // If none of these cases occur, we simply go up one floor.
            this.changeFloor(nextFloor);
            this.updated = true;
            // If a higher floor is one of the stops, the elevator moves there - we remove that stop.
            // We check and update passengers list
            if (this.stops.contains(nextFloor)) {
                stops.remove(nextFloor);
                this.handlePassengers(nextFloor.getFloorID());
            }
        }
    }
    // Finding the highest/lowest floor in the current elevator's stop list.
    public Floor findLowestFloor(){
        int lowest = this.currentFloor.getFloorID();
        Floor lowestFloor = this.currentFloor;
        for (Floor floor : this.stops) {
            if (floor.getFloorID() < lowest) {
                lowest = floor.getFloorID();
                lowestFloor = floor;
            }
        }
        return lowestFloor;
    }

    public Floor findHighestFloor(){
        int highest = this.currentFloor.getFloorID();
        Floor highestFloor = this.currentFloor;
        for (Floor floor : this.stops) {
            if (floor.getFloorID() > highest) {
                highest = floor.getFloorID();
                highestFloor = floor;
            }
        }
        return highestFloor;
    }

    // We check the list of passengers. If the elevator has a stop at a particular floor,
    // it means that either a passenger is waiting there or it is destination floor.
    public void handlePassengers(int floorID){
        ArrayList<Passenger> toUpdate = new ArrayList<>();
        for (Passenger passenger: this.waitingPassengers){
            if (passenger.getCurrentFloor().getFloorID() == floorID){
                Floor destinationFloor = passenger.getDestinationFloor();
                // If it is not the destination floor (only a floor where someone is waiting),
                // we remove that floor from the list of stops and add the passenger's destination
                // floor (if it is not already in the stops list).
                if (!this.stops.contains(destinationFloor)) {
                    this.addStop(destinationFloor);
                    toUpdate.add(passenger);
                }
            }
        }
        for (Passenger passenger:toUpdate) this.waitingPassengers.remove(passenger); // avoiding concurrent modification error
    }

    // setters and getters below
    public void setNewDestination(Floor floor){
        this.destinationFloor = floor;
    }
    public Floor getCurrentFloor() {
        return this.currentFloor;
    }

    public Floor getDestinationFloor(){
        return this.destinationFloor;
    }

    public Direction getCurrentDirection() {
        return this.currentDirection;
    }

    public void changeDirection(Direction direction){
        this.currentDirection = direction;
    }

    public void changeFloor(Floor floor){
        this.currentFloor = floor;
    }
}
