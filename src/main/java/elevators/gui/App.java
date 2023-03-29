package elevators.gui;

import elevators.Elevator;
import elevators.ElevatorSystem;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import elevators.gui.Lift;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.scene.input.MouseEvent;
import javafx.scene.effect.DropShadow;

public class App extends Application {
    private GridPane gridPane = new GridPane();
    private final Stage stage = new Stage();
    private final int size = 50;

    private Scene scene;
    private BorderPane mainbox = new BorderPane();
    private ElevatorSystem system;
    private ScrollPane scroll = new ScrollPane();
    private int floorsNumber, elevatorsNumber;

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
        styleButtonHover(start);
        start.setFont(new Font("Arial", 40));


        start.setOnMouseClicked(event -> {
            try {
                inputsScene();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        Image image = null;
        try {
            image = new Image(new FileInputStream("src/main/resources/start.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(500);
        imageView.setFitHeight(400);

        VBox vBox = new VBox(100, welcomeLabel, imageView, start);
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

        Button proceed = new Button("Proceed");
        Button end = new Button("End");

        proceed.setPrefWidth(100);
        end.setPrefWidth(100);

        styleButtonHover(proceed);
        styleButtonHover(end);

        TextField numberOfFloors = new TextField("2");
        numberOfFloors.setAlignment(Pos.CENTER);
        Label numberOfFloorsLabel = new Label("Enter number of floors:");
        HBox input1 = new HBox(40, numberOfFloorsLabel, numberOfFloors);

        TextField numberOfElevators = new TextField("2");
        numberOfElevators.setAlignment(Pos.CENTER);
        Label numberOfElevatorsLabel = new Label("Enter number of elevators:");
        HBox input2 = new HBox(25, numberOfElevatorsLabel, numberOfElevators);

        VBox inputs = new VBox(20,input1,input2);
        inputs.setAlignment(Pos.CENTER);


        proceed.setOnMouseClicked(event -> {
            try {
                floorsNumber = Integer.parseInt(numberOfFloors.getText());
                elevatorsNumber = Integer.parseInt(numberOfElevators.getText());
                this.system = new ElevatorSystem(floorsNumber, elevatorsNumber);
                drawSimulation();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });


        HBox hBox = new HBox(40, proceed, end);
        hBox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(100, chooseLabel, inputs,hBox);
        vBox.setAlignment(Pos.CENTER);
        gridPane.add(vBox, 0, 0);
        gridPane.setAlignment(Pos.CENTER);
        scene = new Scene(gridPane, 1500, 800);
        stage.setScene(scene);
        stage.show();
    }

    public void drawSimulation() throws FileNotFoundException {
        BorderPane pane = new BorderPane();
        gridPane.getChildren().clear();

        // make grid scrollable
        scroll.setContent(gridPane);

        Button endSimulation = new Button("END");
        endSimulation.setOnMouseClicked(event -> System.exit(0));
        styleButtonHover(endSimulation);

        HBox hBox = new HBox(40, endSimulation);
        hBox.setAlignment(Pos.CENTER);
        VBox vBox = new VBox(100, hBox);
        pane.setTop(vBox);

        /// tutaj bedzie grid
        ArrayList<VBox> buttons = new ArrayList<>();
        for (int i = 0; i < floorsNumber; i++){
            elevators.gui.Button button = new elevators.gui.Button(i, this.system);
            buttons.add(button.getvBox());
        }

        int gridWidth = (elevatorsNumber * 2) + 2; // each elevator - 2 cells width and 2 additional for button
        int gridHeight = floorsNumber * 2; // each floor 2 cells height

        drawMap(gridWidth, gridHeight, buttons);

        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);

        this.mainbox.setTop(pane);
        this.mainbox.setCenter(scroll);
        BorderPane.setAlignment(scroll, Pos.CENTER);
        gridPane.setAlignment(Pos.CENTER);

        scene.setRoot(mainbox);
        stage.setScene(scene);
        stage.show();
    }


    public void drawMap(int gridWidth, int gridHeight, ArrayList<VBox> buttons){
        gridPane.setGridLinesVisible(true);

        for (int i = 0; i < gridWidth; i++) gridPane.getColumnConstraints().add(new ColumnConstraints(size));
        for (int i = 0; i < gridHeight; i++) gridPane.getRowConstraints().add(new RowConstraints(size));

        int currRow =  gridHeight - 2;
        for (VBox button:buttons){
            int row =  currRow;
            int col = 0;
            gridPane.add(button, col, row, 2, 2);
            GridPane.setHalignment(button, HPos.CENTER);
            currRow-=2;
        }

        int elevatorID = 0;
        for (Elevator elevator : this.system.elevators){
            int[] position = getElevatorGridPosition(elevatorID, elevator.getCurrentFloor().getFloorID());
            elevatorID++;
            VBox lift = new Lift(elevator).getvBox();
            gridPane.add(lift, position[0], position[1], 2, 2);
        }

        scene.setRoot(gridPane);
        stage.setScene(scene);
        stage.show();
    }


    public void styleButtonHover(Button B) {
        B.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> B.setEffect(new DropShadow()));
        B.addEventHandler(MouseEvent.MOUSE_EXITED, e -> B.setEffect(null));
        B.setStyle("-fx-background-color: #f7cac9;" + "-fx-background-radius: 1em;");
        B.setFont(new Font("Arial", 14));
    }

    public int[] getElevatorGridPosition(int elevatorID, int floorID){
        int row = 2*floorsNumber -2 - 2*floorID;
        int col = 2 + elevatorID*2;
        return new int[]{col, row};
    }

}