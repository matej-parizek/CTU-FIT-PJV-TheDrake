package thedrake.action;

import java.io.PrintWriter;

public interface JSONSerializable {
    public void toJSON(PrintWriter writer);
}
