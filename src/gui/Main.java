package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
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
    private Label homeTitle, pathfindTitle, sortingTitle;
    private Label name, name2, name3;
    private Scene homeScene, pathfindScene, sortingScene;
    private Button homeButton, homeButton2, pathfindButton, sortingButton;
    private Button aStarButton, mergeSortButton;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Algorithm Visualizer");
        stage.setResizable(false);

        homeTitle = new Label("Algorithm Visualizer");
        pathfindTitle = new Label("Pathfinding Algorithms");
        sortingTitle = new Label("Sorting Algorithms");

        name = new Label("GitHub:   jtorres-dev");
        name2 = new Label("GitHub:   jtorres-dev");
        name3 = new Label("GitHub:   jtorres-dev");

        // spread out by how many pixels (v: )
        StackPane homeLayout = new StackPane();
        StackPane pathfindLayout = new StackPane();
        StackPane sortingLayout = new StackPane();

        homeButton = new Button("Go back");
        homeButton2 = new Button("Go back");
        pathfindButton = new Button("Pathfinding Algorithms");
        sortingButton = new Button("Sorting Algorithms");
        aStarButton = new Button("A* Algorithm");
        mergeSortButton = new Button("Merge Sort Algorithm");

        homeButton.setOnAction(event -> stage.setScene(homeScene));
        homeButton2.setOnAction(event -> stage.setScene(homeScene));
        sortingButton.setOnAction(event -> stage.setScene(sortingScene));
        pathfindButton.setOnAction(event -> stage.setScene(pathfindScene));


        homeLayout.getChildren().addAll(homeTitle, pathfindButton, sortingButton, name);
        pathfindLayout.getChildren().addAll(pathfindTitle, aStarButton, homeButton, name2);
        sortingLayout.getChildren().addAll(sortingTitle, mergeSortButton, homeButton2, name3);

        homeLayout.setMargin(homeTitle, new Insets(-250, 0, 0, 0));
        homeLayout.setMargin(pathfindButton, new Insets(50, 0, 0, 0));
        homeLayout.setMargin(sortingButton, new Insets(150, 0, 0, 0));
        homeLayout.setMargin(name, new Insets(675, -850, 0, 0));

        pathfindLayout.setMargin(pathfindTitle, new Insets(-250, 0, 0, 0));
        pathfindLayout.setMargin(aStarButton, new Insets(50, 0, 0, 0));
        pathfindLayout.setMargin(homeButton, new Insets(150, 0, 0, 0));
        pathfindLayout.setMargin(name2, new Insets(675, -850, 0, 0));

        sortingLayout.setMargin(sortingTitle, new Insets(-250, 0, 0, 0));
        sortingLayout.setMargin(mergeSortButton, new Insets(50, 0, 0, 0));
        sortingLayout.setMargin(homeButton2, new Insets(150, 0, 0, 0));
        sortingLayout.setMargin(name3, new Insets(675, -850, 0, 0));


        BorderPane mainPane = new BorderPane();
        BorderPane pathfindPane = new BorderPane();
        BorderPane sortingPane = new BorderPane();

        mainPane.setCenter(homeLayout);
        pathfindPane.setCenter(pathfindLayout);
        sortingPane.setCenter(sortingLayout);

        homeScene = new Scene(mainPane, 1024, 720);
        pathfindScene = new Scene(pathfindPane, 1024, 720);
        sortingScene = new Scene(sortingPane, 1024, 720);

        stage.setScene(homeScene);
        stage.show();
    }

    public static void main(String[] args) {
        // sets up Application
        launch(args);
    }
}
