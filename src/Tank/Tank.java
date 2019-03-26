package Tank;

import Attack.Fire;
import Attack.MediumFire;
import Attack.NormalFire;
import Attack.QuickFire;
import Client.Direction;
import Client.TankClient;
import Environment.*;

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
	boolean hasRocket=false;

	private static Toolkit tk = Toolkit.getDefaultToolkit();

	static Random r = new Random();
	int step = r.nextInt(12) + 3;

	boolean bL = false, bU = false, bR = false, bD = false;
	Direction dir = STOP;
	public Direction ptDir = U;

	static Image[] tankImags = null;

	private static Image[] missileImages = null;

	static {
		tankImags = new Image[] {
				tk.getImage(Explode.class.getResource("../Images/tankD.gif")),
				tk.getImage(Explode.class.getResource("../Images/tankU.gif")),
				tk.getImage(Explode.class.getResource("../Images/tankL.gif")),
				tk.getImage(Explode.class.getResource("../Images/tankR.gif")),
				 };
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
	}

	public Tank(int x, int y, Direction dir, TankClient tc) {
		this(x, y);
		this.dir = dir;
		this.tc = tc;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void draw(Graphics g) {
	}

	void move() {

		this.oldX = x;
		this.oldY = y;

		switch (dir) {
		case L:
			x -= XSPEED;
			break;
		case U:
			y -= YSPEED;
			break;
		case R:
			x += XSPEED;
			break;
		case D:
			y += YSPEED;
			break;
		case STOP:
			break;
		}

		if (this.dir != STOP) {
			this.ptDir = this.dir;
		}

		if (x < 0)
			x = 0;
		if (y < 30)
			y = 30;
		if (x + Tank.WIDTH > TankClient.GAME_WIDTH)
			x = TankClient.GAME_WIDTH - Tank.WIDTH;
		if (y + Tank.HEIGHT > TankClient.GAME_HEIGHT)
			y = TankClient.GAME_HEIGHT - Tank.HEIGHT;
		doSth();
	}
	void doSth(){
		return;
	}

	void stay() {
		x = oldX;
		y = oldY;
	}

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
			tc.dispose();
			tc=new TankClient(50);
			tc.lauchFrame();
			break;
		case KeyEvent.VK_RIGHT:
			bR = true;
			break;
		case KeyEvent.VK_D:
			bR = true;
			break;
		case KeyEvent.VK_LEFT:
			bL = true;
			break;
		case KeyEvent.VK_A:
			bL = true;
			break;
		case KeyEvent.VK_UP:
			bU = true;
			break;
		case KeyEvent.VK_W:
			bU = true;
			break;
		case KeyEvent.VK_DOWN:
			bD = true;
			break;
		case KeyEvent.VK_S:
			bD = true;
			break;
		}
		locateDirection();
	}

	void locateDirection() {
		if (bL && !bU && !bR && !bD)
			dir = L;

		else if (!bL && bU && !bR && !bD)
			dir = U;

		else if (!bL && !bU && bR && !bD)
			dir = R;

		else if (!bL && !bU && !bR && bD)
			dir = D;

		else if (!bL && !bU && !bR && !bD)
			dir = STOP;
	}

	public void keyReleased(KeyEvent e) {
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
			case KeyEvent.VK_RIGHT:
				bR = false;
				break;
			case KeyEvent.VK_D:
				bR = false;
				break;
			case KeyEvent.VK_LEFT:
				bL = false;
				break;
			case KeyEvent.VK_A:
				bL = false;
				break;
			case KeyEvent.VK_UP:
				bU = false;
				break;
			case KeyEvent.VK_W:
				bU = false;
				break;
			case KeyEvent.VK_DOWN:
				bD = false;
				break;
			case KeyEvent.VK_S:
				bD = false;
				break;
			case KeyEvent.VK_K:
				superFire();
				break;
		}
		locateDirection();
	}

	public Missile fire() {
		if (!live)
			return null;
		int x = this.x + Tank.WIDTH / 2 - Missile.WIDTH / 2;
		int y = this.y + Tank.HEIGHT / 2 - Missile.HEIGHT / 2;
		Missile m=null;
		if (this.good&&hasRocket){
			Missile.changeMissile1();
			 m= new Missile(x, y + 2, good, ptDir, this.tc);
			tc.missiles.add(m);
		}else {
			Missile.changeMissile2();
			m = new Missile(x, y + 2, good, ptDir, this.tc);
			tc.missiles.add(m);
		}
		return m;
	}

	public Missile fire(Direction dir) {
		if (!live)
			return null;
		int x = this.x + Tank.WIDTH / 2 - Missile.WIDTH / 2;
		int y = this.y + Tank.HEIGHT / 2 - Missile.HEIGHT / 2;
		Missile m = new Missile(x, y, good, dir, this.tc);
		tc.missiles.add(m);
		return m;
	}

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

	public boolean collidesWithWall(OrdinaryWall w) {
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

	private void superFire() {
		Direction[] dirs = values();
		for (int i = 0; i < 4; i++) {
			fire(dirs[i]);
		}
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public class BloodBar {
		public void draw(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.RED);
			g.drawRect(x, y - 15, WIDTH, 10);
			int w = WIDTH * life / 100;
			g.fillRect(x, y - 15, w, 10);
			g.setColor(c);
		}
	}

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
			this.XSPEED=10;
			this.YSPEED=10;
			lightning.setLive(false);
			return true;
		}
		return false;
	}

	public boolean eatSuperBloodBox(SuperBloodBox superBloodBox) {
		if (this.live && superBloodBox.isLive() && this.getRect().intersects(superBloodBox.getRect())) {
			this.life+=100;
			superBloodBox.setLive(false);
			return true;
		}
		return false;
	}

	public boolean eatRocket(RocketBag rocketBag) {
		if (this.live && rocketBag.isLive() && this.getRect().intersects(rocketBag.getRect())) {
			hasRocket=true;
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