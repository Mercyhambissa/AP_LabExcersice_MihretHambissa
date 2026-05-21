import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.util.Base64;

public class ChatClient extends Application {

    TextArea chatArea = new TextArea();
    TextField input = new TextField();
    TextField username = new TextField();

    PrintWriter out;

    @Override
    public void start(Stage stage) {

        chatArea.setEditable(false);

        username.setPromptText("Enter username");

        Button connectBtn = new Button("Connect");
        Button sendBtn = new Button("Send");
        Button imgBtn = new Button("Image");

        HBox top = new HBox(10, username, connectBtn);
        HBox bottom = new HBox(10, input, sendBtn, imgBtn);

        VBox root = new VBox(10, top, chatArea, bottom);

        VBox.setVgrow(chatArea, Priority.ALWAYS);
        top.setPadding(new Insets(10));
        bottom.setPadding(new Insets(10));

        connectBtn.setOnAction(e -> connect());
        sendBtn.setOnAction(e -> sendMessage());
        imgBtn.setOnAction(e -> sendImage(stage));

        stage.setScene(new Scene(root, 600, 400));
        stage.setTitle("Chat Client");
        stage.show();
    }

    void connect() {

        try {

            Socket socket = new Socket("localhost", 5000);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            out = new PrintWriter(socket.getOutputStream(), true);

            // send username first
            out.println(username.getText());

            Thread t = new Thread(() -> {

                try {

                    String msg;

                    while ((msg = in.readLine()) != null) {

                        String finalMsg = msg;

                        Platform.runLater(() ->
                                chatArea.appendText(finalMsg + "\n")
                        );
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            t.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void sendMessage() {

        if (!input.getText().isEmpty()) {

            out.println(input.getText());

            input.clear();
        }
    }

    void sendImage(Stage stage) {

        try {

            FileChooser fc = new FileChooser();

            File file = fc.showOpenDialog(stage);

            if (file != null) {

                byte[] data = Files.readAllBytes(file.toPath());

                String encoded = Base64.getEncoder().encodeToString(data);

                out.println("[IMG]" + encoded);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}