package gui;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;

/*  Stage is the entire "Window"
    Content inside the Stage is the Scene
*/

// every java application extends Application
// lets us use javafx stuff
// EventHandler<ActionEvent> is for mouse clicks
public class Main extends Application {

    private Scene homeScene, pathfindScene, sortingScene, aStarSettings, aStarScene;
    private final int H_RES = 1280;
    private final int V_RES = 720;
    private final Color BACKGROUND = Color.BLACK; // Not working

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Algorithm Visualizer");
        stage.setResizable(false);

        homeScene = createHomeScene(stage);
            pathfindScene = createPathfindScene(stage);
                aStarSettings = createAStarSettings(stage);
                    aStarScene = createAStarScene(stage);

            sortingScene = createSortingScene(stage);


        stage.setScene(homeScene);
        stage.show();
    }

    private Scene createHomeScene(Stage stage) {
        Label homeTitle = new Label("Algorithm Visualizer");
        Button pathfindButton = new Button("Pathfinding Algorithms");
        Button sortingButton = new Button("Sorting Algorithms");
        Label name = new Label("GitHub:   jtorres-dev");

        sortingButton.setOnAction(event -> stage.setScene(sortingScene));
        pathfindButton.setOnAction(event -> stage.setScene(pathfindScene));
        StackPane homeLayout = new StackPane();

        homeLayout.getChildren().addAll(homeTitle, pathfindButton, sortingButton, name);
        homeLayout.setMargin(homeTitle, new Insets(-250, 0, 0, 0));
        homeLayout.setMargin(pathfindButton, new Insets(50, 0, 0, 0));
        homeLayout.setMargin(sortingButton, new Insets(150, 0, 0, 0));
        homeLayout.setMargin(name, new Insets(675, -1100, 0, 0));

        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(homeLayout);

        return new Scene(mainPane, H_RES, V_RES, BACKGROUND);
    }

    private Scene createPathfindScene(Stage stage) {
        Label pathfindTitle = new Label("Pathfinding Algorithms");
        Button aStarButton = new Button("A* Algorithm");
        Button homeButton = new Button("Back");
        Label name = new Label("GitHub:   jtorres-dev");

        homeButton.setOnAction(event -> stage.setScene(homeScene));
        aStarButton.setOnAction(event -> stage.setScene(aStarSettings));
        StackPane pathfindLayout = new StackPane();

        pathfindLayout.getChildren().addAll(pathfindTitle, aStarButton, homeButton, name);
        pathfindLayout.setMargin(pathfindTitle, new Insets(-250, 0, 0, 0));
        pathfindLayout.setMargin(aStarButton, new Insets(50, 0, 0, 0));
        pathfindLayout.setMargin(homeButton, new Insets(150, 0, 0, 0));
        pathfindLayout.setMargin(name, new Insets(675, -1100, 0, 0));

        BorderPane pathfindPane = new BorderPane();
        pathfindPane.setCenter(pathfindLayout);

        return new Scene(pathfindPane, H_RES, V_RES, BACKGROUND);
    }

    private Scene createAStarSettings(Stage stage) {
        GridPane gridPane = new GridPane();

        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setHgap(50);
        gridPane.setVgap(50);

        gridPane.setPadding(new Insets(125, 20, 20, 20));

        Text settingsTitle = new Text("Settings");
        settingsTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 24));
        gridPane.add(settingsTitle, 0, 0, 2, 1);

        Label gridLabel = new Label("Grid size:");
        gridPane.add(gridLabel, 0, 1);

        Label startLabel = new Label("Start node color:");
        gridPane.add(startLabel, 0, 2);

        Label endLabel = new Label("End node color:");
        gridPane.add(endLabel, 0, 3);

        Label wallLabel = new Label("Wall color:");
        gridPane.add(wallLabel, 0, 4);

        Button backButton = new Button("Back");
        backButton.setAlignment(Pos.BOTTOM_LEFT);
        backButton.setOnAction(event -> stage.setScene(pathfindScene));
        gridPane.add(backButton, 0, 5);

        Button nextButton = new Button("Start");
        nextButton.setAlignment(Pos.BOTTOM_RIGHT);
        nextButton.setOnAction(event -> stage.setScene(aStarScene));
        gridPane.add(nextButton, 2, 5);

        ComboBox<String> gridSizes = createGridOptions();
        gridPane.add(gridSizes, 1, 1);

        ColorPicker startColor = new ColorPicker();
        startColor.setValue(Color.BLUE);
        gridPane.add(startColor, 1, 2);

        ColorPicker endColor = new ColorPicker();
        endColor.setValue(Color.RED);
        gridPane.add(endColor, 1, 3);

        ColorPicker wallColor = new ColorPicker();
        wallColor.setValue(Color.BLACK);
        gridPane.add(wallColor, 1, 4);

        BorderPane settingsPane = new BorderPane();
        settingsPane.setCenter(gridPane);

        return new Scene(settingsPane, H_RES, V_RES, BACKGROUND);
    }
    private ComboBox<String> createGridOptions() {
        ComboBox<String> gridSizes = new ComboBox<>();
        gridSizes.setPromptText("Choose a grid size");

        gridSizes.getItems().addAll(
                "5 x 5", "6 x 6", "7 x 7", "8 x 8", "9 x 9", "10 x 10",
                "11 x 11", "12 x 12", "13 x 13", "14 x 14", "15 x 15", "16 x 16",
                "17 x 17", "18 x 18", "19 x 19", "20 x 20"
        );

//        String response = gridSizes.getValue();
//        int size = 0;
//
//        // used for AStar construction later
//        if(response != null) {
//            switch (response) {
//                case "5 x 5": size = 6;
//                    break;
//                case "6 x 6": size = 7;
//                    break;
//                case "7 x 7": size = 8;
//                    break;
//                case "8 x 8": size = 9;
//                    break;
//                case "9 x 9": size = 10;
//                    break;
//                case "10 x 10": size = 11;
//                    break;
//                default: size = 6;
//                    break;
//            }
//        }
        //        gridSizes.setOnAction(event -> System.out.println("User selected: " + gridSizes.getValue()));
        return gridSizes;
    }

    private Scene createAStarScene(Stage stage) {
        BorderPane aStarPane = new BorderPane();
        return new Scene(aStarPane, H_RES, V_RES, BACKGROUND);
    }

    private Scene createSortingScene(Stage stage) {
        Label sortingTitle = new Label("Sorting Algorithms");
        Button mergeSortButton = new Button("Merge Sort Algorithm");
        Button homeButton = new Button("Back");
        Label name = new Label("GitHub:   jtorres-dev");

        homeButton.setOnAction(event -> stage.setScene(homeScene));
        StackPane sortingLayout = new StackPane();

        sortingLayout.getChildren().addAll(sortingTitle, mergeSortButton, homeButton, name);
        sortingLayout.setMargin(sortingTitle, new Insets(-250, 0, 0, 0));
        sortingLayout.setMargin(mergeSortButton, new Insets(50, 0, 0, 0));
        sortingLayout.setMargin(homeButton, new Insets(150, 0, 0, 0));
        sortingLayout.setMargin(name, new Insets(675, -1100, 0, 0));

        BorderPane sortingPane = new BorderPane();
        sortingPane.setCenter(sortingLayout);

        return new Scene(sortingPane, H_RES, V_RES, BACKGROUND);
    }

    public static void main(String[] args) {
        // sets up Application
        launch(args);
    }
}
