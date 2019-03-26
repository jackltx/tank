package MoveState;

import Environment.Missile;
import Environment.MissileInterface;
import Tank.Tank;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Stack;

public class Stop implements State {
    private Tank tank;
    private HashMap<State,Integer> pressed;
    private State preState;

    public Stop(Tank tank,State initState) {
        this.tank = tank;
        this.pressed = new HashMap<>();
        this.preState = initState;

    }

    public Stop(Tank tank, State initState, HashMap<State, Integer> pressed) {

        this.tank = tank;
        this.pressed = pressed;
        this.preState = initState;

    }

    public State getPreState() {
        return preState;
    }

    public void setPreState(State preState) {
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
    public void drawMissileSelf(Graphics g, MissileInterface missile) {
        preState.drawMissileSelf(g, missile);
    }

    @Override
    public void changeMissilePosition(MissileInterface missile) {
        preState.changeMissilePosition(missile);
    }

    public void addPreState(State preState) {
        this.pressed.put(preState, 1);
        this.preState = preState;
    }

    //松开按键之后 把相应hashmap标记为0 然后找出不为0的一个状态 返回 就是当前状态
    public State findCurState(int key,State up,State down,State left,State right) {
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            pressed.put(right, 0);
        } else if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            pressed.put(left, 0);
        } else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
            pressed.put(up, 0);
        } else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
            pressed.put(down, 0);
        }
        for (State s : pressed.keySet()) {
            if (pressed.get(s) != 0) {
                return s;
            }
        }
        return this;

    }

    public Tank getTank() {
        return tank;
    }

    public HashMap<State, Integer> getPressed() {
        return pressed;
    }
}
