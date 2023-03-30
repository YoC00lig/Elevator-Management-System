package elevators;
import elevators.gui.App;
import java.io.FileNotFoundException;

public class SimulationEngine implements IEngine, Runnable {

    private final int moveDelay;
    private final ElevatorSystem system;
    private final App app;
    private int iter = 0;
    public boolean running;

    public SimulationEngine(ElevatorSystem system, int moveDelay, App app, boolean running) {
        this.moveDelay = moveDelay;
        this.system = system;
        this.app = app;
        this.running = running;
    }

    private void update() {
        this.system.moveAllElevators();
    }

    @Override
    public void run() {
        while (running) {
           update();
            try {
                app.draw();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        Thread.interrupted();
    }



}