package elevators;

import java.util.*;


public class Elevator {
    private Floor currentFloor, destinationFloor;
    private Direction currentDirection;
    private final ElevatorSystem system;
    private ArrayList<Floor> stops;
    private LinkedList<Passenger> passengersIn;


    public Elevator(ElevatorSystem system) {
        this.currentDirection = Direction.IDLE;
        this.currentFloor = new Floor(0);
        this.destinationFloor = this.currentFloor;
        this.passengersIn = new LinkedList<>();
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

    public void letPassengerIn(Passenger passenger){
        this.system.removePassenger(passenger);
        this.passengersIn.add(passenger);
    }

    public void letPassengerOut(Passenger passenger) {
        int idx = passengersIn.indexOf(passenger);
        this.passengersIn.remove(idx);
    }

    public void move(){
//        if (this.currentDirection == Direction.UP){
//            if (this.currentFloor.getFloorID() + 1 < system.numberOfFloors){
//                this.currentFloor = nextFloor(this.currentFloor);
//            }
//        }
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
