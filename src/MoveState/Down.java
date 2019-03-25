package MoveState;

import Environment.Missile;
import Tank.Tank;

import java.awt.*;

public class Down implements State {
    private Tank tank;
    private Image tankImage;

    public Down(Tank tank, Image image) {
        this.tankImage = image;
        this.tank = tank;
    }
    @Override
    public void changePosition() {
        tank.y = tank.y + tank.YSPEED;
    }

    @Override
    public void drawSelf(Graphics g) {
        g.drawImage(tankImage, tank.x, tank.y, null);
    }

    @Override
    public void drawMissileSelf(Graphics g,Missile missile) {
        g.drawImage(missile.getImgs().get("D"), missile.getX(), missile.getY(), null);
    }

    @Override
    public void changeMissilePosition(Missile missile) {
        missile.setY(missile.getY() + missile.YSPEED);
    }
}
