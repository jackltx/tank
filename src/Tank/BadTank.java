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

    public BadTank(int x, int y, TankClient tc) {
        this(x, y);
        this.tc = tc;
    }

    public void draw(Graphics g) {
        if (!live) {
            tc.tanks.remove(this);
            return;
        }
        this.curState.drawSelf(g);
//        switch (ptDir) {
//            case L:
//                g.drawImage(tankImags[2], x, y, null);
//                break;
//
//            case U:
//                g.drawImage(tankImags[1], x, y, null);
//                break;
//
//            case R:
//                g.drawImage(tankImags[3], x, y, null);
//                break;
//
//            case D:
//                g.drawImage(tankImags[0], x, y, null);
//                break;
//
//        }
        move();
        //AI坦克随机移动\开火
        randomAction();
    }

    void randomAction(){
        if (step == 0) {
            step = r.nextInt(12) + 3;
            int rn = r.nextInt(4);//随机四个方向
            switch (rn) {
                case 0:
                    curState = up;
                    break;
                case 1:
                    curState = down;
                    break;
                case 2:
                    curState = left;
                    break;
                case 3:
                    curState = right;
                    break;
            }
        }
        step--;
        if (r.nextInt(40) > 38)
            this.fire();
    }
}
