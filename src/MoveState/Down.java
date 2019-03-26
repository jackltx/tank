package MoveState;

import Environment.Missile;
import Environment.MissileInterface;
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
    public void drawMissileSelf(Graphics g, MissileInterface missile) {
        g.drawImage(missile.getImgs().get("D"), missile.getX(), missile.getY(), null);
    }

    @Override
    public void changeMissilePosition(MissileInterface missile) {
        missile.setY(missile.getY() + missile.getYSPEED());
    }
    public Tank getTank() {
        return tank;
    }

    public Image getTankImage() {
        return tankImage;
    }
}
