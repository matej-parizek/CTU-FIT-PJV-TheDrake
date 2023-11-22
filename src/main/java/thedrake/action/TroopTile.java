package thedrake.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class TroopTile implements Tile {
    private final Troop troop;
    private final PlayingSide side;
    private final TroopFace face;

    public TroopTile(Troop troop, PlayingSide side, TroopFace face) {
        this.troop = troop;
        this.side = side;
        this.face = face;
    }

    // Vrací barvu, za kterou hraje jednotka na této dlaždici
    public PlayingSide side(){return side;}

    // Vrací stranu, na kterou je jednotka otočena
    public TroopFace face(){return face;}

    // Jednotka, která stojí na této dlaždici
    public Troop troop(){return troop;}
    // Vrací False, protože na dlaždici s jednotkou se nedá vstoupit
    @Override
    public boolean canStepOn() {return false;}

    // Vrací True
    @Override
    public boolean hasTroop() {return true;}

    // Vytvoří novou dlaždici, s jednotkou otočenou na opačnou stranu
    // (z rubu na líc nebo z líce na rub)
    public TroopTile flipped(){
        return this.face==TroopFace.AVERS ?
        new TroopTile(this.troop,this.side,TroopFace.REVERS) :
        new TroopTile(this.troop,this.side,TroopFace.AVERS);
    }

    public List<Move> movesFrom(BoardPos origin, GameState state) {
        List<Move> result = new ArrayList();

        for (TroopAction action : this.troop.actions(this.face)){
            result.addAll(action.movesFrom(origin,this.side,state));
        }

        return result;
    }

    @Override
    public void toJSON(PrintWriter writer) {
        writer.printf("{\"troop\":");
        this.troop.toJSON(writer);
        writer.printf(",\"side\":");
        this.side.toJSON(writer);
        writer.printf(",\"face\":");
        this.face.toJSON(writer);
        writer.printf("}");



    }
}
