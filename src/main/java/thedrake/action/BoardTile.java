package thedrake.action;

import java.io.PrintWriter;
import java.util.List;

//prazdne dllazdice a hory
public interface BoardTile extends Tile {
    BoardTile EMPTY = new BoardTile() {

        @Override
        public boolean canStepOn() {
            return true;
        }

        @Override
        public List<Move> movesFrom(BoardPos pos, GameState state) {
            return null;
        }

        @Override
        public boolean hasTroop() {
            return false;
        }

        @Override
        public String toString() {
            return "empty";
        }

        @Override
        public void toJSON(PrintWriter writer) {
            writer.printf("\"empty\"");
        }
    };

    BoardTile MOUNTAIN = new BoardTile() {
        @Override
        public boolean canStepOn() {
            return false;
        }

        @Override
        public boolean hasTroop() {
            return false;
        }

        @Override
        public List<Move> movesFrom(BoardPos pos, GameState state) {
            return null;
        }

        @Override
        public String toString() {
            return "mountain";
        }

        @Override
        public void toJSON(PrintWriter writer) {
            writer.printf("\"mountain\"");
        }
    };
}
