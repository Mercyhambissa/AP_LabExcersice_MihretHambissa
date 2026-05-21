import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Random;

public class PokerGame extends Application {

    Label player = new Label("Your Card: -");
    Label computer = new Label("Computer Card: -");
    Label result = new Label("Result: -");

    Random random = new Random();

    @Override
    public void start(Stage stage) {

        Button draw = new Button("Draw Cards");

        draw.setOnAction(e -> play());

        VBox root = new VBox(15, player, computer, draw, result);
        root.setStyle("-fx-padding: 20; -fx-font-size: 16;");

        stage.setScene(new Scene(root, 300, 200));
        stage.setTitle("Poker Game");
        stage.show();
    }

    void play() {

        int p = random.nextInt(13) + 1;
        int c = random.nextInt(13) + 1;

        player.setText("Your Card: " + p);
        computer.setText("Computer Card: " + c);

        if (p > c) {
            result.setText("You Win 🎉");
        } else if (p < c) {
            result.setText("Computer Wins 💻");
        } else {
            result.setText("Draw 🤝");
        }
    }

    public static void main(String[] args) {
        launch();
    }
}