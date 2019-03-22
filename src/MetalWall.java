import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class MetalWall {
	public static final int w = 36;
	public static final int h = 37;
	private int x, y;
	private TankClient tc;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] wallImags = null;
	static {
		wallImags = new Image[] { tk.getImage(OrdinaryWall.class
				.getResource("Images/����ǽ.gif")), };
	}

	public MetalWall(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}

	public void draw(Graphics g) {
		/*
		 * Color c = g.getColor(); g.setColor(Color.yellow); g.fillRect(x, y, w,
		 * h); g.setColor(c);
		 */
		g.drawImage(wallImags[0], x, y, null);
	}

	public Rectangle getRect() {
		return new Rectangle(x, y, w, h);
	}
}