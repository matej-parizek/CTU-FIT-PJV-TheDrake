package thedrake.animation;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class WinnerPlayer2View {
    private Stage stage;
    public WinnerPlayer2View(GameWindow gameWindow) throws IOException {
         this.stage= new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Menu.class.getResource("WinnerPlayer2View.fxml"));
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
