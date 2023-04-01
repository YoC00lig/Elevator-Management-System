package elevators;

import java.util.ArrayList;
import  java.util.LinkedList;
import java.util.LinkedHashMap;

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

    public Elevator getElevatorForPassenger(Passenger passenger) {
        int floorID = passenger.getCurrentFloor().getFloorID();

        Elevator bestPossibleElevator = getPossibleElevator(floorID);
        if (bestPossibleElevator != null) return bestPossibleElevator;

        bestPossibleElevator = findIDLEElevator(passenger.getCurrentFloor().getFloorID());
        if (bestPossibleElevator != null){
            if (floorID > bestPossibleElevator.getCurrentFloor().getFloorID()) {bestPossibleElevator.changeDirection(Direction.UP);}
            else bestPossibleElevator.changeDirection(Direction.DOWN);
            return bestPossibleElevator;
        }
        return null;
    }


    public Elevator getPossibleElevator(int floorID){
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

    public Floor getFloorWithId(int floorID){
        for (Floor floor: this.floors){
            if (floor.getFloorID() == floorID) return floor;
        }
        return null;
    }

    public Floor getNextFloor(Floor currentFloor) {
        for (Floor floor: this.floors){
            if (floor.getFloorID() == currentFloor.getFloorID() + 1) return floor;
        }
        return null;
    }

    public Floor getPrevFloor(Floor currentFloor) {
        for (Floor floor: this.floors){
            if (floor.getFloorID() == currentFloor.getFloorID() - 1) return floor;
        }
        return null;
    }
}
