import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;


public class Tree {
	public static final int WIDTH = 36;
	public static final int HEIGHT = 36;
	int x, y;
	TankClient tc ;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] treeImags = null;
	static {
		treeImags = new Image[]{
				tk.getImage(OrdinaryWall.class.getResource("Images/tree.gif")),
		};
	}
	
	
	public Tree(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}
	
	public void draw(Graphics g) {
		g.drawImage(treeImags[0],x, y, null);
	}
	
}
