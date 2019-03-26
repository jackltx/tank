package Tank;

import Attack.Fire;
import Attack.MediumFire;
import Attack.NormalFire;
import Attack.QuickFire;
import Client.Direction;
import Client.TankClient;
import Environment.*;
import MoveState.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

import static Client.Direction.*;
import static Client.Direction.D;

public abstract class Tank {
	public int XSPEED = 5, YSPEED = 5;
	public static int WIDTH = 34, HEIGHT = 35;
	public TankClient tc;
	public boolean good;
	public int x, y;
	public int oldX, oldY;
	public boolean live = true;
	int life = 100;
	static boolean hasRocket=false;
	static Random r = new Random();
	int step = r.nextInt(12) + 3;

	//按下去为true 位置会一直改变 弹起来为false 位置就不能变了
//	boolean bL = false, bU = false, bR = false, bD = false;
//	public Direction ptDir = U;
	//组合五种朝向状态：向上下左右和停止
	protected Up up;
	protected Down down;
	protected Left left;
	protected Right right;
	protected Stop stop;
	protected State curState;


	static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] missileImages = null;
	static {

		missileImages = new Image[] {
				tk.getImage(Missile.class.getClassLoader().getResource("images/missileL.gif")),

				tk.getImage(Missile.class.getClassLoader().getResource("images/missileU.gif")),

				tk.getImage(Missile.class.getClassLoader().getResource("images/missileR.gif")),

				tk.getImage(Missile.class.getClassLoader().getResource("images/missileD.gif")),

				tk.getImage(Missile.class.getClassLoader().getResource("images/9.gif")),

		};
	}

	public Tank(int x, int y) {
		this.x = x;
		this.y = y;
		this.oldX = x;
		this.oldY = y;
		//初始化状态朝上
		this.up = new Up(this, tk.getImage(Explode.class.getResource("../Images/tankU.gif")));
		this.down = new Down(this,tk.getImage(Explode.class.getResource("../Images/tankD.gif")));
		this.left = new Left(this, tk.getImage(Explode.class.getResource("../Images/tankL.gif")));
		this.right = new Right(this, tk.getImage(Explode.class.getResource("../Images/tankR.gif")));
		this.stop = new Stop(this, this.up);
		this.curState = this.stop;
	}

	public Tank(int x, int y, TankClient tc) {
		this(x, y);
		this.tc = tc;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public abstract void draw(Graphics g);

	//通过算出设置移动后的xy坐标，然后重画坦克来表示移动
	//todo 状态模式
	void move() {

		this.oldX = x;
		this.oldY = y;
		//用一个context handle 概括所有的判断
		//如果是处于（某朝向）状态 则往该状态下的方向计算新的距离
		//curState.moveTo()
		curState.changePosition();
//		switch (dir) {
//		case L:
//			x -= XSPEED;
//			break;
//		case U:
//			y -= YSPEED;
//			break;
//		case R:
//			x += XSPEED;
//			break;
//		case D:
//			y += YSPEED;
//			break;
//		case STOP:
//			break;
//		}

//		if (this.dir != STOP) {
//			this.ptDir = this.dir;
//		}

		if (x < 0)
			x = 0;
		if (y < 30)
			y = 30;
		if (x + Tank.WIDTH > TankClient.GAME_WIDTH)
			x = TankClient.GAME_WIDTH - Tank.WIDTH;
		if (y + Tank.HEIGHT > TankClient.GAME_HEIGHT)
			y = TankClient.GAME_HEIGHT - Tank.HEIGHT;
	}


	void stay() {
		x = oldX;
		y = oldY;
	}

	//按键事件，按下对应的方向表示可以往哪个方向运动
	//todo 用状态模式改进 当按了什么键 context对象里的当前状态就改成什么状态
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
			case KeyEvent.VK_1:
				tc.dispose();
				tc=new TankClient(50);
				tc.lauchFrame();
				break;
			case KeyEvent.VK_2:
				tc.dispose();
				tc=new TankClient(40);
				tc.lauchFrame();
				break;
			case KeyEvent.VK_3:
				tc.dispose();
				tc=new TankClient(30);
				tc.lauchFrame();
				break;
		case KeyEvent.VK_F2:
			//重建一个画布和一个线程 来运行一个新游戏
			//todo 要能删除原来的游戏
			//因为home是单例的 上一局里死了之后 按f2 home还是被标记为死了 所以不能重画
			Home home = Home.getInstance();
			home.setLive(true);//要重开一局游戏 就要让home活
			new TankClient(50).lauchFrame();
			tc.dispose();

			break;
		case KeyEvent.VK_RIGHT:
			curState = this.right;
			stop.addPreState(curState);
			break;
		case KeyEvent.VK_D:
			curState = this.right;
			stop.addPreState(curState);
			break;
		case KeyEvent.VK_LEFT:
			curState = this.left;
			stop.addPreState(curState);
			break;
		case KeyEvent.VK_A:
			curState = this.left;
			stop.addPreState(curState);
			break;
		case KeyEvent.VK_UP:
			curState = this.up;
			stop.addPreState(curState);
			break;
		case KeyEvent.VK_W:
			curState = this.up;
			stop.addPreState(curState);
			break;
		case KeyEvent.VK_DOWN:
			curState = this.down;
			stop.addPreState(curState);
			break;
		case KeyEvent.VK_S:
			curState = this.down;
			stop.addPreState(curState);
			break;
		}
		//保存上一步的操作 为了保证多键同时按下 松开后的方向正确
		//stop.addPreState(curState);
		//最后确定坦克朝向
//		locateDirection();
	}

//	void locateDirection() {
//		if (bL && !bU && !bR && !bD)
//			dir = L;
//
//		else if (!bL && bU && !bR && !bD)
//			dir = U;
//
//		else if (!bL && !bU && bR && !bD)
//			dir = R;
//
//		else if (!bL && !bU && !bR && bD)
//			dir = D;
//
//		else if (!bL && !bU && !bR && !bD)
//			dir = STOP;
//	}

    //松开键 会将方向布尔值变为false
	public void keyReleased(KeyEvent e) {
		//Fire接口表示一种开火方式 普通 中间 快速
		Fire fire;
		int key = e.getKeyCode();
		switch (key) {
			case KeyEvent.VK_M:
				fire=new MediumFire();
				fire.fire(this);
				break;
			case KeyEvent.VK_H:
				fire=new QuickFire();
				fire.fire(this);
				break;
			case KeyEvent.VK_J:
				fire=new NormalFire(hasRocket);
				fire.fire(this);
				break;
			case KeyEvent.VK_K:
				superFire();
				break;
		}
		curState = stop.findCurState(key, up, down, left, right);
		//locateDirection();
	}

	public MissileInterface fire() {
		if (!live)
			return null;
		int x = this.x + Tank.WIDTH / 2 - Missile.WIDTH / 2;
		int y = this.y + Tank.HEIGHT / 2 - Missile.HEIGHT / 2;
//<<<<<<< HEAD
//		//发射的子弹
//		Missile m = new Missile(x, y + 2, good, curState, this.tc);
//		tc.missiles.add(m);
//=======
		MissileInterface m=null;
		m = new Missile(x, y + 2, good, curState, this.tc);
		tc.missiles.add(m);
		return m;
	}

	public Missile fire(State curState) {
		if (!live)
			return null;
		int x = this.x + Tank.WIDTH / 2 - Missile.WIDTH / 2;
		int y = this.y + Tank.HEIGHT / 2 - Missile.HEIGHT / 2;
		Missile m = new Missile(x, y, good, curState, this.tc);
		tc.missiles.add(m);
		return m;
	}

	//碰撞体积
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public boolean isGood() {
		return good;
	}

	public State getCurState() {
		return curState;
	}

	//碰撞到墙的事件
	public boolean collidesWithWall(OrdinaryWall w) {
		//如果坦克的碰撞体积和墙重叠
		if (this.live && this.getRect().intersects(w.getRect())) {
			this.stay();
			return true;
		}
		return false;
	}

	public boolean collidesWithWall(MetalWall w) {
		if (this.live && this.getRect().intersects(w.getRect())) {
			this.stay();
			return true;
		}
		return false;
	}

	public boolean collidesRiver(River r) {
		if (this.live && this.getRect().intersects(r.getRect())) {
			this.stay();
			return true;
		}
		return false;
	}

	public boolean collidesHome(Home h) {
		if (this.live && this.getRect().intersects(h.getRect())) {
			this.stay();
			return true;
		}
		return false;
	}

	public boolean collidesWithTanks(java.util.List<Tank> tanks) {
		for (int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i);
			if (this != t) {
				if (this.live && t.isLive()
						&& this.getRect().intersects(t.getRect())) {
					this.stay();
					t.stay();
					return true;
				}
			}
		}
		return false;
	}

	//todo 吃装备才能用高级弹药 这是一个技能 用策略
	private void superFire() {
//		for (int i = 0; i < 4; i++) {
//			fire(dirs[i]);
//		}
		//要四个方向同时开火 可以调用多个fire方法 传入多个方向state
		fire(up);
		fire(down);
		fire(left);
		fire(right);
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	//血条类
	public class BloodBar {
		//画血条
		public void draw(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.RED);
			g.drawRect(x, y - 15, WIDTH, 10);
			int w = WIDTH * life / 100;
			g.fillRect(x, y - 15, w, 10);
			g.setColor(c);
		}
	}

	//吃血包的方法
	public boolean eat(BloodBox b) {
		if (this.live && b.isLive() && this.getRect().intersects(b.getRect())) {
			if (this.life<100){
				this.life += 25;
			}
			b.setLive(false);
			return true;
		}
		return false;
	}

	public boolean eatLightning(Lightning lightning) {
		if (this.live && lightning.isLive() && this.getRect().intersects(lightning.getRect())) {
			this.XSPEED=8;
			this.YSPEED=8;
			lightning.setLive(false);
			return true;
		}
		return false;
	}

	public boolean eatSuperBloodBox(SuperBloodBox superBloodBox) {
		if (this.live && superBloodBox.isLive() && this.getRect().intersects(superBloodBox.getRect())) {
			if (this.life<200) {
				this.life += 50;
			}
			superBloodBox.setLive(false);
			return true;
		}
		return false;
	}

	public boolean eatRocket(RocketBag rocketBag) {
		if (this.live && rocketBag.isLive() && this.getRect().intersects(rocketBag.getRect())) {
//			System.out.println("rrrrrrrrrrr");
			hasRocket=true;
			System.out.println(hasRocket);
			rocketBag.setLive(false);
			return true;
		}
		return false;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}