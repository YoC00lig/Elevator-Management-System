package elevators.gui;
import elevators.*;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ElevatorButton {

    private final VBox vBox;

    public ElevatorButton(ElevatorSystem system, int floorID) {
        Image image = null;
        try {
            image = new Image(new FileInputStream("src/main/resources/button.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(45);
        imageView.setFitHeight(45);

        Label labelForFloorID = new Label("Floor " + floorID);

        TextField signature = new TextField("0");
        signature.setAlignment(Pos.CENTER);

        Floor currentFloor = system.getFloorWithId(floorID);

        vBox = new VBox(5, imageView, labelForFloorID, signature);
        vBox.setAlignment(Pos.CENTER);

        imageView.setOnMouseClicked(event -> {
            int destinationFloorID = Integer.parseInt(signature.getText());
            if (destinationFloorID != floorID) {
                if (destinationFloorID >= system.numberOfFloors) throw new IllegalArgumentException("Floor " + destinationFloorID + " does not exist");
                Floor destinationFloor = system.getFloorWithId(destinationFloorID);
                Passenger newPassenger = new Passenger(currentFloor, destinationFloor);
                system.addWaitingPassenger(newPassenger);
            }
        });

        vBox.setStyle("-fx-background-color: #f7cac9");

    }

    public VBox getvBox() {
        return vBox;
    }
}