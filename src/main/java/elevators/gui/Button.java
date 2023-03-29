package elevators.gui;

import elevators.*;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import javafx.scene.control.TextField;

public class Button {
    private final VBox vBox;

    public Button(int floorID, ElevatorSystem system){
        Image image = null;
        try {
            image = new Image(new FileInputStream("src/main/resources/button.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(60);
        imageView.setFitHeight(60);

        TextField signature = new TextField("0");
        signature.setAlignment(Pos.CENTER);

        Floor currentFloor = system.getFloorWithId(floorID);

        vBox = new VBox(10,imageView, signature);
        vBox.setAlignment(Pos.CENTER);

        imageView.setOnMouseClicked(event -> {
            int destinationFloorID = Integer.parseInt(signature.getText());
            if (destinationFloorID != floorID) {
                Floor destinationFloor = system.getFloorWithId(destinationFloorID);
                Passenger newPassenger = new Passenger(currentFloor, destinationFloor);
                system.addWaitingPassenger(newPassenger);
            }
        });

        vBox.setStyle("-fx-background-color: #f7cac9");

    }

    public VBox getvBox()  {
        return vBox;
    }
}
