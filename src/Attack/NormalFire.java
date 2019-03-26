package Attack;

import Environment.*;
import Tank.Tank;

public class NormalFire implements Fire {
    boolean hasRocket;
    public NormalFire(boolean hasRocket){
        this.hasRocket=hasRocket;
    }
    @Override
    public MissileInterface fire(Tank tank) {
        if (!tank.live)
            return null;
        int x = tank.x + Tank.WIDTH / 2 - Missile.WIDTH / 2;
        int y = tank.y + Tank.HEIGHT / 2 - Missile.HEIGHT / 2;
        MissileInterface m=null;
        if (hasRocket){
            m = new RocketMissile(x, y + 2, tank.good, tank.ptDir, tank.tc);
        }else {
            m = new Missile(x, y + 2, tank.good, tank.ptDir, tank.tc);
        }
        tank.tc.missiles.add(m);
        return m;
    }
}
