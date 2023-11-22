package thedrake.animation;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import thedrake.action.*;
import thedrake.thedrake.BoardAction;

import java.util.Stack;

public class BoardView extends ActionTroopView {
    private BoardTroops boardTroops;
    private Stack<Stack<Rectangle>> stackBoard;
    private GameState gameState;
    private GameWindow gameWindow;
            //TODO: Board a BoardPos kouknout se na toto
    //TODO:Board kontrolovat uda neco neni na pozici
    public BoardView(Stack<Stack<Rectangle>> stackBoard){
        super(stackBoard);
        this.stackBoard=stackBoard;

    }
    public BoardView(BoardTroops boardTroops, Stack<Stack<Rectangle>> stackBoard,GameState gameState,GameWindow gameWindow){
        super(boardTroops, stackBoard, gameState,gameWindow);
        this.stackBoard=stackBoard;
        this.gameState=gameState;
        this.boardTroops=boardTroops;
        this.gameWindow=gameWindow;
    }

    public void showStepOnFromStuck(){
        this.ShowingTroops();
        if(!this.boardTroops.isLeaderPlaced()){
            beginning();
        }
        else if(this.boardTroops.isPlacingGuards())
            this.guardPlacing();
        else
            showFromStack();
}
    private void beginning(){
        if(boardTroops.playingSide()==PlayingSide.BLUE){
            for(int i=0; i<stackBoard.size(); ++i ){
                Rectangle rectangle=stackBoard.get(i).get(0);
                if(gameState.canPlaceFromStack(new BoardPos(gameState.board().dimension(),i,0))){
                    rectangle.setFill(Paint.valueOf("1CB02DFF"));
                    BoardPos boardPos=new BoardPos(gameState.board().dimension(),i,0);
                    BoardAction action=new BoardAction(gameState,stackBoard,gameWindow);
                    action.clickPlaceFromStack(this.stackBoard.get(boardPos.i()).get(boardPos.j()),boardPos);
                }
            }
        }
        if(boardTroops.playingSide()==PlayingSide.ORANGE){
            for(int i=0; i<stackBoard.size(); ++i ){
                Rectangle rectangle=stackBoard.get(i).get(stackBoard.size()-1);
                if(gameState.canPlaceFromStack(new BoardPos(gameState.board().dimension(),i, stackBoard.size()-1))){
                    rectangle.setFill(Paint.valueOf("1CB02DFF"));
                    BoardPos boardPos=new BoardPos(gameState.board().dimension(),i,stackBoard.size()-1);
                    BoardAction action=new BoardAction(gameState,stackBoard,gameWindow);
                    action.clickPlaceFromStack(this.stackBoard.get(boardPos.i()).get(boardPos.j()),boardPos);
                }
            }
        }
    }
    private void guardPlacing(){
        for( TilePos tilePos :boardTroops.leaderPosition().neighbours()){
            if(gameState.canPlaceFromStack(tilePos)) {
                BoardAction action=new BoardAction(gameState,stackBoard,gameWindow);
                action.clickPlaceFromStack(this.stackBoard.get(tilePos.i()).get(tilePos.j()),(BoardPos)tilePos);
                this.stackBoard.get(tilePos.i()).get(tilePos.j()).setFill(Paint.valueOf("1CB02DFF"));
            }
        }
    }
    private void showFromStack(){
        for (BoardPos boardPos : boardTroops.troopPositions()){
            for(BoardPos tmp :boardPos.neighbours() ){
                if (gameState.canPlaceFromStack(tmp)) {
                    BoardAction action=new BoardAction(gameState,stackBoard,gameWindow);
                    action.clickPlaceFromStack(this.stackBoard.get(tmp.i()).get(tmp.j()),tmp);
                    this.stackBoard.get(tmp.i()).get(tmp.j()).setFill(Paint.valueOf("1CB02DFF"));
                }
            }
        }
    }

    public  void Mountains(Board.TileAt... ats){
       Image image= new Image("C:\\Users\\matpa\\OneDrive\\Plocha\\THEDRAKE\\src\\main\\resources\\Images\\mountain.png");
        for ( Board.TileAt tmp : ats){
            stackBoard.get(tmp.pos.i()).get(tmp.pos.j()).setFill(new ImagePattern(image));
        }

    }

}
