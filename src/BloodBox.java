import java.awt.*;
import java.util.Random;

public class BloodBox {
	
	public static final int w = 20;
	public static final int h = 20;

	private int x, y;
	TankClient tc;
	private static Random r = new Random();

	int step = 0;
	private boolean live = false;

	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] bloodImags = null;
	static {
		bloodImags = new Image[] { tk.getImage(OrdinaryWall.class
				.getResource("Images/blood.jpg")), };
	}

	private int[][] pos = { { 100, 120 }, { 200, 300 }, { 760, 570 },
			{ 280, 570 }, { 600, 40 }, { 700, 60 }, { 680, 225 } };

	public BloodBox() {
		x = pos[0][0];
		y = pos[0][1];
	}

	public void draw(Graphics g) {
		if (r.nextInt(100) > 98) {
			this.live = true;
			move();
		}
		if (!live)
			return;
		g.drawImage(bloodImags[0], x, y, null);

	}

	private void move() {
		step++;
		if (step == pos.length) {
			step = 0;
		}
		x = pos[step][0];
		y = pos[step][1];
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
