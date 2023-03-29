package elevators;
import elevators.gui.App;

public class SimulationEngine implements IEngine, Runnable {

    private final int moveDelay;
    private final ElevatorSystem system;
    private final App app;


    public SimulationEngine(ElevatorSystem system, int moveDelay, App app) {
        this.moveDelay = moveDelay;
        this.system = system;
        this.app = app;
    }

    private void update() {
    }

    @Override
    public void run() {
        while (true){
            this.update();
        }
    }


}