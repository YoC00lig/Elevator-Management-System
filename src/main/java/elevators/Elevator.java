package elevators;

import java.util.*;


public class Elevator {
    private Floor currentFloor, destinationFloor;
    private Direction currentDirection;
    private final ElevatorSystem system;
    private ArrayList<Floor> stops;
    public boolean updated = false;


    public Elevator(ElevatorSystem system) {
        this.currentDirection = Direction.IDLE;
        this.currentFloor = new Floor(0);
        this.destinationFloor = this.currentFloor;
        this.stops = new ArrayList<>();
        this.system = system;
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
        if (this.currentDirection == Direction.IDLE) {
            this.updated = false;
            return;
        }

        else if (this.currentDirection == Direction.UP){
            Floor nextFloor = this.system.getNextFloor(this.currentFloor);
            if (nextFloor == null || checkIfHigherStopExists(this.getCurrentFloor().getFloorID()) == -1) {
                if (checkIfLowerStopExists(this.getCurrentFloor().getFloorID()) != -1) this.changeDirection(Direction.DOWN);
                else this.changeDirection(Direction.IDLE);
                this.updated = false;
            }
            else if (this.stops.contains(nextFloor)){
                stops.remove(nextFloor);
                this.changeFloor(nextFloor);
                this.updated = true;
            }
            else {
                this.changeFloor(nextFloor);
                this.updated = true;
            }
        }
        else  {
            Floor prevFloor = this.system.getPrevFloor(this.currentFloor);
            if (prevFloor == null || checkIfLowerStopExists(this.getCurrentFloor().getFloorID()) == -1){
                if (checkIfHigherStopExists(this.getCurrentFloor().getFloorID()) != -1) this.changeDirection(Direction.UP);
                else this.changeDirection(Direction.IDLE);
                this.updated = false;
            }
            else if (this.stops.contains(prevFloor)) {
                stops.remove(prevFloor);
                this.changeFloor(prevFloor);
                this.updated = true;
            }
            else {
                this.changeFloor(prevFloor);
                this.updated = true;
            }
        }
    }

    public int checkIfHigherStopExists(int floorID){
        if (!this.stops.isEmpty()) {
            for (Floor floor : this.stops) {
                if (floor.getFloorID() > floorID) return floor.getFloorID();
            }
        }
        return -1;
    }

    public int checkIfLowerStopExists(int floorID){
        if (!this.stops.isEmpty()) {
            for (Floor floor : this.stops) {
                if (floor.getFloorID() < floorID) return floor.getFloorID();
            }
        }
        return -1;
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
