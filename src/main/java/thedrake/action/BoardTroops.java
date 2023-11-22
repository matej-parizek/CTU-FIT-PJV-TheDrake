package thedrake.action;

import java.io.PrintWriter;
import java.util.*;

public class BoardTroops implements JSONSerializable{
    private final PlayingSide playingSide;
    private final Map<BoardPos, TroopTile> troopMap;
    private final TilePos leaderPosition;
    private final int guards;

    public BoardTroops(PlayingSide playingSide) {
        this(playingSide,Collections.emptyMap(),TilePos.OFF_BOARD,0);
    }

    public BoardTroops(PlayingSide playingSide,Map<BoardPos, TroopTile> troopMap, TilePos leaderPosition, int guards) {
        this.troopMap=troopMap;
        this.playingSide=playingSide;
        this.guards=guards;
        this.leaderPosition=leaderPosition;
    }

    public Optional<TroopTile> at(TilePos pos) {
        return Optional.ofNullable((TroopTile)this.troopMap.get(pos));
    }

    public PlayingSide playingSide() {
        return this.playingSide;
    }

    public TilePos leaderPosition() {
        return this.leaderPosition;
    }

    public int guards() {
        return this.guards;
    }

    public boolean isLeaderPlaced() {
        return this.leaderPosition==TilePos.OFF_BOARD ? false : true;
    }

    public boolean isPlacingGuards() {
        return this.leaderPosition!=TilePos.OFF_BOARD && this.guards<2;
    }

    public Set<BoardPos> troopPositions() {
        return this.troopMap.keySet();
    }

    public BoardTroops placeTroop(Troop troop, BoardPos target) {
        if(this.at(target).isPresent()){
            throw new IllegalArgumentException();
        }
        else {
            Map<BoardPos,TroopTile> newTroopMap= new HashMap<>(this.troopMap);
            newTroopMap.put(target,new TroopTile(troop,this.playingSide,TroopFace.AVERS));
            TilePos newLeader=this.leaderPosition();
            if(this.leaderPosition()==TilePos.OFF_BOARD){
                newLeader=target;
            }
            int newGuard=this.guards;
            if(this.isPlacingGuards()){
                newGuard+=1;
            }
            return new BoardTroops(playingSide(),newTroopMap,newLeader,newGuard);
        }
    }

    public BoardTroops troopStep(BoardPos origin, BoardPos target) {
        if (!this.isLeaderPlaced()) {
            throw new IllegalStateException(
                    "Cannot move troops before the leader is placed.");
        }

        if (this.isPlacingGuards()) {
            throw new IllegalStateException(
                    "Cannot move troops before guards are placed.");
        }
        if(this.at(origin).isEmpty() || this.at(target).isPresent()){
            throw new IllegalArgumentException();
        }
        else {
            Map <BoardPos,TroopTile> newTroop=new HashMap<>(this.troopMap);
            TroopTile tile=newTroop.remove(origin);
            newTroop.put(target, tile.flipped());

            TilePos newLeaderPosition=this.leaderPosition;
            if(this.leaderPosition.equals(origin)){
                newLeaderPosition=target;
            }
            return new BoardTroops(this.playingSide(),newTroop, newLeaderPosition, this.guards);
        }
    }

    public BoardTroops troopFlip(BoardPos origin) {
        if (!isLeaderPlaced()) {
            throw new IllegalStateException(
                    "Cannot move troops before the leader is placed.");
        }

        else if (isPlacingGuards()) {
            throw new IllegalStateException(
                    "Cannot move troops before guards are placed.");
        }

        else if (!at(origin).isPresent())
            throw new IllegalArgumentException();
        else {
            Map<BoardPos, TroopTile> newTroops = new HashMap<>(troopMap);
            TroopTile tile = newTroops.remove(origin);
            newTroops.put(origin, tile.flipped());

            return new BoardTroops(this.playingSide(), newTroops, leaderPosition(), this.guards);
        }
    }

    public BoardTroops removeTroop(BoardPos target) {
        if (!isLeaderPlaced()) {
            throw new IllegalStateException(
                    "Cannot move troops before the leader is placed.");
        }

        if (isPlacingGuards()) {
            throw new IllegalStateException(
                    "Cannot move troops before guards are placed.");
        }

        if (!at(target).isPresent())
            throw new IllegalArgumentException();
        else {
            Map<BoardPos, TroopTile> newTroops = new HashMap<>(troopMap);
            newTroops.remove(target);
            TilePos newLeader=leaderPosition();
            if(newLeader.equals(target))
                newLeader=TilePos.OFF_BOARD;
            return new BoardTroops(playingSide(),newTroops,newLeader,guards());
        }
    }


    private void troopMapToJSON(PrintWriter writer){
        writer.printf("{");

        SortedMap<String,TroopTile> sort = new TreeMap<>();

        for(Map.Entry<BoardPos,TroopTile> tmp : this.troopMap.entrySet())
            sort.put(tmp.getKey().toString(),tmp.getValue());
        for(Map.Entry<String,TroopTile> buffer: sort.entrySet()) {
            if(!buffer.getKey().equals(sort.firstKey()))
                writer.printf(",");
            writer.printf("\""+buffer.getKey()+"\"");
            writer.printf(":");
            buffer.getValue().toJSON(writer);
        }
        writer.printf("}");
    }
    @Override
    public void toJSON(PrintWriter writer) {
        writer.printf("{");
        writer.printf("\"side\":");
        this.playingSide.toJSON(writer);
        writer.printf(",\"leaderPosition\":\"%s\"",leaderPosition());
        writer.printf(",\"guards\":%s",this.guards);
        writer.printf(",\"troopMap\":");
        this.troopMapToJSON(writer);
        writer.printf("}");
    }
}
