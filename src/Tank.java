
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Tank {
	public static final int XSPEED = 5, YSPEED = 5;
	public static final int WIDTH = 34, HEIGHT = 35;

	TankClient tc;
	
	private boolean good;
	private int x, y;
	private int oldX, oldY;
	private boolean live = true;
	private int life = 100;
	
	private static Random r = new Random();
	private int step = r.nextInt(12) + 3;

	private boolean bL = false, bU = false, bR = false, bD = false;
	private Direction dir = Direction.STOP;
	private Direction ptDir = Direction.U;

	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] tankImags = null;
	static {
		tankImags = new Image[] {
				tk.getImage(Explode.class.getResource("Images/tankD.gif")),
				tk.getImage(Explode.class.getResource("Images/tankU.gif")),
				tk.getImage(Explode.class.getResource("Images/tankL.gif")),
				tk.getImage(Explode.class.getResource("Images/tankR.gif")),
				 };

	}

	public Tank(int x, int y, boolean good) {
		this.x = x;
		this.y = y;
		this.oldX = x;
		this.oldY = y;
		this.good = good;
	}

	public Tank(int x, int y, boolean good, Direction dir, TankClient tc) {
		this(x, y, good);
		this.dir = dir;
		this.tc = tc;
	}

	public void draw(Graphics g) {
		if (!live) {
			if (!good) {
				tc.tanks.remove(this);
			}
			return;
		}

		if (good)
			new BloodBar().draw(g);

		switch (ptDir) {
		case L:
			g.drawImage(tankImags[2], x, y, null);
			break;
		
		case U:
			g.drawImage(tankImags[1], x, y, null);
			break;
		
		case R:
			g.drawImage(tankImags[3], x, y, null);
			break;
		
		case D:
			g.drawImage(tankImags[0], x, y, null);
			break;
		
		}

		move();
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

		if (this.dir != Direction.STOP) {
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

		if (!good) {
			Direction[] dirs = Direction.values();
			if (step == 0) {
				step = r.nextInt(12) + 3;
				int rn = r.nextInt(dirs.length);
				dir = dirs[rn];
			}
			step--;

			if (r.nextInt(40) > 38)
				this.fire();
		}
	}

	private void stay() {
		x = oldX;
		y = oldY;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_F2:
			tc.tanks.clear();
			tc.missiles.clear();
			tc.trees.clear();
			tc.walls.clear();
			tc.wallsHome.clear();
			tc.metalWalls.clear();
			tc.myTank.setLive(false);
			if (tc.tanks.size() == 0) {
				for (int i = 0; i < 9; i++) {
					if(i<3)
					tc.tanks.add(new Tank(250 + 60 * i, 50, false, Direction.D,
									tc));
					else if(i<6)
						tc.tanks.add(new Tank(750, 150+ 60 * (i-3), false, Direction.D,
								tc));
					else
						tc.tanks.add(new Tank(1, 150+ 60 * (i-6), false, Direction.D,
								tc));
				}
			}
			tc.myTank = new Tank(380, 480, true, Direction.STOP, tc);
			if (!tc.home.isLive())
				tc.home.setLive(true);
			tc.createFrame();
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
			dir = Direction.L;

		else if (!bL && bU && !bR && !bD)
			dir = Direction.U;

		else if (!bL && !bU && bR && !bD)
			dir = Direction.R;

		else if (!bL && !bU && !bR && bD)
			dir = Direction.D;

		else if (!bL && !bU && !bR && !bD)
			dir = Direction.STOP;
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_J:
			fire();
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
		Missile m = new Missile(x, y + 2, good, ptDir, this.tc);
		tc.missiles.add(m);
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
		Direction[] dirs = Direction.values();
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

	private class BloodBar {
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
			this.life = 100;
			b.setLive(false);
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