package thedrake.animation;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import thedrake.action.*;
import thedrake.thedrake.BoardAction;

import java.util.Stack;

public class ActionTroopView {
    private BoardTroops boardTroops;
    private final Stack<Stack<Rectangle>> stackBoard;
    private GameState gameState;
    private GameWindow gameWindow;
    protected Image movingOn= new Image("TheDrake\\src\\main\\resources\\Images\\sipka.png");

    
    public ActionTroopView(Stack<Stack<Rectangle>> stackBoard){
        this.stackBoard=stackBoard;
    }
    public ActionTroopView(BoardTroops boardTroops, Stack<Stack<Rectangle>> stackBoard,GameState gameState,GameWindow gameWindow){
        this.stackBoard=stackBoard;
        this.gameState=gameState;
        this.boardTroops=boardTroops;
        this.gameWindow=gameWindow;
    }

    private BackgroundImage changePattern(Troop troop,TroopTile troopTile){
        TroopView troopView=new TroopView(troopTile.side(), troop);
        //System.out.println(troopTile.troop().name()+" "+troopTile.face() + " "+ troopTile.side());
        if(troopTile.face()==TroopFace.AVERS)
            return new BackgroundImage(troopView.selectAVERS(),
                                        BackgroundRepeat.NO_REPEAT,
                                        BackgroundRepeat.NO_REPEAT,
                                        BackgroundPosition.CENTER,
                    new BackgroundSize(5,5,true,true,true,true));
        else
            return new BackgroundImage(troopView.selectREVERS(),
                                        BackgroundRepeat.NO_REPEAT,
                                        BackgroundRepeat.NO_REPEAT,
                                        BackgroundPosition.DEFAULT,
                    new BackgroundSize(5,5,false,false,false,true));
    }

    private void MoveView(Troop troop,TilePos tilePos){
        TroopTile origin =(TroopTile)this.gameState.tileAt(tilePos);
        for(TroopAction troopAction : troop.actions(origin.face())){
            for ( Move move:troopAction.movesFrom((BoardPos)tilePos,boardTroops.playingSide(),gameState)) {
                Rectangle rect=this.stackBoard.get(move.target().i()).get(move.target().j());
                rect.setFill(new ImagePattern(this.movingOn));
                rect.setOpacity(0.7);
                HBox hBox=(HBox) rect.getParent();
                if(this.gameState.tileAt(move.target()).hasTroop()){
                    TroopTile troopTile =(TroopTile)this.gameState.tileAt(move.target());
                    hBox.setBackground(new Background(this.changePattern(troopTile.troop(),troopTile)));
                }
                else {
                    hBox.setBackground(new Background(new BackgroundFill(Paint.valueOf("#dcdee1"),CornerRadii.EMPTY, Insets.EMPTY)));
                }
                BoardPos boardPos=new BoardPos(gameState.board().dimension(),move.target().i(),move.target().j());
                BoardAction action=new BoardAction(gameState,stackBoard,gameWindow);
                action.clickPlaceOnBoard(this.stackBoard.get(boardPos.i()).get(boardPos.j()),(BoardPos) tilePos,boardPos,move);
            }

        }
    }
    public void troopMove(TilePos tilePos){
        ShowingTroops();
        if (this.boardTroops.at(tilePos).isPresent()) {
            TroopTile troopTile = this.boardTroops.at(tilePos).get();
            this.MoveView(troopTile.troop(), tilePos);
        }
    }
    public void ShowingTroops(){
        for(int i=0; i< this.stackBoard.size();i++){
            for (int j =0;j< this.stackBoard.get(i).size();j++){
                BoardPos boardPos=new BoardPos(gameState.board().dimension(),i,j);
                Boolean BlueTroop=gameState.armyOnTurn().boardTroops().at(boardPos).isPresent();
                Boolean OrangeTroop=gameState.armyNotOnTurn().boardTroops().at(boardPos).isPresent();
                if(gameState.board().at(boardPos).canStepOn() && !(BlueTroop || OrangeTroop)){
                    stackBoard.get(i).get(j).setFill(Paint.valueOf("#dcdee1"));
                    stackBoard.get(i).get(j).setOpacity(1);
                }
                else if(gameState.board().at(boardPos).canStepOn()) {
                    HBox hBox=(HBox)stackBoard.get(i).get(j).getParent();
                    hBox.setBackground(new Background(new BackgroundFill(Paint.valueOf("#dcdee1"),CornerRadii.EMPTY, Insets.EMPTY)));
                    stackBoard.get(i).get(j).setFill(Paint.valueOf("#dcdee1"));
                    stackBoard.get(i).get(j).setOpacity(1);
                }
                //TODO: remove evenHandler
                if(!gameState.armyOnTurn().boardTroops().at(boardPos).isPresent()) {
                    BoardAction boardAction = new BoardAction();
                    boardAction.clearEvent(stackBoard.get(i).get(j));
                }
            }
        }
        troopShow();
    }
    private void troopShow(){
        BoardTroops troopsOnTurn =gameState.armyOnTurn().boardTroops();
        BoardTroops troopsNotOnTurn= gameState.armyNotOnTurn().boardTroops();
        Troops(troopsOnTurn);
        Troops(troopsNotOnTurn);

    }
    private void Troops(BoardTroops boardTroops){
        for(BoardPos troopBoard:boardTroops.troopPositions()){
            TroopView troopView=new TroopView(boardTroops.playingSide(),boardTroops.at(troopBoard).get().troop());
            if(boardTroops.at(troopBoard).isPresent()) {
                Image image = troopView.placeOnBoard(boardTroops.at(troopBoard).get());
                stackBoard.get(troopBoard.i()).get(troopBoard.j()).setFill(new ImagePattern(image));
            }
        }
    }
}
