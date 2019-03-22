
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
		// tk.getImage(OrdinaryWall.class.getResource("Images/Ω Ù«Ω.gif")),
		tk.getImage(OrdinaryWall.class.getResource("Images/∆’Õ®«Ω.gif")), };
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
