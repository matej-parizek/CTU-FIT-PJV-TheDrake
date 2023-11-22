package thedrake.thedrake;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import thedrake.action.GameState;
import thedrake.action.PlayingSide;
import thedrake.animation.BoardView;
import thedrake.animation.GameWindow;

public class StackAction {
    private final GameState gameState;
    private final GameWindow gameWindow;
    private boolean stackClick;
    public StackAction(GameState gameState, GameWindow gameWindow) {
        this.gameState=gameState;
        this.gameWindow=gameWindow;
        this.stackClick=false;
    }
    public void ClickPlaceFromStack(){
        if(gameState.sideOnTurn()== PlayingSide.BLUE){
            gameWindow.orangeStack.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {

                }
            });
            gameWindow.blueStack.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                   movingFromStack();
                }
            });
        }
        else if(gameState.sideOnTurn()==PlayingSide.ORANGE) {
            gameWindow.orangeStack.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    movingFromStack();
                }
            });
            gameWindow.blueStack.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {

                }
            });
        }
    }
    private void movingFromStack(){
        BoardView boardView=new BoardView(gameState.armyOnTurn().boardTroops(), gameWindow.getStackBoard(),gameState,gameWindow);
        boardView.showStepOnFromStuck();
    }
}
