package thedrake.thedrake;

import javafx.scene.Scene;
import javafx.stage.Stage;
import thedrake.action.Board;
import thedrake.action.GameState;
import thedrake.action.StandardDrakeSetup;
import thedrake.action.*;
import thedrake.animation.BoardView;
import thedrake.animation.GameWindow;

import java.io.IOException;

public class Game {
    private Scene scene;
    private Stage stage;
    private StandardDrakeSetup standardDrakeSetup;
    private GameState gameState;
    private Board board;
    private Board.TileAt mountain;
    public Game(Stage stage, Scene scene) {
        this.scene = scene;
        this.stage = stage;
        this.standardDrakeSetup = new StandardDrakeSetup();
        this.board = new Board(5);

        this.mountain = new Board.TileAt(new BoardPos(5,1,1), BoardTile.MOUNTAIN);
        this.board=this.board.withTiles(mountain);
        this.gameState = this.standardDrakeSetup.startState(board);

    }
    public void setup(GameWindow gameWindowController) throws InterruptedException, IOException {
        //zobrazeni hor

        BoardView boardView=new BoardView(gameWindowController.getStackBoard());
        boardView.Mountains(mountain);

        Playing playing=new Playing(gameWindowController,gameState);
        playing.PlayingON();

    }

}
