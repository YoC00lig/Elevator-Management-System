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
import elevators.SimulationEngine;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javafx.scene.input.MouseEvent;
import javafx.scene.effect.DropShadow;
import javafx.application.Platform;

public class App extends Application {
    private GridPane gridPane = new GridPane();
    private final Stage stage = new Stage();
    private final int size = 50;
    private SimulationEngine engine;

    private Scene scene;
    private BorderPane mainbox = new BorderPane();
    private ElevatorSystem system;
    private ScrollPane scroll = new ScrollPane();
    private ScrollPane sideBar = new ScrollPane();
    private int floorsNumber, elevatorsNumber;
    Thread thread;
    BorderPane pane = new BorderPane();
    ArrayList<VBox> buttons = new ArrayList<>();
    LinkedHashMap<Elevator, VBox> elevators = new LinkedHashMap<>();
    ArrayList<ElevatorInformation> informations = new ArrayList<>();
    int gridHeight, gridWidth;
    private VBox allInformation;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        stage.setResizable(false);
        stage.setOnCloseRequest( e -> {
            System.exit(0);
        });

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

        gridPane.setStyle("-fx-background-color: #e6e6ff");
        stage.setScene(new Scene(gridPane, 1500, 800));
        stage.setTitle("Elevator System Management");
        stage.show();

    }

    public void inputsScene() throws FileNotFoundException {
        gridPane.getChildren().clear();
        gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: #e6e6ff");

        Label chooseLabel = new Label("Input your values");
        chooseLabel.setFont(new Font("Arial", 50));

        Button proceed = new Button("Proceed");
        Button end = new Button("End");

        proceed.setPrefWidth(100);
        end.setPrefWidth(100);

        styleButtonHover(proceed);
        styleButtonHover(end);

        TextField numberOfFloors = new TextField("20");
        numberOfFloors.setAlignment(Pos.CENTER);
        Label numberOfFloorsLabel = new Label("Enter number of floors:");
        HBox input1 = new HBox(40, numberOfFloorsLabel, numberOfFloors);

        TextField numberOfElevators = new TextField("10");
        numberOfElevators.setAlignment(Pos.CENTER);
        Label numberOfElevatorsLabel = new Label("Enter number of elevators:");
        HBox input2 = new HBox(25, numberOfElevatorsLabel, numberOfElevators);

        VBox inputs = new VBox(20,input1,input2);
        inputs.setAlignment(Pos.CENTER);

        end.setOnMouseClicked(event -> System.exit(0));

        proceed.setOnMouseClicked(event -> {
            floorsNumber = Integer.parseInt(numberOfFloors.getText());
            elevatorsNumber = Integer.parseInt(numberOfElevators.getText());
            this.gridWidth = (elevatorsNumber * 2) + 2; // each elevator - 2 cells width and 2 additional for button
            this.gridHeight = floorsNumber * 2; // each floor 2 cells height
            this.system = new ElevatorSystem(floorsNumber, elevatorsNumber);
            this.PrepareGrid();
            engine = new SimulationEngine(this.system, 500, this, true);
            thread = new Thread(engine);
            thread.start();
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

    public void drawMap(){
        for (Elevator elevator:this.system.elevators){
            if (elevator.updated){
                gridPane.getChildren().remove(this.elevators.get(elevator));
                VBox liftBox = new Lift(elevator, this.system).getvBox();
                this.elevators.put(elevator,liftBox);
            }
        }

        allInformation.getChildren().clear();
        for (ElevatorInformation information: this.informations) {
            information.update();
            allInformation.getChildren().add(information.getHBox());
        }

        for (Elevator elevator : this.elevators.keySet()){
            if (elevator.updated){
                int[] position = getElevatorGridPosition(this.system.elevators.indexOf(elevator), elevator.getCurrentFloor().getFloorID());
                VBox lift = this.elevators.get(elevator);
                gridPane.add(lift, position[0], position[1], 2, 2);
            }
        }

        stage.setScene(scene);
        stage.show();
    }

    public void PrepareGrid(){
        gridPane.getChildren().clear();
        gridPane.setGridLinesVisible(true);
        gridPane.setStyle("-fx-background-color: #e6e6ff");


        for (int i = 0; i < gridWidth; i++) gridPane.getColumnConstraints().add(new ColumnConstraints(size));
        for (int i = 0; i < gridHeight; i++) gridPane.getRowConstraints().add(new RowConstraints(size));

        for (int i = 0; i < floorsNumber; i++){
            ElevatorButton button = new ElevatorButton(this.system, i);
            buttons.add(button.getvBox());
        }

        int currRow =  gridHeight - 2;
        for (VBox button:buttons){
            int row =  currRow;
            int col = 0;
            gridPane.add(button, col, row, 2, 2);
            GridPane.setHalignment(button, HPos.CENTER);
            currRow-=2;
        }

        scroll.setContent(gridPane);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);

        for (Elevator elevator : this.system.elevators){
            int[] position = getElevatorGridPosition(this.system.elevators.indexOf(elevator), elevator.getCurrentFloor().getFloorID());
            VBox lift = new Lift(elevator, this.system).getvBox();
            ElevatorInformation liftInformation = new ElevatorInformation(elevator, this.system);
            informations.add(liftInformation);
            gridPane.add(lift, position[0], position[1], 2, 2);
            this.elevators.put(elevator, lift);
        }

        this.allInformation = new VBox();
        for (ElevatorInformation information:this.informations) allInformation.getChildren().add(information.getHBox());
        allInformation.setStyle("-fx-background-color: #f7cac9");
        allInformation.setPrefWidth(300);
        allInformation.setPrefHeight(800);
        allInformation.setSpacing(20);
        allInformation.setAlignment(Pos.CENTER);
        sideBar.setContent(allInformation);
        scroll.setPrefWidth(1200);
        sideBar.setPrefWidth(300);
        sideBar.setStyle("-fx-background-color: #f7cac9");
        sideBar.setEffect(new DropShadow());
        HBox allScrollPanes = new HBox(scroll, sideBar);


        this.mainbox.setTop(pane);
        this.mainbox.setCenter(allScrollPanes);
        BorderPane.setAlignment(scroll, Pos.CENTER);
        gridPane.setAlignment(Pos.CENTER);

        scene.setRoot(mainbox);
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

    public void draw() throws FileNotFoundException {
        Platform.runLater(this::drawMap);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.exit(0);
        }
    }

}