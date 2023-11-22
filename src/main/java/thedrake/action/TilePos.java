package thedrake.action;

import java.io.PrintWriter;
import java.util.List;

//Predstavuje dlazdice na hracim planu
//dlazdice jsou reprezentovany jako souradnice i,j
/*
levy horni roh [0,0] nebo lidsky= a1,c4 apod
*/
 public interface TilePos extends JSONSerializable {
     // která představuje pozici, která se nachází mimo
     // hrací desku. Vždy, když by nějaká z metod step...
     // měla vykročit mimo desku, vrací právě pozici OFF_BOARD.
    public static final TilePos OFF_BOARD = new TilePos() {

        @Override
        public int i() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int j() {
            throw new UnsupportedOperationException();
        }

        @Override
        public char column() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int row() {
            throw new UnsupportedOperationException();
        }
        //vytvoří novou souřadnici posunutou ve směru zadaného offsetu
        @Override
        public TilePos step(int columnStep, int rowStep) {
            throw new UnsupportedOperationException();
        }
        //vytvoří novou souřadnici posunutou ve směru zadaného offsetu
        @Override
        public TilePos step(Offset2D step) {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<TilePos> neighbours() {
            throw new UnsupportedOperationException();
        }

        //vrátí true, pokud tato pozice sousedí se zadanou pozicí.
        @Override
        public boolean isNextTo(TilePos pos) {
            throw new UnsupportedOperationException();
        }

        @Override
        public TilePos stepByPlayingSide(Offset2D dir, PlayingSide side) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equalsTo(int i, int j) {
            return false;
        }

        @Override
        public String toString() {
            return "off-board";
        }

         @Override
         public void toJSON(PrintWriter writer){
            writer.printf("\"off-board\"");
         }
     };

    public int i();

    public int j();

    public char column();

    public int row();
    //vytvoří novou souřadnici posunutou ve směru zadaného offsetu
    public TilePos step(int columnStep, int rowStep);
    //vytvoří novou souřadnici posunutou ve směru zadaného offsetu
    public TilePos step(Offset2D step);

    public List<? extends TilePos> neighbours();

    public boolean isNextTo(TilePos pos);
    //navíc dělá to, že posune tuto souřadnici podle barvy hráče
    // Pokud hraje modrý, funguje jako metoda step().Pokud hraje oranžový, udělá posun s obrácenou y souřadnicí.
    public TilePos stepByPlayingSide(Offset2D dir, PlayingSide side);

    public boolean equalsTo(int i, int j);

    @Override
    public void toJSON(PrintWriter writer);
}
