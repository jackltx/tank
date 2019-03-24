package Attack;

import Environment.Missile;
import Tank.Tank;

public interface Fire {
    Missile fire(Tank tank);
}
