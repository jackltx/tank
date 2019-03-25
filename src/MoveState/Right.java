package MoveState;

import Environment.Missile;
import Tank.Tank;

import java.awt.*;

public class Right implements State {
    private Tank tank;
    private Image tankImage;
    public Right(Tank tank,Image image) {
        this.tank = tank;
        this.tankImage = image;
    }
    @Override
    public void changePosition() {
        tank.x = tank.x + tank.XSPEED;
    }

    @Override
    public void drawSelf(Graphics g) {
        g.drawImage(tankImage, tank.x, tank.y, null);
    }
    @Override
    public void drawMissileSelf(Graphics g, Missile missile) {
        g.drawImage(missile.getImgs().get("R"), missile.getX(), missile.getY(), null);
    }

    @Override
    public void changeMissilePosition(Missile missile) {
        missile.setX(missile.getX() + missile.XSPEED);
    }
}
