package thedrake.action;

import java.io.PrintWriter;
import java.util.Optional;

public class GameState implements JSONSerializable{
    private final Board board;
    private final PlayingSide sideOnTurn;
    private final Army blueArmy;
    private final Army orangeArmy;
    private final GameResult result;

    public GameState(
            Board board,
            Army blueArmy,
            Army orangeArmy) {
        this(board, blueArmy, orangeArmy, PlayingSide.BLUE, GameResult.IN_PLAY);
    }

    public GameState(
            Board board,
            Army blueArmy,
            Army orangeArmy,
            PlayingSide sideOnTurn,
            GameResult result) {
        this.board = board;
        this.sideOnTurn = sideOnTurn;
        this.blueArmy = blueArmy;
        this.orangeArmy = orangeArmy;
        this.result = result;
    }

    public Board board() {
        return board;
    }

    public PlayingSide sideOnTurn() {
        return sideOnTurn;
    }

    public GameResult result() {
        return result;
    }

    public Army army(PlayingSide side) {
        if (side == PlayingSide.BLUE) {
            return this.blueArmy;
        }

        return this.orangeArmy;
    }

    public Army armyOnTurn() {
        return army(this.sideOnTurn);
    }

    public Army armyNotOnTurn() {
        if (sideOnTurn == PlayingSide.BLUE)
            return orangeArmy;

        return blueArmy;
    }

    public Tile tileAt(TilePos pos) {
        Optional <TroopTile> troop= this.orangeArmy.boardTroops().at(pos);
        if(troop.isPresent())
            return troop.get();
        troop=this.blueArmy.boardTroops().at(pos);
        if(troop.isPresent())
            return troop.get();
        else
            return board.at(pos);

    }

    private boolean canStepFrom(TilePos origin) {
        if (this.result!=GameResult.IN_PLAY)
            return false;
        else if(origin == TilePos.OFF_BOARD)
            return false;
        else if(this.armyOnTurn().boardTroops().guards()<2)
            return false;
        else {

            Tile newTile = this.tileAt(origin);
            if (newTile.hasTroop()) {
                return (((TroopTile) newTile).side() == this.sideOnTurn);
            }else
                    return false;
        }
    }

    private boolean canStepTo(TilePos target) {
        if(this.result!=GameResult.IN_PLAY)
            return false;
        if(target == TilePos.OFF_BOARD)
            return false;
        Tile newTile=this.tileAt(target);
        return newTile.canStepOn();
    }

    private boolean canCaptureOn(TilePos target) {
        if(this.result!=GameResult.IN_PLAY)
            return false;
        if(target==TilePos.OFF_BOARD)
            return false;
        Tile newTile=this.tileAt(target);
        if (newTile.hasTroop())
            return ((TroopTile)newTile).side()!=this.sideOnTurn;
        return false;
    }

    public boolean canStep(TilePos origin, TilePos target) {
        return canStepFrom(origin) && canStepTo(target);
    }

    public boolean canCapture(TilePos origin, TilePos target) {
        return this.canStepFrom(origin) && this.canCaptureOn(target);
    }

    public boolean canPlaceFromStack(TilePos target) {
        if(this.result!=GameResult.IN_PLAY)
            return false;
        if(target==TilePos.OFF_BOARD)
            return false;
        if(this.armyOnTurn().stack().isEmpty())
            return false;
        Tile newTile= this.tileAt(target);
        if(!newTile.canStepOn())
            return false;
        //game start choose leader position on board
        BoardTroops playing=this.armyOnTurn().boardTroops();
        if(!playing.isLeaderPlaced()){
            if(this.sideOnTurn==PlayingSide.BLUE)
                return target.j()==0;
            else if (this.sideOnTurn==PlayingSide.ORANGE)
                return target.j()== board().dimension()-1;
        }
        //midle game;
        if(playing.isPlacingGuards())
            return playing.leaderPosition().isNextTo(target);

        for(BoardPos pos : playing.troopPositions() ){
            if(pos.isNextTo(target))
                return true;
        }
        return false;
    }

    public GameState stepOnly(BoardPos origin, BoardPos target) {
        if (canStep(origin, target))
            return createNewGameState(
                    armyNotOnTurn(),
                    armyOnTurn().troopStep(origin, target), GameResult.IN_PLAY);

        throw new IllegalArgumentException();
    }

    public GameState stepAndCapture(BoardPos origin, BoardPos target) {
        if (canCapture( origin,target)) {
            Troop captured = armyNotOnTurn().boardTroops().at(target).get().troop();
            GameResult newResult = GameResult.IN_PLAY;

            if (armyNotOnTurn().boardTroops().leaderPosition().equals(target))
                newResult = GameResult.VICTORY;

            return createNewGameState(
                    armyNotOnTurn().removeTroop(target),
                    armyOnTurn().troopStep(origin, target).capture(captured), newResult);
        }

        throw new IllegalArgumentException();
    }

    public GameState captureOnly(BoardPos origin, BoardPos target) {
        if (canCapture(origin, target)) {
            Troop captured = armyNotOnTurn().boardTroops().at(target).get().troop();
            GameResult newResult = GameResult.IN_PLAY;

            if (armyNotOnTurn().boardTroops().leaderPosition().equals(target))
                newResult = GameResult.VICTORY;

            return createNewGameState(
                    armyNotOnTurn().removeTroop(target),
                    armyOnTurn().troopFlip(origin).capture(captured), newResult);
        }

        throw new IllegalArgumentException();
    }

    public GameState placeFromStack(BoardPos target) {
        if (canPlaceFromStack(target)) {
            return createNewGameState(
                    armyNotOnTurn(),
                    armyOnTurn().placeFromStack(target),
                    GameResult.IN_PLAY);
        }

        throw new IllegalArgumentException();
    }

    public GameState resign() {
        return createNewGameState(
                armyNotOnTurn(),
                armyOnTurn(),
                GameResult.VICTORY);
    }

    public GameState draw() {
        return createNewGameState(
                armyOnTurn(),
                armyNotOnTurn(),
                GameResult.DRAW);
    }

    @Override
    public void toJSON(PrintWriter writer) {
        writer.printf("{\"result\":");
            this.result.toJSON(writer);
        writer.printf(",\"board\":");
            this.board.toJSON(writer);
        writer.printf(",\"blueArmy\":");
            this.blueArmy.toJSON(writer);
        writer.printf(",\"orangeArmy\":");
            this.orangeArmy.toJSON(writer);
        writer.printf("}");


    }

    private GameState createNewGameState(Army armyOnTurn, Army armyNotOnTurn, GameResult result) {
        if (armyOnTurn.side() == PlayingSide.BLUE) {
            return new GameState(board, armyOnTurn, armyNotOnTurn, PlayingSide.BLUE, result);
        }

        return new GameState(board, armyNotOnTurn, armyOnTurn, PlayingSide.ORANGE, result);
    }


}
