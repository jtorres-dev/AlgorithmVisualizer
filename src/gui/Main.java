package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
  private int size;

  @Override
  public void start(Stage stage) {
    stage.setTitle("Algorithm Visualizer");
    stage.setResizable(false);

    // scene creations
    homeScene = createHomeScene(stage);
      pathfindScene = createPathfindScene(stage);
        aStarSettings = createAStarSettings(stage);

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
    // potentially change to GridPane
    StackPane homeLayout = new StackPane();

    homeLayout.getChildren().addAll(homeTitle, pathfindButton, sortingButton, name);

    /*                                  Insets(top, right, bottom, left) */
    StackPane.setMargin(homeTitle, new Insets(0, 0, 250, 0));
    StackPane.setMargin(pathfindButton, new Insets(50, 0, 0, 0));
    StackPane.setMargin(sortingButton, new Insets(150, 0, 0, 0));
    StackPane.setMargin(name, new Insets(675, 0, 0, 1100));

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
    StackPane.setMargin(pathfindTitle,  new Insets(0, 0, 250, 0));
    StackPane.setMargin(aStarButton,  new Insets(50, 0, 0, 0));
    StackPane.setMargin(homeButton,  new Insets(150, 0, 0, 0));
    StackPane.setMargin(name,  new Insets(675, 0, 0, 1100));

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
    nextButton.setDisable(true);
    gridPane.add(nextButton, 2, 5);

    nextButton.setOnAction(event ->  {
      aStarScene = createAStarScene(stage);
      stage.setScene(aStarScene);
    });

    ComboBox<String> gridSizes = createGridOptions();
    gridPane.add(gridSizes, 1, 1);

    gridSizes.setOnAction(event -> {
      setGridSize(gridSizes.getValue());
      nextButton.setDisable(false);
    });

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

    return gridSizes;
  }

  private void setGridSize(String response) {

    /* TODO:
       Using a space delimiter to tokenize response
       and grabbing the first int could be cleaner code.
       Better scalability as well.
    */
    switch (response) {
      case "5 x 5": size = 5;
        break;
      case "6 x 6": size = 6;
        break;
      case "7 x 7": size = 7;
        break;
      case "8 x 8": size = 8;
        break;
      case "9 x 9": size = 9;
        break;
      case "10 x 10": size = 10;
        break;
      case "11 x 11": size = 11;
        break;
      case "12 x 12": size = 12;
        break;
      case "13 x 13": size = 13;
        break;
      case "14 x 14": size = 14;
        break;
      case "15 x 15": size = 15;
        break;
      case "16 x 16": size = 16;
        break;
      case "17 x 17": size = 17;
        break;
      case "18 x 18": size = 18;
        break;
      case "19 x 19": size = 19;
        break;
      case "20 x 20": size = 20;
        break;
      default: size = 5;
        break;
    }
  }

  // for loop is going to create different objects. If
  // tiles are not different objects, Children duplication
  // related exceptions get thrown.
  private class Tile extends StackPane {
    Tile(double width) {
      Rectangle border = new Rectangle(width, width);
      border.setFill(null);
      border.setStroke(Color.BLACK);

      setAlignment(Pos.CENTER);
      getChildren().addAll(border);
    }
  }

  private Scene createAStarScene(Stage stage) {
    Button backButton = new Button("Back");
    backButton.setAlignment(Pos.BOTTOM_LEFT);
    backButton.setOnAction(event -> stage.setScene(aStarSettings));

    BorderPane aStarPane = new BorderPane();
    HBox hbox = new HBox(40);
    hbox.getChildren().add(backButton);
    hbox.setPadding(new Insets(20, 20, 30, 40));
    aStarPane.setBottom(hbox);
    // temp
    int squareSize = 40;


    // if you select 12 x 12, the program no longer goes back
    for(int i = 0; i < size; i++) {
      for(int j = 0; j < size; j++) {
        Tile tile = new Tile(squareSize);
        tile.setAlignment(Pos.CENTER);
        tile.setPadding(new Insets(475, 0, 0, 1100));
        tile.setTranslateX(j * squareSize);
        tile.setTranslateY(i * squareSize);
        aStarPane.getChildren().add(tile);
      }
    }

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
    StackPane.setMargin(sortingTitle, new Insets(-250, 0, 0, 0));
    StackPane.setMargin(mergeSortButton, new Insets(50, 0, 0, 0));
    StackPane.setMargin(homeButton, new Insets(150, 0, 0, 0));
    StackPane.setMargin(name, new Insets(675, -1100, 0, 0));

    BorderPane sortingPane = new BorderPane();
    sortingPane.setCenter(sortingLayout);

    return new Scene(sortingPane, H_RES, V_RES, BACKGROUND);
  }

  public static void main(String[] args) {
    // sets up Application
    launch(args);
  }
}
