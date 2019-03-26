package Environment;

import Client.TankClient;

import java.awt.*;

public class OrdinaryWall {
	public static final int w = 22;
	public static final int h = 21;
	int x, y;

	TankClient tc;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] wallImags = null;
	static {
		wallImags = new Image[] {
//		 tk.getImage(Environment.OrdinaryWall.class.getResource("Images/金属墙.gif")),
		tk.getImage(OrdinaryWall.class.getResource("../Images/普通墙.gif")),
		};
	}

	public OrdinaryWall(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}

	public void draw(Graphics g) {
		g.drawImage(wallImags[0], x, y, null);
	}

	public Rectangle getRect() {
		return new Rectangle(x, y, w, h);
	}
}
