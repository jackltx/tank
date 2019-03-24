package Attack;

import Environment.Missile;
import Tank.Tank;

public class QuickFire implements Fire {
    @Override
    public Missile fire(Tank tank) {
        if (!tank.live)
            return null;
        int x = tank.x + Tank.WIDTH / 2 - Missile.WIDTH / 2;
        int y = tank.y + Tank.HEIGHT / 2 - Missile.HEIGHT / 2;
        Missile m = new Missile(x, y + 2, tank.good, tank.ptDir, tank.tc);
        m.XSPEED=20;
        m.YSPEED=20;
        tank.tc.missiles.add(m);
        return m;
    }
}
