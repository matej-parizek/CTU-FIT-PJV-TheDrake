package thedrake.action;

import java.io.PrintWriter;

public class Board implements JSONSerializable {
    private final int dimension;
    private final BoardTile[][] tiles;

    // Konstruktor. Vytvoří čtvercovou hrací desku zadaného rozměru, kde všechny dlaždice jsou prázdné, tedy BoardTile.EMPTY
    public Board(int dimension) {
        this.dimension=dimension;
        this.tiles = new BoardTile[dimension][dimension];
        for (int i=0; i<dimension;i++){
            for (int j=0; j<dimension; j++){
                tiles[j][i]=BoardTile.EMPTY;
            }
        }
    }
    private Board(int dimension, BoardTile[][] tiles){
        this.dimension=dimension;
        this.tiles=tiles;
    }

    // Rozměr hrací desky
    public int dimension() {return dimension;}

    // Vrací dlaždici na zvolené pozici.
    public BoardTile at(TilePos pos) {return tiles[pos.i()][pos.j()];}

    //kopie desky
    private static BoardTile[][] copyTiles(BoardTile[][] tiles){
        BoardTile[][] newTiles = new BoardTile[tiles.length][];

        for(int i=0; i<tiles.length; i++){
            newTiles[i]=tiles[i].clone();
        }
        return newTiles;
    }


    // Vytváří novou hrací desku s novými dlaždicemi. Všechny ostatní dlaždice zůstávají stejné
    public Board withTiles(TileAt... ats) {

        BoardTile[][] newTiles= copyTiles(this.tiles);

        //Prochazi pocet neznamych ats;
        //A nasledne prida kameny nove mountiens(hory)

        for ( TileAt tmp : ats){
            newTiles[tmp.pos.i()][tmp.pos.j()]=tmp.tile;
        }
        return new Board(this.dimension,newTiles);
    }

    // Vytvoří instanci PositionFactory pro výrobu pozic na tomto hracím plánu
    public PositionFactory positionFactory() {
        return new PositionFactory(this.dimension);
    }

    public static class TileAt {
        public final BoardPos pos;
        public final BoardTile tile;

        public TileAt(BoardPos pos, BoardTile tile) {
            this.pos = pos;
            this.tile = tile;
        }
    }

    @Override
    public void toJSON(PrintWriter writer) {
        writer.printf("{");
        writer.printf("\"dimension\":%d",this.dimension);
        writer.printf(",\"tiles\":[");
        //Nacinani je [j,i] a ne [i,j] pozor na to zminit u cviciciho
        int count=0;
        for( int i=0; i<this.tiles.length; i++)
            for (int j=0 ; j<this.tiles[i].length;j++) {
                this.tiles[j][i].toJSON(writer);
                count++;
                if(count<this.dimension*this.dimension)
                    writer.printf(",");
            }
        writer.printf("]");
        writer.printf("}");
    }
}

