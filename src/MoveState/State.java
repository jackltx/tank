package MoveState;

import Environment.Missile;

import java.awt.*;

//运动的朝向状态 上下左右停止
public interface State {
    public void changePosition();//根据当前方向 更新最新的xy坐标
    public void drawSelf(Graphics g);//根据当前x y 坐标在Graphics里画出自己坦克
    public void drawMissileSelf(Graphics g, Missile missile);//根据当前方向状态 发射子弹

    public void changeMissilePosition(Missile missile);//根据当前方向 更新子弹最新的xy坐标
}
