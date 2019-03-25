package Environment;

import Tank.Tank;
import java.awt.*;
import java.util.*;
import java.util.List;

public interface MissileInterface {
    void draw(Graphics g);
    void move();
    boolean isLive();
    Rectangle getRect();
    boolean hitTank(Tank t);
    boolean hitTanks(List<Tank> tanks);
    public boolean hitWall(OrdinaryWall w);
    public boolean hitWall(MetalWall w);
    public boolean hitHome();
}
