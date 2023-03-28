package elevators;

public class Request {
    private int currentFloor, destinationFloor;

    public Request(int startFloor, int endFloor){
        this.currentFloor = startFloor;
        this.destinationFloor = endFloor;
    }

}
