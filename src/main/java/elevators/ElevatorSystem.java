package elevators;

import java.util.ArrayList;
import  java.util.LinkedList;
import java.util.LinkedHashMap;

public class ElevatorSystem {
    public ArrayList<Floor> floors;
    public ArrayList<Elevator> elevators;
    protected LinkedHashMap<Floor, LinkedList<Passenger>> passengers;
    public int numberOfFloors, numberOfElevators;

    public ElevatorSystem(int numberOfFloors, int numberOfElevators){
        this.numberOfFloors = numberOfFloors;
        this.numberOfElevators = numberOfElevators;
        this.floors = new ArrayList<>();
        this.elevators = new ArrayList<>();
        this.passengers = new LinkedHashMap<>();

        for (int id = 0; id < this.numberOfFloors; id++) floors.add(new Floor(id));
        for (int i = 0; i < this.numberOfElevators; i++) elevators.add(new Elevator(this));
    }

    // gdy pasażer naciska przycisk, szukana jest dla niego winda - jesli nie uda nam się jej znalezc,
    // pasazer dodawany jest na liste oczekujacych
    public void addWaitingPassenger(Passenger passenger){
        Elevator elevator = getElevatorForPassenger(passenger);
        System.out.println("Znaleziono windę: " + elevator);
        if (elevator == null){
            Floor floor = passenger.getCurrentFloor();
            if (this.passengers.get(floor) == null){
                LinkedList<Passenger> newPassengers = new LinkedList<>();
                newPassengers.add(passenger);
                this.passengers.put(floor, newPassengers);
            }
            else {
                this.passengers.get(floor).add(passenger);
            }
        }
    }

    public void removePassenger(Passenger passenger){
        Floor floor = passenger.getCurrentFloor();
        this.passengers.get(floor).remove(passenger);
        if (this.passengers.get(floor).size() == 0) this.passengers.remove(floor);
    }

    public void moveAllElevators() {
        for (Elevator elevator : this.elevators) elevator.move();
    }

    public Elevator getElevatorForPassenger(Passenger passenger) {
        int floorID = passenger.getCurrentFloor().getFloorID();

        for (Elevator elevator : this.elevators) {
            if (elevator.getCurrentDirection() == Direction.IDLE) {
                if (passenger.getCurrentFloor().getFloorID() >= elevator.getCurrentFloor().getFloorID()){
                    elevator.changeDirection(Direction.UP);
                }
                else elevator.changeDirection(Direction.DOWN);
                elevator.addStop(passenger.getCurrentFloor());
                elevator.addStop(passenger.getDestinationFloor());
                elevator.setNewDestination(passenger.getDestinationFloor());
                return elevator;
            }
        }

        Elevator bestPossibleElevator = getPossibleElevator(floorID);
        if (bestPossibleElevator == null) return null;

        // jesli znaleziono najlepsza mozliwa winde, to wpuszczamy pasazera do
        // srodka i dodajemy stop dla tej windy, winda jedzie po niego
        Direction bestElevatorDirection = bestPossibleElevator.getCurrentDirection();
        bestPossibleElevator.letPassengerIn(passenger);
        bestPossibleElevator.addStop(passenger.getCurrentFloor());

        int currentDestinationId = bestPossibleElevator.getDestinationFloor().getFloorID();
        int newDestinationId = passenger.getDestinationFloor().getFloorID();

        // aktualizujemy cel dla danej windy
        switch (bestElevatorDirection){
            case UP -> {
                if (currentDestinationId < newDestinationId){
                    bestPossibleElevator.setNewDestination(passenger.getDestinationFloor());
                }
            }
            case DOWN -> {
                if (currentDestinationId > newDestinationId){
                    bestPossibleElevator.setNewDestination(passenger.getDestinationFloor());
                }
            }
        }
        return bestPossibleElevator;
    }

    // szuka windy, która ma podane piętro na swojej aktualnej drodze i ma do niej najblizej
    public Elevator getPossibleElevator(int floorID){
        int smallestDistance = this.numberOfElevators;
        Elevator nearestElevator = null;

        for (Elevator elevator: this.elevators){
            int distance = this.numberOfElevators;
            Direction direction = elevator.getCurrentDirection();

            // jesli jedzie do gory, to dane pietro musi byc wyzej lub takie samo
            if (direction == Direction.UP) {
                distance = floorID - elevator.getCurrentFloor().getFloorID();
                if (distance < 0) continue;
            }
            // jesli jedzie na dol, to dane pietro musi byc ponizej lub takie samo
            else if (direction == Direction.DOWN){
                distance = elevator.getCurrentFloor().getFloorID() - floorID;
                if (distance < 0) continue;
            }
            // zmieniamy najlepszy aktualny wynik, jesli osiagnelismy cos lepszego
            if (distance < smallestDistance && elevator.getCurrentDirection() == direction){
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
