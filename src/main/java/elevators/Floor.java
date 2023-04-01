package elevators;

public record Floor(int floorID) {
    public int getFloorID(){
        return this.floorID;
    }
}

