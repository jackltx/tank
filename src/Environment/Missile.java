package Environment;

import Client.Direction;
import Client.HitObserver;
import Client.TankClient;
import MoveState.State;
import Tank.Tank;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

public class Missile extends Observable implements MissileInterface{
	public int XSPEED = 10;
	public int YSPEED = 10;
	
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	
	private int x, y;
	private State curState;//表示坦克朝向的状态类
	
	private boolean good;
	private boolean live = true;
	
	public TankClient tc;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	public static Image[] missileImages = new Image[5];
	public static Map<String, Image> imgs = new HashMap<String, Image>();
	static {

		missileImages[0] = tk.getImage(Missile.class.getClassLoader().getResource("images/missileL.gif"));
		missileImages[1] = tk.getImage(Missile.class.getClassLoader().getResource("images/missileU.gif"));
		missileImages[2] = tk.getImage(Missile.class.getClassLoader().getResource("images/missileR.gif"));
		missileImages[3] = tk.getImage(Missile.class.getClassLoader().getResource("images/missileD.gif"));
		missileImages[4] = tk.getImage(Missile.class.getClassLoader().getResource("images/9.gif"));

		imgs.put("L", missileImages[0]);

		imgs.put("U", missileImages[1]);

		imgs.put("R", missileImages[2]);

		imgs.put("D", missileImages[3]);


	}
//    public static void changeMissile1(){
//        imgs.put("L", missileImages[4]);
//        imgs.put("U", missileImages[4]);
//        imgs.put("R", missileImages[4]);
//        imgs.put("D", missileImages[4]);
//    }
//    public static void changeMissile2(){
//        imgs.put("L", missileImages[0]);
//        imgs.put("U", missileImages[1]);
//        imgs.put("R", missileImages[2]);
//        imgs.put("D", missileImages[3]);
//    }
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}


	public Missile(int x, int y, State curState) {
		this.x = x;
		this.y = y;
		this.curState = curState;
	}
	
	public Missile(int x, int y, boolean good, State curState, TankClient tc) {
		this(x, y, curState);
		this.good = good;
		this.tc = tc;
	}
	
	public void draw(Graphics g) {
		if(!live) {
			tc.missiles.remove(this);
			return;
		}

		//子弹移动 只要调用State的画图方向就行
		curState.drawMissileSelf(g,this);
//		switch(dir) {
//		case L:
//			g.drawImage(imgs.get("L"), x, y, null);
//			break;
//
//		case U:
//			g.drawImage(imgs.get("U"), x, y, null);
//			break;
//
//		case R:
//			g.drawImage(imgs.get("R"), x, y, null);
//			break;
//
//		case D:
//			g.drawImage(imgs.get("D"), x, y, null);
//			break;
//
//		}

		move();
	}

	public void move() {
		

		//根据朝向 更新最新位置
		curState.changeMissilePosition(this);
//		switch(dir) {
//		case L:
//			x -= XSPEED;
//			break;
//
//		case U:
//			y -= YSPEED;
//			break;
//
//		case R:
//			x += XSPEED;
//			break;
//
//		case D:
//			y += YSPEED;
//			break;
//
//		case STOP:
//			break;
//		}
		
		if(x < 0 || y < 0 || x > TankClient.GAME_WIDTH || y > TankClient.GAME_HEIGHT) {
			live = false;
		}		
	}

	public boolean isLive() {
		return live;
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	public boolean hitTank(Tank t) {
		if(this.live && this.getRect().intersects(t.getRect()) && t.isLive() && this.good != t.isGood()) {
			Explode e = new Explode(t.getX(), t.getY(), tc);
			tc.explodes.add(e);
			if(t.isGood()) {
				//若使我方坦克则生命值-25，并触发敌军数量的增加
				t.setLife(t.getLife()-25);
				//通知观察者发生的变化
				HitObserver hitObserver=new HitObserver(this,tc);
				setChanged();
				notifyObservers();
				//如果生命低于0则死亡
				if(t.getLife() <= 0) t.setLive(false);
			} else {
				//若使敌方坦克则直接死亡
				t.setLive(false);
			}
			
			this.live = false;
			
			return true;
		}
		return false;
	}
	
	public boolean hitTanks(List<Tank> tanks) {
		for(int i=0; i<tanks.size(); i++) {
			if(hitTank(tanks.get(i))) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hitWall(OrdinaryWall w) {
		if(this.live && this.getRect().intersects(w.getRect())) {
			this.live = false;
			this.tc.walls.remove(w);
			this.tc.wallsHome.remove(w);
			return true;
		}
		return false;
	}
	public boolean hitWall(MetalWall w) {
		if(this.live && this.getRect().intersects(w.getRect())) {
			this.live = false;
			return true;
		}
		return false;
	}
	public boolean hitHome() {
		if(this.live && this.getRect().intersects(tc.home.getRect())) {
			this.live = false;
//			this.tc.home.setLive(false);
			this.tc.home.setLive(false);
			return true;
		}
		return false;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {

		this.y = y;
	}

	@Override
	public int getXSPEED() {
		return XSPEED;
	}

	@Override
	public int getYSPEED() {
		return YSPEED;
	}
	@Override
	public Map<String, Image> getImgs() {
		return this.imgs;
	}
}
