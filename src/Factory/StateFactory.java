package Factory;

import MoveState.*;
import com.sun.media.sound.RIFFInvalidDataException;

public class StateFactory {
    public static State clone(State state) {
        if (state instanceof Right) {
            return new Right(((Right) state).getTank(), ((Right) state).getTankImage());
        } else if (state instanceof Left) {
            return new Left(((Left) state).getTank(), ((Left) state).getTankImage());
        } else if (state instanceof Down) {
            return new Down(((Down) state).getTank(), ((Down) state).getTankImage());
        } else if (state instanceof Up) {
            return new Up(((Up) state).getTank(), ((Up) state).getTankImage());
        } else if (state instanceof Stop) {
            return new Stop(((Stop) state).getTank(), ((Stop) state).getPreState(), ((Stop) state).getPressed());
        } else {
            return null;
        }
    }
}
