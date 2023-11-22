package thedrake.thedrake;

import javafx.scene.shape.Rectangle;
import thedrake.action.*;
import thedrake.animation.GameWindow;

import java.io.IOException;
import java.util.Stack;

public class ChangeScene {
    private GameState gameState;
    private GameWindow gameWindow;
    public ChangeScene(GameState gameState, GameWindow gameWindow) {
        this.gameState=gameState;
        this.gameWindow=gameWindow;
    }
    public void movingOnBoard(BoardPos origin, BoardPos target, Stack<Stack<Rectangle>> stack, Move move) throws IOException {
        if(move.getClass().equals(StepOnly.class))
            gameState=gameState.stepOnly(origin,target);
        else if(move.getClass().equals(CaptureOnly.class))
            gameState=gameState.captureOnly(origin,target);
        else if(move.getClass().equals(StepAndCapture.class))
            gameState=gameState.stepAndCapture(origin,target);
        Playing playing=new Playing(gameWindow,gameState);
        playing.PlayingON();
    }
    public void movingFromStack(BoardPos target, Stack<Stack<Rectangle>> stack) throws IOException {
        this.gameState=gameState.placeFromStack(target);
        Playing playing=new Playing(gameWindow,gameState);
        playing.PlayingON();

    }
}
