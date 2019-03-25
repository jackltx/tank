package MoveState;

import Environment.Missile;
import Tank.Tank;

import java.awt.*;

public class Stop implements State {
    private Tank tank;
    private State preState;//ֹͣǰ��״̬����

    public void setPreState(State preState) {
        this.preState = preState;
    }

    public Stop(Tank tank, State preState) {
        this.tank = tank;
        this.preState = preState;
    }



    @Override
    public void changePosition() {
        //��Ϊ��ֹͣ״̬ ���Ե�ˢ��֡ʱ����
    }

    @Override
    public void drawSelf(Graphics g) {
        //��Ȼ��ֹͣ״̬ ����ÿһ֡����Ҫ���� �ͻ���һ��״̬��ͼ
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
