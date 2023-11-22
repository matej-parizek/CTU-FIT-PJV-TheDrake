package thedrake.action;

import java.util.ArrayList;
import java.util.List;

public class SlideAction extends TroopAction {
    public SlideAction(Offset2D offset) {
        super(offset);
    }

    public SlideAction(int offsetX, int offsetY) {
        super(offsetX, offsetY);
    }

    @Override
    public List<Move> movesFrom(BoardPos origin, PlayingSide side, GameState state) {
        List<Move> result = new ArrayList();

        TilePos target;
        for(target = origin.stepByPlayingSide(this.offset(), side); state.canStep(origin, target); target = target.stepByPlayingSide(this.offset(), side)) {
            result.add(new StepOnly(origin, (BoardPos)target));
        }

        if (state.canCapture(origin, target)) {
            result.add(new StepAndCapture(origin,(BoardPos) target));
        }
        return result;
    }
}
