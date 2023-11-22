package thedrake.animation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import thedrake.thedrake.Game;


import java.io.IOException;

public class MenuController {

    @FXML
    private Button exitButton;
    @FXML
    private Button signleButton;
    @FXML
    private Button multiButton;
    @FXML
    private Button onlineButton;




    @FXML
    protected void exitClick() throws IOException {
        Stage stage = (Stage) this.exitButton.getScene().getWindow();
        stage.close();
        System.exit(0);
    }

    @FXML
    protected void sigleClick() {

    }

    @FXML
    protected void multiClick() throws IOException,InterruptedException {
        Stage stage = (Stage) this.multiButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Menu.class.getResource("Game.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        scene.getStylesheets().add(getClass().getResource("Visual.css").toExternalForm());
        stage.setTitle("TheDrake");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        Game game= new Game(stage,scene);
        GameWindow gameWindowController=(GameWindow) fxmlLoader.getController();
        game.setup(gameWindowController);
    }

    @FXML
    protected void onlineClick() {
        Stage stage = (Stage) this.onlineButton.getScene().getWindow();
    }
}