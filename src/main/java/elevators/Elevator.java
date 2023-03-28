package elevators;

import java.util.*;

public class Elevator {
    private Floor currentFloor;
    private Direction currentDirection;
    private final ElevatorSystem system;
    private LinkedList<Passenger> passengersIn;


    public Elevator(ElevatorSystem system) {
        this.currentDirection = Direction.IDLE;
        this.currentFloor = new Floor(0);
        this.passengersIn = new LinkedList<>();
        this.system = system;
    }

    public void changeDirection(Direction direction){
        this.currentDirection = direction;
    }

    public void changeFloor(Floor floor){
        this.currentFloor = floor;
    }

    private void look(){
        SortedSet<Floor> newStopsConsistent = new TreeSet<>(Comparator.comparingInt(Floor::getFloorID));
        SortedSet<Floor> newStopsOpposite = new TreeSet<>(Comparator.comparingInt(Floor::getFloorID));
        ArrayList<Floor> stops = new ArrayList<>();

        int direction = switch(this.currentDirection){
            case UP -> 1;
            case DOWN -> -1;
            case IDLE -> 0;
        };

        // prepare Set with floors in the same direction as an elevator;
        ArrayList<Floor> allFloors = this.system.getPassengersFullFloors();
        for (Floor floor: allFloors){
            if (direction * (floor.getFloorID() - this.currentFloor.getFloorID()) > 0){
                newStopsConsistent.add(floor);
            }
        }

    }

    public void letPassengersIn(){
        LinkedList<Passenger> allPassengers = new LinkedList<>(this.system.getPassengersAt(this.currentFloor));
        for (Passenger passenger: allPassengers){
            if (passenger.getCurrentFloorID() == this.currentFloor.getFloorID()) {
                this.passengersIn.add(passenger);
                this.system.removePassenger(passenger);
            }
        }
    }
}
