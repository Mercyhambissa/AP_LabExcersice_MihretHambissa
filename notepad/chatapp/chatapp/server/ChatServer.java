import javafx.application.Platform;
import javafx.scene.control.*;
import java.util.Timer;
import java.util.TimerTask;

public class ChatController {

    TextArea chatArea = new TextArea();
    TextField usernameField = new TextField();
    TextField messageField = new TextField();
    Button sendBtn = new Button("Send");

    public ChatController() {
        setup();
    }

    private void setup() {

        chatArea.setEditable(false);
        chatArea.setPrefHeight(400);

        usernameField.setPromptText("Username");
        messageField.setPromptText("Message");

        sendBtn.setOnAction(e -> sendMessage());

        startAutoRefresh();
    }

    private void sendMessage() {
        String user = usernameField.getText();
        String msg = messageField.getText();

        if (user.isEmpty() || msg.isEmpty()) return;

        MessageDAO.saveMessage(new Message(user, msg));
        messageField.clear();
    }

    private void loadMessages() {
        StringBuilder sb = new StringBuilder();

        for (Message m : MessageDAO.getMessages()) {
            sb.append(m.getUsername())
              .append(": ")
              .append(m.getMessage())
              .append("\n");
        }

        chatArea.setText(sb.toString());
    }

    private void startAutoRefresh() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> loadMessages());
            }
        }, 0, 2000);
    }

    public TextArea getChatArea() {
        return chatArea;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public TextField getMessageField() {
        return messageField;
    }

    public Button getSendBtn() {
        return sendBtn;
    }
}