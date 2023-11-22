package thedrake.animation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.Collection;
import java.util.Stack;

public class GameWindow{

    @FXML
    private Button exitButton;
    @FXML
    private Button backButton;
    @FXML
    private Button winnerB;
    @FXML
    public VBox blueStack; //6 images
    @FXML
    public VBox orangeStack; //6 images

    @FXML
    private AnchorPane anchorPane;
    @FXML
    public VBox gameBoard;
    //gameBoard
    public Rectangle a0, a1,a2, a3,a4,b0,b1,b2,b3,b4,c0,c1,c2,c3,c4,d0,d1,d2,d3,d4,e0,e1,e2,e3,e4;

    @FXML
    public Rectangle remachButton;
    @FXML
    public Circle blueSide, orangeSide;

    @FXML
    private void exitClick(){
        Stage stage=(Stage) exitButton.getScene().getWindow();
        stage.close();
        System.exit(0);
    }

    @FXML
    public void backClik() throws IOException {
        Stage stage = (Stage)this.backButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Menu.class.getResource("MenuFXML.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        scene.getStylesheets().add(getClass().getResource("Visual.css").toExternalForm());
        stage.setTitle("TheDrake");
        stage.setScene(scene);
        stage.setResizable(false);
    }

    public Stack<Stack<Rectangle>> getStackBoard(){
        Stack<Rectangle> Row1= new Stack<>();
        Row1.add(a0);Row1.add(a1);Row1.add(a2);Row1.add(a3);Row1.add(a4);
        Stack<Rectangle> Row2= new Stack<>();
        Row2.add(b0);Row2.add(b1);Row2.add(b2);Row2.add(b3);Row2.add(b4);
        Stack<Rectangle> Row3= new Stack<>();
        Row3.add(c0);Row3.add(c1);Row3.add(c2);Row3.add(c3);Row3.add(c4);
        Stack<Rectangle> Row4= new Stack<>();
        Row4.add(d0);Row4.add(d1);Row4.add(d2);Row4.add(d3);Row4.add(d4);
        Stack<Rectangle> Row5= new Stack<>();
        Row5.add(e0);Row5.add(e1);Row5.add(e2);Row5.add(e3);Row5.add(e4);
        Stack<Stack<Rectangle>> stack= new Stack<>();
        stack.add(Row1);
        stack.add(Row2);
        stack.add(Row3);
        stack.add(Row4);
        stack.add(Row5);
        return stack;
    }

}
