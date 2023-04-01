package elevators;

import java.util.ArrayList;

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

        Elevator bestPossibleElevator = getPossibleMovingElevator(floorID);
        if (bestPossibleElevator != null) return bestPossibleElevator;

        bestPossibleElevator = findIDLEElevator(passenger.getCurrentFloor().getFloorID());
        if (bestPossibleElevator != null){
            if (floorID > bestPossibleElevator.getCurrentFloor().getFloorID()) {bestPossibleElevator.changeDirection(Direction.UP);}
            else bestPossibleElevator.changeDirection(Direction.DOWN);
            return bestPossibleElevator;
        }
        return null;
    }


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
        if (floorID < this.numberOfFloors) return this.floors.get(floorID);
        else return null;
    }

    public Floor getNextFloor(Floor currentFloor) {
        int idx = currentFloor.getFloorID();
        if (idx + 1< this.numberOfFloors) return this.floors.get(idx+1);
        else return null;
    }

    public Floor getPrevFloor(Floor currentFloor) {
        int idx = currentFloor.getFloorID();
        if (idx - 1 >= 0) return this.floors.get(idx-1);
        else return null;
    }
}
