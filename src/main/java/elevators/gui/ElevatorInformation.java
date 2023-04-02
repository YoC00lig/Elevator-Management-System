package elevators.gui;

import elevators.Elevator;
import elevators.ElevatorSystem;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.Label;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class ElevatorInformation {
    private HBox box;
    private VBox vbox1;
    private final VBox vbox2;
    private final Elevator elevator;
    private final ElevatorSystem system;
    Image image = null;

    // The element on the side panel that corresponds to a particular elevator and describes its current state.
    public ElevatorInformation(Elevator elevator, ElevatorSystem system){
        this.elevator = elevator;
        this.system = system;
        Label elevatorID = new Label("Elevator " + system.elevators.indexOf(elevator));
        Label currentFloor = new Label("Current floor: " + elevator.getCurrentFloor().getFloorID());
        Label destinationFloor = new Label("Destination floor: " + elevator.getDestinationFloor().getFloorID());
        Label currentDirection = new Label("Current Direction: " + elevator.getCurrentDirection());

        try {
            image = new Image(new FileInputStream("src/main/resources/lift.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);


        vbox1 = new VBox(20, currentFloor, destinationFloor, currentDirection);
        vbox2 = new VBox(10, elevatorID, imageView);

        box = new HBox(20, vbox2, vbox1);

        box.setMaxWidth(250);
        box.setStyle("-fx-background-color: #e6e6ff;" + "-fx-background-radius: 1em;");
        box.setAlignment(Pos.CENTER);
        box.setEffect(new DropShadow());

    }

    public HBox getHBox(){
        return this.box;
    }

    public void update(){
        Label currentFloor = new Label("Current floor: " + elevator.getCurrentFloor().getFloorID());
        Label destinationFloor = new Label("Destination floor: " + elevator.getDestinationFloor().getFloorID());
        Label currentDirection = new Label("Current Direction: " + elevator.getCurrentDirection());

        vbox1 = new VBox(20, currentFloor, destinationFloor, currentDirection);
        box = new HBox(20, vbox2, vbox1);

        box.setMaxWidth(250);
        box.setStyle("-fx-background-color: #e6e6ff;" + "-fx-background-radius: 1em;");
        box.setAlignment(Pos.CENTER);
        box.setEffect(new DropShadow());
    }
}
