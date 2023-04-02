package elevators;

import java.util.ArrayList;
import java.util.Objects;

public class ElevatorSystem {
    public ArrayList<Floor> floors;
    public ArrayList<Elevator> elevators;
    public int numberOfFloors, numberOfElevators;

    public ElevatorSystem(int numberOfFloors, int numberOfElevators){
        this.numberOfFloors = numberOfFloors;
        this.numberOfElevators = numberOfElevators;
        this.floors = new ArrayList<>();
        this.elevators = new ArrayList<>();

        for (int id = 0; id < this.numberOfFloors; id++) floors.add(new Floor(id));
        for (int i = 0; i < this.numberOfElevators; i++) elevators.add(new Elevator(this));
    }


    // The system searches for the best possible elevator for the passenger
    // and assigns the passenger to the waiting list of that elevator.
    public void addWaitingPassenger(Passenger passenger){
        Elevator elevator = getElevatorForPassenger(passenger);
        if (elevator != null){
            if(passenger.getCurrentFloor().getFloorID() != elevator.getCurrentFloor().getFloorID()) {
                elevator.addStop(passenger.getCurrentFloor());
                elevator.waitingPassengers.add(passenger);
            }
            else elevator.addStop(passenger.getDestinationFloor());
        }
    }

    public void moveAllElevators() {
        for (Elevator elevator : this.elevators) elevator.move();
    }

    // The function first searches for a lift that is already moving
    // and has the passenger's floor on its route, but if one does not exist,
    // it sends the lift that is in the IDLE state. If there is no elevator that
    // meets these criteria, then the elevator with the closest destination to the given floor is chosen.
    public Elevator getElevatorForPassenger(Passenger passenger) {
        int floorID = passenger.getCurrentFloor().getFloorID();

        Elevator bestPossibleElevator = getPossibleMovingElevator(floorID);
        if (bestPossibleElevator != null) return bestPossibleElevator;

        bestPossibleElevator = findIDLEElevator(passenger.getCurrentFloor().getFloorID());
        if (bestPossibleElevator != null){
            if (floorID > bestPossibleElevator.getCurrentFloor().getFloorID()) {bestPossibleElevator.changeDirection(Direction.UP);}
            else bestPossibleElevator.changeDirection(Direction.DOWN);
            return bestPossibleElevator;
        }
        return findNearest(floorID);
    }

    // Searching for an elevator that is moving and has the given floor on its route.
    public Elevator getPossibleMovingElevator(int floorID){
        int smallestDistance = this.numberOfFloors;
        Elevator nearestElevator = null;

        for (Elevator elevator: this.elevators){
            int distance = this.numberOfFloors;
            Direction direction = elevator.getCurrentDirection();

            if (direction == Direction.UP) {
                distance = floorID - elevator.getCurrentFloor().getFloorID();
                if (distance < 0) continue;
            }

            else if (direction == Direction.DOWN){
                distance = elevator.getCurrentFloor().getFloorID() - floorID;
                if (distance < 0) continue;
            }

            if (distance < smallestDistance && elevator.getCurrentDirection() == direction){
                nearestElevator = elevator;
                smallestDistance = distance;
            }
        }
        return nearestElevator;
    }

    // Searching for the elevator closest to a given floor that is in the IDLE state.
    public Elevator findIDLEElevator(int floorID){
        Elevator nearestElevator = null;
        int smallestDistance = this.numberOfFloors;

        for (Elevator elevator : this.elevators){
            int distance = Math.abs(floorID - elevator.getCurrentFloor().getFloorID());
            if (elevator.getCurrentDirection() == Direction.IDLE && distance < smallestDistance){
                nearestElevator = elevator;
                smallestDistance = distance;
            }
        }
        return nearestElevator;
    }

    // finding elevator with the closest destination to the given floor
    public Elevator findNearest(int floorID) {
        int distance = this.numberOfFloors;
        Elevator result = null;
        for (Elevator elevator : this.elevators){
            int newDistance = Math.abs(elevator.getDestinationFloor().getFloorID() - floorID);
            if (newDistance < distance){
                distance =  newDistance;
                result = elevator;
            }
        }
        return result;
    }

    // Finding the floor with given id
    public Floor getFloorWithId(int floorID){
        return (floorID < this.numberOfFloors) ? this.floors.get(floorID) : null;
    }

    //  Finding the next/previous floor.
    public Floor getNextFloor(Floor currentFloor) {
        int idx = currentFloor.getFloorID();
        return (idx + 1 < this.numberOfFloors) ? this.floors.get(idx + 1) : null;
    }

    public Floor getPrevFloor(Floor currentFloor) {
        int idx = currentFloor.getFloorID();
        return (idx - 1 >= 0) ? this.floors.get(idx - 1) : null;
    }

}
