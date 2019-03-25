package MoveState;

import Environment.Missile;
import Tank.Tank;

import java.awt.*;

public class Stop implements State {
    private Tank tank;
    private State preState;//停止前的状态朝向

    public void setPreState(State preState) {
        this.preState = preState;
    }

    public Stop(Tank tank, State preState) {
        this.tank = tank;
        this.preState = preState;
    }



    @Override
    public void changePosition() {
        //因为是停止状态 所以当刷新帧时不动
    }

    @Override
    public void drawSelf(Graphics g) {
        //虽然是停止状态 但是每一帧还是要画的 就画上一个状态的图
        preState.drawSelf(g);
    }

    @Override
    public void drawMissileSelf(Graphics g, Missile missile) {
        preState.drawMissileSelf(g, missile);
    }

    @Override
    public void changeMissilePosition(Missile missile) {
        preState.changeMissilePosition(missile);
    }
}
