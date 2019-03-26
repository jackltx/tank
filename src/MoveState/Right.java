package MoveState;

import Environment.Missile;
import Environment.MissileInterface;
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
    public void drawMissileSelf(Graphics g, MissileInterface missile) {
        g.drawImage(missile.getImgs().get("R"), missile.getX(), missile.getY(), null);
    }

    @Override
    public void changeMissilePosition(MissileInterface missile) {
        missile.setX(missile.getX() + missile.getXSPEED());
    }
    public Tank getTank() {
        return tank;
    }

    public Image getTankImage() {
        return tankImage;
    }
}
