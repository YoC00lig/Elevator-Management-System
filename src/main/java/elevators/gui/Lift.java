package elevators.gui;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import elevators.Elevator;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Lift {

    private final VBox vBox;

    public Lift(Elevator elevator){
        Image image = null;
        try {
            image = new Image(new FileInputStream("src/main/resources/lift.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(60);
        imageView.setFitHeight(60);

        int currFloor = elevator.getCurrentFloor().getFloorID();
        Label l = new Label("Current: " + currFloor);

        vBox = new VBox(10,imageView, l);
        vBox.setAlignment(Pos.CENTER);
//        vBox.setStyle("-fx-background-color: #f7caa9");
    }

    public VBox getvBox()  {
        return vBox;
    }

}
