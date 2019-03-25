package Attack;

import Environment.Missile;
import Environment.MissileInterface;
import Tank.Tank;

public interface Fire {
    MissileInterface fire(Tank tank);
}
