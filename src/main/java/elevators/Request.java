package elevators;

public class Request {
    private int startFloor, destinationFloor;
    private Passenger passenger;

    public Request(int startFloor, int destinationFloor, Passenger passenger){
        this.startFloor = startFloor;
        this.destinationFloor = destinationFloor;
        this.passenger = passenger;
    }

    public Passenger getPassenger(){
        return this.passenger;
    }


}
