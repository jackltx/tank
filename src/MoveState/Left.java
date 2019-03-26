package MoveState;

import Environment.Missile;
import Environment.MissileInterface;
import Tank.Tank;

import java.awt.*;

public class Left implements State {
    private Tank tank;
    private Image tankImage;

    public Left(Tank tank, Image image) {
        this.tankImage = image;
        this.tank = tank;
    }
    @Override
    public void changePosition() {
        tank.x = tank.x - tank.XSPEED;
    }

    @Override
    public void drawSelf(Graphics g) {
        g.drawImage(tankImage, tank.x, tank.y, null);
    }
    @Override
    public void drawMissileSelf(Graphics g, MissileInterface missile) {
        g.drawImage(missile.getImgs().get("L"), missile.getX(), missile.getY(), null);
    }

    @Override
    public void changeMissilePosition(MissileInterface missile) {
        missile.setX(missile.getX() - missile.getXSPEED());
    }

    public Tank getTank() {
        return tank;
    }

    public Image getTankImage() {
        return tankImage;
    }
}
