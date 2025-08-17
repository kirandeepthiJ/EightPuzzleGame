import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.*;

public class EightPuzzleApp extends Application {
    private static final int SIZE = 3;
    private Button[][] buttons = new Button[SIZE][SIZE];
    private int emptyRow, emptyCol;
    private Label moveCounterLabel;
    private int moveCount = 0;

    private Label timerLabel;
    private int seconds = 0;
    private Timeline timeline;

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.getStyleClass().add("grid-pane");

        moveCounterLabel = new Label("Moves: 0");
        moveCounterLabel.setId("moveLabel");

        timerLabel = new Label("Time: 0s");
        timerLabel.setId("timerLabel");

        int[] numbers = createShuffledArray();
        int index = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Button button = new Button();
                button.setMinSize(100, 100);
                button.setFont(new Font("Verdana", 20));

                styleButton(button, getRandomColor());

                int number = numbers[index++];
                if (number == 0) {
                    emptyRow = i;
                    emptyCol = j;
                    button.setVisible(false);
                } else {
                    button.setText(String.valueOf(number));
                }
                int row = i, col = j;
                button.setOnAction(e -> handleMove(row, col));
                buttons[i][j] = button;
                grid.add(button, j, i);
            }
        }

        Button shuffleBtn = new Button("🔄 Shuffle / Restart");
        shuffleBtn.setId("shuffleButton");
        shuffleBtn.setOnAction(e -> shufflePuzzle());

        HBox controlButtons = new HBox(15, shuffleBtn);
        controlButtons.setAlignment(Pos.CENTER);

        HBox statsBox = new HBox(30, moveCounterLabel, timerLabel);
        statsBox.setAlignment(Pos.CENTER);

        VBox root = new VBox(20, statsBox, grid, controlButtons);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 20;");

        Scene scene = new Scene(root, 420, 550);
        scene.getStylesheets().add(getClass().getResource("ai-style.css").toExternalForm());

        primaryStage.setTitle("🤖 8 Puzzle Game - AI Style");
        primaryStage.setScene(scene);
        primaryStage.show();

        startTimer();
    }

    private void handleMove(int row, int col) {
        if ((Math.abs(emptyRow - row) == 1 && emptyCol == col) ||
                (Math.abs(emptyCol - col) == 1 && emptyRow == row)) {

            Button clicked = buttons[row][col];
            Button empty = buttons[emptyRow][emptyCol];

            empty.setText(clicked.getText());
            empty.setVisible(true);

            clicked.setText("");
            clicked.setVisible(false);

            animateButton(empty);

            emptyRow = row;
            emptyCol = col;

            moveCount++;
            moveCounterLabel.setText("Moves: " + moveCount);

            if (isSolved()) {
                timeline.stop();  // Stop the timer
                Alert winAlert = new Alert(Alert.AlertType.INFORMATION);
                winAlert.setTitle("🎉 Congratulations!");
                winAlert.setHeaderText(null);
                winAlert.setContentText("You Win in " + moveCount + " moves and " + seconds + " seconds!");
                winAlert.showAndWait();
            }
        }
    }

    private void shufflePuzzle() {
        int[] numbers = createShuffledArray();
        int index = 0;
        moveCount = 0;
        moveCounterLabel.setText("Moves: 0");

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int number = numbers[index++];
                Button btn = buttons[i][j];

                if (number == 0) {
                    btn.setText("");
                    btn.setVisible(false);
                    emptyRow = i;
                    emptyCol = j;
                } else {
                    btn.setText(String.valueOf(number));
                    btn.setVisible(true);
                }
            }
        }

        startTimer();  // Reset and start the timer again
    }

    private void startTimer() {
        seconds = 0;
        timerLabel.setText("Time: 0s");

        if (timeline != null) {
            timeline.stop();
        }

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            seconds++;
            timerLabel.setText("Time: " + seconds + "s");
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private int[] createShuffledArray() {
        List<Integer> list = new ArrayList<>();
        do {
            list.clear();
            for (int i = 0; i < SIZE * SIZE; i++) {
                list.add(i);
            }
            Collections.shuffle(list);
        } while (!isSolvable(list));
        return list.stream().mapToInt(i -> i).toArray();
    }

    private boolean isSolvable(List<Integer> list) {
        int inversions = 0;
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i) != 0 && list.get(j) != 0 && list.get(i) > list.get(j)) {
                    inversions++;
                }
            }
        }
        return inversions % 2 == 0;
    }

    private boolean isSolved() {
        int count = 1;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                String text = buttons[i][j].getText();
                if (i == SIZE - 1 && j == SIZE - 1) {
                    return text.equals("");
                }
                if (!text.equals(String.valueOf(count++))) {
                    return false;
                }
            }
        }
        return true;
    }

    private void animateButton(Button button) {
        FadeTransition ft = new FadeTransition(Duration.millis(200), button);
        ft.setFromValue(0.5);
        ft.setToValue(1.0);
        ft.play();
    }

    private void styleButton(Button button, String color) {
        button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-background-radius: 10;");
        if (!button.getStyleClass().contains("button")) {
            button.getStyleClass().add("button");
        }
    }

    private String getRandomColor() {
        String[] colors = {
                "#e74c3c", "#8e44ad", "#3498db", "#16a085",
                "#f39c12", "#d35400", "#2ecc71", "#e67e22"
        };
        return colors[new Random().nextInt(colors.length)];
    }

    public static void main(String[] args) {
        launch(args);
    }
}
