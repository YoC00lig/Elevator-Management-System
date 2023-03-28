package elevators;

public class Floor {
    private int waitingPassengers;
    private int floorID;

    public Floor(int id) {
        this.waitingPassengers = 0;
        this.floorID = id;
    }

    public void incrementWaitingPassengersNum(){
        this.waitingPassengers++;
    }

    public void decreaseWaitingPassengersNum(){
        this.waitingPassengers--;
    }

    public int getFloorID(){
        return this.floorID;
    }
}

