package elevators;

// A record represents a floor with a specific ID.
public record Floor(int floorID) {
    public int getFloorID(){
        return this.floorID;
    }
}

