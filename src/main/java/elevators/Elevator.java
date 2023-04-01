package elevators;

import java.util.*;


public class Elevator {
    private Floor currentFloor, destinationFloor;
    private Direction currentDirection;
    private final ElevatorSystem system;
    public ArrayList<Floor> stops;
    public boolean updated = false;
    public Floor nextStep;
    ArrayList<Passenger> waitingPassengers = new ArrayList<>();


    public Elevator(ElevatorSystem system) {
        this.currentDirection = Direction.IDLE;
        this.currentFloor = new Floor(0);
        this.destinationFloor = this.currentFloor;
        this.stops = new ArrayList<>();
        this.system = system;
        this.nextStep = null;
    }

    public void addStop(Floor floor) {
        if (!this.stops.contains(floor)) this.stops.add(floor);
    }

    public void changeDirection(Direction direction){
        this.currentDirection = direction;
    }

    public void changeFloor(Floor floor){
        this.currentFloor = floor;
    }

    public void move(){
        Floor currHighestStop = findHighestFloor();
        Floor currLowestStop = findLowestFloor();

        if (this.currentDirection == Direction.IDLE) {
            this.setNewDestination(this.currentFloor);
            this.updated = false;
        }

        else if (this.currentDirection == Direction.UP){
            Floor nextFloor = this.system.getNextFloor(this.currentFloor);
            this.setNewDestination(currHighestStop);
            if (nextFloor == null || currHighestStop.getFloorID() == this.currentFloor.getFloorID()) {
                if (findLowestFloor().getFloorID() != this.currentFloor.getFloorID()) {
                    this.changeDirection(Direction.DOWN);
                    this.setNewDestination(this.findLowestFloor());
                }
                else this.changeDirection(Direction.IDLE);
                this.updated = false;
            }
            else if (this.stops.contains(nextFloor)){
                stops.remove(nextFloor);
                this.changeFloor(nextFloor);
                this.letPassengersIn(nextFloor.getFloorID());
                this.updated = true;
            }
            else if (currHighestStop.getFloorID() != this.currentFloor.getFloorID()) {
                this.changeFloor(nextFloor);
                this.updated = true;
            }
        }
        else  {
            Floor prevFloor = this.system.getPrevFloor(this.currentFloor);
            this.setNewDestination(currLowestStop);
            if (prevFloor == null || currLowestStop.getFloorID() == this.currentFloor.getFloorID()){
                if (currHighestStop.getFloorID() != this.currentFloor.getFloorID()) {
                    this.changeDirection(Direction.UP);
                    this.setNewDestination(this.findHighestFloor());
                }
                else this.changeDirection(Direction.IDLE);
                this.updated = false;
            }
            else if (this.stops.contains(prevFloor)) {
                stops.remove(prevFloor);
                this.changeFloor(prevFloor);
                this.letPassengersIn(prevFloor.getFloorID());
                this.updated = true;
            }
            else if (currLowestStop.getFloorID() != this.currentFloor.getFloorID()){
                this.changeFloor(prevFloor);
                this.updated = true;
            }
        }
    }

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

    public void letPassengersIn(int floorID){
        ArrayList<Passenger> toUpdate = new ArrayList<>();
        for (Passenger passenger: this.waitingPassengers){
            if (passenger.getCurrentFloor().getFloorID() == floorID){
                Floor destinationFloor = passenger.getDestinationFloor();
                if (!this.stops.contains(destinationFloor)) {
                    this.stops.add(destinationFloor);
                    toUpdate.add(passenger);
                }
            }
        }
        for (Passenger passenger:toUpdate) this.waitingPassengers.remove(passenger);
    }

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
}
