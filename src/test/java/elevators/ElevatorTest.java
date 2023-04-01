package elevators;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


public class ElevatorTest {
    ElevatorSystem system = new ElevatorSystem(20,20);

    @Test
    public void getFloorWithIDTest(){
        Floor floor1 = this.system.getFloorWithId(1);
        assertEquals(this.system.floors.get(1), floor1);
        assertEquals(this.system.getNextFloor(new Floor(0)), floor1);
        assertEquals(this.system.getPrevFloor(new Floor(2)), floor1);
    }

    @Test
    public void addingPassenger(){
        Passenger passenger = new Passenger(new Floor(3), new Floor(4));
        this.system.addWaitingPassenger(passenger);
        assertEquals(this.system.elevators.get(0).stops.size(), 1);
        this.system.moveAllElevators();
        assertEquals(this.system.elevators.get(0).getCurrentFloor().getFloorID(), 1);
        this.system.moveAllElevators();
        this.system.moveAllElevators();
        assertEquals(this.system.elevators.get(0).stops.size(), 1);
        this.system.moveAllElevators();
        assertEquals(this.system.elevators.get(0).stops.size(), 0);
    }

    @Test
    public void findElevatorTest(){
        assertEquals(this.system.findIDLEElevator(1), this.system.elevators.get(0));
        assertNull(this.system.getPossibleMovingElevator(1));
    }

    @Test
    public void checkFloorDestination(){
        Passenger passenger = new Passenger(new Floor(3), new Floor(4));
        this.system.addWaitingPassenger(passenger);
        assertEquals(this.system.elevators.get(0).getCurrentDirection(), Direction.UP);
        assertEquals(this.system.elevators.get(1).getCurrentDirection(), Direction.IDLE);
    }

    @Test
    public void gettersForRecords() {
        Passenger passenger = new Passenger(new Floor(1), new Floor(0));
        Floor floor = new Floor(5);
        assertEquals(passenger.getDestinationFloor().getFloorID(), 0);
        assertEquals(passenger.getCurrentFloor().getFloorID(), 1);
        assertEquals(floor.getFloorID(), 5);
    }
}
