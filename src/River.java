import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;


public class River {
	public static final int riverW = 55;
	public static final int riverH = 155;
	private int x, y;
	TankClient tc ;
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] riverImags = null;
	static {
		riverImags = new Image[]{
				tk.getImage(OrdinaryWall.class.getResource("Images/river.jpg")),
		};
	}
	
	
	public River(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}
	
	public void draw(Graphics g) {
		g.drawImage(riverImags[0],x, y, null);
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, riverW, riverH);
	}
}
