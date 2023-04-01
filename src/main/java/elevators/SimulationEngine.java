package elevators;
import elevators.gui.App;
import java.io.FileNotFoundException;

public class SimulationEngine implements Runnable {

    private final ElevatorSystem system;
    private final App app;
    public boolean running;

    public SimulationEngine(ElevatorSystem system, App app, boolean running) {

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