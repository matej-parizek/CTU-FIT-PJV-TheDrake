package thedrake.thedrake;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import thedrake.action.BoardPos;
import thedrake.action.GameState;
import thedrake.action.Move;
import thedrake.animation.BoardView;
import thedrake.animation.GameWindow;

import java.io.IOException;
import java.util.Stack;

public class BoardAction {
    private  Stack<Stack<Rectangle>> stack;
    private GameState gameState;
    private boolean clickOn=false;
    public BoardAction(){}
    private  GameWindow gameWindow;
    public BoardAction(GameState gameState, Stack<Stack<Rectangle>> stack , GameWindow gameWindow) {
        this.gameState=gameState;
        this.stack=stack;
        this.gameWindow=gameWindow;
    }
    //TODO: KLIKDANI DO POLE a poku to je hrac na hraci desce + a je z Playing side ukazat kam muze jit a prenastaviit clicOn
    //TODO: Posunout hrace na hraci desku podle Shift/Strike/Slide action
    //TODO: Udelat si pripravu pro PlaceFromStack

    //Po
    public void clickPlaceFromStack(Rectangle rect, BoardPos boardPos){
        rect.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
               // System.out.println("Click on board by STACK"+boardPos.i()+" "+boardPos.j());
                ChangeScene changeScene=new ChangeScene(gameState,gameWindow);
                try {
                    changeScene.movingFromStack(boardPos,gameWindow.getStackBoard());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public void clickPlaceOnBoard(Rectangle rect, BoardPos origin,BoardPos target, Move move){
        rect.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
              //  System.out.println("Click on board by BOARDTROOPS"+origin.i()+" "+origin.j()+" "+move.getClass().getName());
                ChangeScene changeScene=new ChangeScene(gameState,gameWindow);
                try {
                    changeScene.movingOnBoard(origin,target,gameWindow.getStackBoard(),move);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public void activeTroops(){
        for(BoardPos boardPos : gameState.armyOnTurn().boardTroops().troopPositions()){
            clickOnPlayingTroop(stack.get(boardPos.i()).get(boardPos.j()),boardPos);
        }
    }
    private void clickOnPlayingTroop(Rectangle rect, BoardPos boardPos){
        BoardView boardView=new BoardView(this.gameState.armyOnTurn().boardTroops(),stack, this.gameState,gameWindow);
        rect.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                boardView.troopMove(boardPos);
            }
        });
    }
    public void clearEvent(Rectangle rect){
        if(rect.getOnMouseClicked()!=null){
            rect.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                }
            });
        }
    }
}
