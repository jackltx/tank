package Tank;

import Client.Direction;
import Client.TankClient;

import java.awt.*;

public class GoodTank extends Tank {
    public GoodTank(int x, int y) {
        super(x, y);
        good=true;
    }

    public GoodTank(int x, int y, Direction dir, TankClient tc) {
        this(x, y);
        this.dir = dir;
        this.tc = tc;
    }

    public void draw(Graphics g) {
        if (!live) {
            return;
        }
        new BloodBar().draw(g);
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

}
