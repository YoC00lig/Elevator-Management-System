package elevators;

import java.util.ArrayList;
import java.util.Collections;
import  java.util.LinkedList;
import java.util.LinkedHashMap;

public class ElevatorSystem {
    public ArrayList<Floor> floors;
    public ArrayList<Elevator> elevators;
    protected LinkedHashMap<Floor, LinkedList<Passenger>> passengers;
    public int numberOfFloors, numberOfElevators, maxCapacity;

    public ElevatorSystem(int numberOfFloors, int numberOfElevators, int maxCapacity){
        this.numberOfFloors = numberOfFloors;
        this.numberOfElevators = numberOfElevators;
        this.floors = new ArrayList<>();
        this.elevators = new ArrayList<>();
        this.passengers = new LinkedHashMap<>();
        this.maxCapacity = maxCapacity;

        for (int id = 0; id < this.numberOfFloors; id++) floors.add(new Floor(id));
        for (int i = 0; i < this.numberOfElevators; i++) elevators.add(new Elevator(this));
    }

    public void addWaitingPassenger(Passenger passenger){
        Floor floor = passenger.getCurrentFloor();
        floor.incrementWaitingPassengersNum();
        if (this.passengers.get(floor) == null){
            LinkedList<Passenger> newPassengers = new LinkedList<>();
            newPassengers.add(passenger);
            this.passengers.put(floor, newPassengers);
        }
        else {
            this.passengers.get(floor).add(passenger);
        }
    }

    public void removePassenger(Passenger passenger){
        Floor floor = passenger.getCurrentFloor();
        floor.decreaseWaitingPassengersNum();
        this.passengers.get(floor).remove(passenger);
        if (this.passengers.get(floor).size() == 0) this.passengers.remove(floor);
    }

    public int getNumberOfPassengersAt(int floorID){
        int numberOfPassengers = 0 ;
        for (LinkedList<Passenger> list: this.passengers.values()){
            for (Passenger passenger: list){
                if (passenger.getCurrentFloorID() == floorID) numberOfPassengers++;
            }
        }
        return numberOfPassengers;
    }

    public LinkedList<Passenger> getPassengersAt(Floor floor){
        return this.passengers.get(floor);
    }

    public ArrayList<Floor> getWaitingFloors(){
        return new ArrayList<>(this.passengers.keySet());
    }

    public ArrayList<Integer> getWaitingFloorsSortedID(){
        ArrayList<Integer> floorsIDs = new ArrayList<>();
        for (Floor floor: this.passengers.keySet()) floorsIDs.add(floor.getFloorID());
        Collections.sort(floorsIDs);
        return floorsIDs;
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
