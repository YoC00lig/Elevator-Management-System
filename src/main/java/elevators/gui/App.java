package elevators.gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import java.io.FileNotFoundException;

public class App extends Application {
    private GridPane gridPane = new GridPane();
    private final Stage stage = new Stage();

    private Scene scene;
    private BorderPane mainbox = new BorderPane();;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        gridPane.getChildren().clear();
        gridPane = new GridPane();

        Label welcomeLabel = new Label("Elevator System Management");
        welcomeLabel.setFont(new Font("Arial", 50));
        Button start = new Button("START");
        start.setFont(new Font("Arial", 40));

        start.setOnMouseClicked(event -> {
            try {
                inputsScene();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        VBox vBox = new VBox(100, welcomeLabel, start);
        vBox.setAlignment(Pos.CENTER);
        gridPane.add(vBox, 0, 0);
        gridPane.setAlignment(Pos.CENTER);

        stage.setScene(new Scene(gridPane, 1500, 800));
        stage.setTitle("Elevator System Management");
        stage.show();

    }

    public void inputsScene() throws FileNotFoundException {
        gridPane.getChildren().clear();
        gridPane = new GridPane();

        Label chooseLabel = new Label("Input your values");
        chooseLabel.setFont(new Font("Arial", 50));

        Button start = new Button("Proceed");
        Button end = new Button("End");

        start.setPrefWidth(100);
        end.setPrefWidth(100);

        start.setOnMouseClicked(event -> {
            try {
                drawSimulation();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });


        HBox hBox = new HBox(40, start, end);
        hBox.setAlignment(Pos.CENTER);

        TextField numberOfFloors = new TextField();
        numberOfFloors.setPromptText("Number of floors");
        Label numberOfFloorsLabel = new Label("Enter number of floors:");
        HBox input1 = new HBox(40, numberOfFloorsLabel, numberOfFloors);

        TextField numberOfElevators = new TextField();
        numberOfElevators.setPromptText("Number of elevators");
        Label numberOfElevatorsLabel = new Label("Enter number of elevators:");
        HBox input2 = new HBox(25, numberOfElevatorsLabel, numberOfElevators);

        TextField capacity = new TextField();
        capacity.setPromptText("Elevator capacity");
        Label capacityLabel = new Label("Enter elevator capacity:");
        HBox input3 = new HBox(40, capacityLabel, capacity);

        VBox inputs = new VBox(20,input1,input2,input3);
        inputs.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(100, chooseLabel, inputs,hBox);
        vBox.setAlignment(Pos.CENTER);
        gridPane.add(vBox, 0, 0);
        gridPane.setAlignment(Pos.CENTER);
        scene = new Scene(gridPane, 1500, 800);
        stage.setScene(scene);
        stage.show();
    }

    public void drawSimulation() throws FileNotFoundException {
        mainbox = new BorderPane();
        gridPane.getChildren().clear();
        Button endSimulation = new Button("END");
        HBox hBox = new HBox(40, endSimulation);
        hBox.setAlignment(Pos.CENTER);
        VBox vBox = new VBox(100, hBox);
        gridPane.add(vBox, 0, 0);
        gridPane.setAlignment(Pos.TOP_CENTER);
        mainbox.setTop(gridPane);
        scene.setRoot(mainbox);
        stage.setScene(scene);
        stage.show();
    }

}