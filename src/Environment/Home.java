package Environment;

import Client.TankClient;

import java.awt.*;

public class Home {
	private int x, y;
	private TankClient tc;
	public static final int w = 43, h = 43;
	private boolean live = true;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] homeImags = null;
	static {
		homeImags = new Image[] { tk.getImage(OrdinaryWall.class
				.getResource("../Images/nju.jpg")), };
	}

	private Home() {
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public TankClient getTc() {
		return tc;
	}

	public void setTc(TankClient tc) {
		this.tc = tc;
	}

	private static Home home = null;

	public static Home getInstance() {
		if (home==null) {
			home = new Home();
			home.setX(373);
			home.setY(545);
		}
		return home;
	}

	public void draw(Graphics g) {
		if (live) {
			g.drawImage(homeImags[0], x, y, null);
			for (int i = 0; i < tc.wallsHome.size(); i++) {
				OrdinaryWall w = tc.wallsHome.get(i);
				w.draw(g);
			}
		} else {
			gameOver();
			Color c = g.getColor();
			g.setColor(Color.red);
			Font f = g.getFont();
			g.setFont(new Font("aaa", Font.BOLD, 40));
			g.drawString("GAME OVER! ", 270, 300);
			g.setFont(f);
			g.setColor(c);
		}
	}

	public void gameOver() {
		while (tc.tanks.size() != 0 || tc.missiles.size() != 0) {
			tc.tanks.clear();
			tc.missiles.clear();
			tc.myTank.setLive(false);
		}
	}
	public  void createHomeWall(){
		for (int i = 0; i < 11; i++) {
			if (i < 4)
				tc.wallsHome.add(new OrdinaryWall(350, 580 - 21 * i, tc));
			else if (i < 7)
				tc.wallsHome.add(new OrdinaryWall(372 + 22 * (i - 4), 517, tc));
			else
				tc.wallsHome.add(new OrdinaryWall(416, 538 + (i - 7) * 21, tc));

		}
	}

	public Rectangle getRect() {
		return new Rectangle(x, y, w, h);
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

}
