package elevators;

import java.util.*;


public class Elevator {
    private Floor currentFloor;
    private Direction currentDirection;
    private final ElevatorSystem system;
    private ArrayList<Floor> stops;
    private LinkedList<Passenger> passengersIn;


    public Elevator(ElevatorSystem system) {
        this.currentDirection = Direction.IDLE;
        this.currentFloor = new Floor(0);
        this.passengersIn = new LinkedList<>();
        this.stops = new ArrayList<>();
        this.system = system;
    }

    public void changeDirection(Direction direction){
        this.currentDirection = direction;
    }

    public void changeFloor(Floor floor){
        this.currentFloor = floor;
    }

    public void letPassengersIn(){
        int passengersNumber = 0;
        LinkedList<Passenger> allPassengers = new LinkedList<>(this.system.getPassengersAt(this.currentFloor));
        for (Passenger passenger: allPassengers){
            if (passenger.getCurrentFloorID() == this.currentFloor.getFloorID() && passengersNumber < this.system.maxCapacity) {
                this.passengersIn.add(passenger);
                this.system.removePassenger(passenger);
                passengersNumber++;
            }
        }

        for (Passenger passenger: this.passengersIn){
            this.system.removePassenger(passenger);
        }
    }

    public void letPassengersOut() {
        ArrayList<Passenger> toUpdate = new ArrayList<>();
        for (Passenger passenger: this.passengersIn){
            if (passenger.getDestinationFloor().getFloorID() == this.currentFloor.getFloorID()){
                toUpdate.add(passenger);
            }
        }

        for (Passenger passenger: toUpdate){
            this.passengersIn.remove(passenger);
        }
    }

    public void findNextStopOrReverse(){
        ArrayList<Floor> allFloors = this.system.getWaitingFloors();
        ArrayList<Integer> floorsSortedByID = this.system.getWaitingFloorsSortedID();

        if (allFloors.size() == 0) {
            this.changeDirection(Direction.IDLE);
            return;
        }

        int index = bisectRight(floorsSortedByID, this.currentFloor.getFloorID());

        if (this.currentDirection == Direction.UP){
            if (index >= this.stops.size())this.changeDirection(Direction.DOWN);
            else {
                for (Floor floor: allFloors){
                    if (floor.getFloorID() == floorsSortedByID.get(index)) this.stops.add(floor);
                }
            }
        }

        else if (this.currentDirection == Direction.DOWN){
            index -= 2;
            if (index < 0) this.changeDirection(Direction.UP);
            else {
                for (Floor floor: allFloors){
                    if (floor.getFloorID() == floorsSortedByID.get(index)) this.stops.add(floor);
                }
            }
        }

        else {
            int left_idx = bisectRight(floorsSortedByID, this.currentFloor.getFloorID()) - 2;
            int right_idx = bisectRight(floorsSortedByID, this.currentFloor.getFloorID());
            Floor left=null, right=null;
            int leftDist=-1, rightDist= -1;

            for (Floor floor : allFloors){
                if (left_idx >= 0 && floor.getFloorID() == floorsSortedByID.get(left_idx)) {
                    left = floor;
                    leftDist = Math.abs(currentFloor.getFloorID() - left.getFloorID());
                }
                else if (right_idx < floorsSortedByID.size() && floor.getFloorID() == floorsSortedByID.get(right_idx)) {
                    right = floor;
                    rightDist = Math.abs(currentFloor.getFloorID() - right.getFloorID());
                }
            }

            if (left != null && right != null){
                if (leftDist < rightDist) {
                    this.stops.add(left);
                    this.changeDirection(Direction.DOWN);
                }
                else {
                    this.stops.add(right);
                    this.changeDirection(Direction.UP);
                }
            }

            else if (left != null) {
                this.stops.add(left);
                this.changeDirection(Direction.DOWN);
            }

            else if (right != null) {
                this.stops.add(right);
                this.changeDirection(Direction.UP);
            }
        }
    }

    public void move() {
        switch (this.currentDirection){
            case UP -> {
                if (this.currentFloor.getFloorID() + 1 < this.system.numberOfFloors){
                    this.currentFloor = this.system.getNextFloor(this.currentFloor);
                }
            }
            case DOWN -> {
                if (this.currentFloor.getFloorID() - 1 >= 0){
                    this.currentFloor = this.system.getPrevFloor(this.currentFloor);
                }
            }
        }
    }
    public static int bisectRight(ArrayList<Integer> floors, int x) {
        return bisectRight_(floors, x, 0, floors.size());
    }

    private static int bisectRight_(ArrayList<Integer> floors, int x, int low, int high) {
        while (low < high) {
            int mid = (low+high)/2;
            if (x < floors.get(mid)) high = mid;
            else low = mid + 1;
        }
        return low;
    }
}
