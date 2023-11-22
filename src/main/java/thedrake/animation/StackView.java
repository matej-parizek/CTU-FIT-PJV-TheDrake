package thedrake.animation;

import thedrake.action.GameState;
import thedrake.action.PlayingSide;
import thedrake.action.Troop;

import java.util.List;

public class StackView {

    private GameWindow controller;
    private GameState gameState;
    public StackView(GameWindow gameWindowController, GameState gameState){
        this.controller=gameWindowController;
        this.controller.blueStack.setSpacing(2);
        this.controller.orangeStack.setSpacing(2);
        this.gameState=gameState;
    }
    public void update(){
        this.controller.blueStack.getChildren().clear();
        this.controller.orangeStack.getChildren().clear();
        stackShow(gameState.armyOnTurn().side(),gameState.armyOnTurn().stack());
        stackShow(gameState.armyNotOnTurn().side(),gameState.armyNotOnTurn().stack());

    }
    private void stackShow(PlayingSide side, List<Troop> stack){
        int counter=0;
        for(Troop troop: stack){
            if(counter==6)
                break;
            TroopView troopView=new TroopView(side, troop);
            if(side==PlayingSide.BLUE){
                this.controller.blueStack.getChildren().add(troopView.getStackView());
            }
            if(side==PlayingSide.ORANGE)
                this.controller.orangeStack.getChildren().add(troopView.getStackView());
            counter+=1;
        }

    }
}
