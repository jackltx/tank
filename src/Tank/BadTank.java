package Tank;

import Client.Direction;
import Client.TankClient;

import java.awt.*;

import static Client.Direction.*;

public class BadTank extends Tank {
    public BadTank(int x, int y) {
        super(x, y);
        good=false;
    }

    public BadTank(int x, int y, Direction dir, TankClient tc) {
        this(x, y);
        this.dir = dir;
        this.tc = tc;
    }

    public void draw(Graphics g) {
        if (!live) {
            tc.tanks.remove(this);
            return;
        }
        switch (ptDir) {
            case L:
                g.drawImage(tankImags[2], x, y, null);
                break;

            case U:
                g.drawImage(tankImags[1], x, y, null);
                break;

            case R:
                g.drawImage(tankImags[3], x, y, null);
                break;

            case D:
                g.drawImage(tankImags[0], x, y, null);
                break;

        }
        move();
    }

    void doSth(){
        Direction[] dirs = values();
        if (step == 0) {
            step = r.nextInt(12) + 3;
            int rn = r.nextInt(dirs.length);
            dir = dirs[rn];
        }
        step--;
        if (r.nextInt(40) > 38)
            this.fire();
    }
}
