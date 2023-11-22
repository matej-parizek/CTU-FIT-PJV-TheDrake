package thedrake.thedrake;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import thedrake.action.*;
import thedrake.animation.*;

import java.io.IOException;
import java.net.URL;

public class Playing {
    private BoardView boardView;
    private GameState gameState;
    private GameWindow gameWindowController;
    private StackView stackView;
    public Playing(GameWindow gameWindowController, GameState gameState) {
        this.gameState=gameState;
        this.gameWindowController=gameWindowController;
        this.stackView= new StackView(gameWindowController,gameState);
        this.boardView=new BoardView(gameState.armyOnTurn().boardTroops(),gameWindowController.getStackBoard(),gameState,gameWindowController);

    }
    @FXML
    public void PlayingON() throws IOException {
        stackView.update();
        boardView.ShowingTroops();

        //TODO:HBOX OHRANICENI
        if(gameState.armyOnTurn().side()==PlayingSide.BLUE){
            gameWindowController.blueSide.setOpacity(1);
            gameWindowController.orangeSide.setOpacity(0);
        }
        if(gameState.armyOnTurn().side()==PlayingSide.ORANGE){
            gameWindowController.orangeSide.setOpacity(1);
            gameWindowController.blueSide.setOpacity(0);
        }


        if(gameState.armyOnTurn().boardTroops().isLeaderPlaced())
            if(!CheckMoves())
                gameState=gameState.draw();
        if(gameState.result()== GameResult.VICTORY) {
            if (gameState.armyOnTurn().side() == PlayingSide.ORANGE) {
                WinnerPlayer1View winnerPlayer1View = new WinnerPlayer1View(gameWindowController);

            }
            if (gameState.armyOnTurn().side() == PlayingSide.BLUE) {

                WinnerPlayer2View winnerPlayer2View = new WinnerPlayer2View(gameWindowController);
            }
        }
        if (gameState.result()==GameResult.DRAW){
            DrawView drawView=new DrawView(gameWindowController);
        }
        BoardAction boardAction=new BoardAction(gameState,gameWindowController.getStackBoard(),gameWindowController);
        StackAction stackAction=new StackAction(gameState,gameWindowController);
        boardAction.activeTroops();
        stackAction.ClickPlaceFromStack();

    }
    private boolean CheckMoves(){
        for(BoardPos origin : gameState.armyOnTurn().boardTroops().troopPositions()){
            for(BoardPos targetFromStack :origin.neighbours()){
                if(gameState.canPlaceFromStack(targetFromStack))
                    return true;
            }
            if(gameState.armyOnTurn().boardTroops().at(origin).isPresent()){
                TroopFace troopFace= gameState.armyOnTurn().boardTroops().at(origin).get().face();
               for ( TroopAction action:gameState.armyOnTurn().boardTroops().at(origin).get().troop().actions(troopFace)){
                   for(Move move: action.movesFrom(origin,gameState.armyOnTurn().side(),gameState)){
                       return true;
                   }
               }

            }
        }
        return false;
    }
}
