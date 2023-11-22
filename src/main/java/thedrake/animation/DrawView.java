package thedrake.animation;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class DrawView {
    private Stage stage;
    @FXML
    protected AnchorPane anchorPane;
    public DrawView(GameWindow gameWindow) throws IOException {
        this.stage= new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Menu.class.getResource("DrawView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        scene.getStylesheets().add(getClass().getResource("Visual.css").toExternalForm());
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.show();
        stage.setAlwaysOnTop(true);
        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stage.close();
                try {
                    gameWindow.backClik();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}