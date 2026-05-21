import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.*;

public class NotepadApp extends Application {

    private TextArea textArea = new TextArea();
    private File currentFile = null;

    @Override
    public void start(Stage stage) {

        // MENU BAR
        MenuBar menuBar = new MenuBar();

        // FILE MENU
        Menu fileMenu = new Menu("File");

        MenuItem newFile = new MenuItem("New");
        MenuItem open = new MenuItem("Open");
        MenuItem save = new MenuItem("Save");
        MenuItem saveAs = new MenuItem("Save As");
        MenuItem exit = new MenuItem("Exit");

        fileMenu.getItems().addAll(
                newFile,
                open,
                save,
                saveAs,
                new SeparatorMenuItem(),
                exit
        );

        // EDIT MENU
        Menu editMenu = new Menu("Edit");

        MenuItem cut = new MenuItem("Cut");
        MenuItem copy = new MenuItem("Copy");
        MenuItem paste = new MenuItem("Paste");
        MenuItem selectAll = new MenuItem("Select All");

        editMenu.getItems().addAll(
                cut,
                copy,
                paste,
                selectAll
        );

        menuBar.getMenus().addAll(fileMenu, editMenu);

        // ROOT LAYOUT
        BorderPane root = new BorderPane();

        root.setTop(menuBar);
        root.setCenter(textArea);

        // FILE ACTIONS

        newFile.setOnAction(e -> {
            textArea.clear();
            currentFile = null;
        });

        open.setOnAction(e -> openFile(stage));

        save.setOnAction(e -> saveFile(stage));

        saveAs.setOnAction(e -> saveAsFile(stage));

        exit.setOnAction(e -> stage.close());

        // EDIT ACTIONS

        cut.setOnAction(e -> textArea.cut());

        copy.setOnAction(e -> textArea.copy());

        paste.setOnAction(e -> textArea.paste());

        selectAll.setOnAction(e -> textArea.selectAll());

        // SCENE

        Scene scene = new Scene(root, 800, 500);

        stage.setTitle("JavaFX Notepad");

        stage.setScene(scene);

        stage.show();
    }

    // OPEN FILE
    private void openFile(Stage stage) {

        FileChooser fileChooser = new FileChooser();

        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {

            try {

                BufferedReader br = new BufferedReader(new FileReader(file));

                String line;

                StringBuilder content = new StringBuilder();

                while ((line = br.readLine()) != null) {

                    content.append(line).append("\n");
                }

                br.close();

                textArea.setText(content.toString());

                currentFile = file;

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }

    // SAVE FILE
    private void saveFile(Stage stage) {

        try {

            if (currentFile == null) {

                FileChooser fileChooser = new FileChooser();

                currentFile = fileChooser.showSaveDialog(stage);
            }

            if (currentFile != null) {

                FileWriter writer = new FileWriter(currentFile);

                writer.write(textArea.getText());

                writer.close();
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // SAVE AS
    private void saveAsFile(Stage stage) {

        try {

            FileChooser fileChooser = new FileChooser();

            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {

                FileWriter writer = new FileWriter(file);

                writer.write(textArea.getText());

                writer.close();

                currentFile = file;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        launch();
    }
}