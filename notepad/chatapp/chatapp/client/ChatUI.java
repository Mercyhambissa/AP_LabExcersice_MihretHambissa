import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        ChatController chat = new ChatController();

        VBox root = new VBox(10,
                chat.getUsernameField(),
                chat.getChatArea(),
                chat.getMessageField(),
                chat.getSendBtn()
        );

        Scene scene = new Scene(root, 400, 500);

        stage.setTitle("Multi-User Chat System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}