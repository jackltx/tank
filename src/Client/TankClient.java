package Client;

import Environment.*;
import Factory.BadTankFactory;
import Factory.GoodTankFactory;
import Factory.TankFactory;
import Tank.Tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class TankClient extends Frame {

	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	TankFactory tankFactory=new GoodTankFactory(this);
	public Tank myTank = tankFactory.createTank();

	BloodBox b = new BloodBox();
	SuperBloodBox superBloodBox=new SuperBloodBox();
	Lightning lightning=new Lightning();
	RocketBag rocketBag =new RocketBag();
//	Environment.Home home = new Environment.Home(373, 545, this);
	public Home home=Home.getInstance();

	public List<River> rivers = new ArrayList<River>();
	public List<OrdinaryWall> wallsHome = new ArrayList<OrdinaryWall>();
	public List<OrdinaryWall> walls = new ArrayList<OrdinaryWall>();
	public List<MetalWall> metalWalls = new ArrayList<MetalWall>();
	public List<Explode> explodes = new ArrayList<Explode>();
	public List<MissileInterface> missiles = new ArrayList<MissileInterface>();
	public List<Tank> tanks = new ArrayList<Tank>();
	public List<Tree> trees = new ArrayList<Tree>();
	public Image screenImage = null;
	private int sleep;

	public TankClient(int sleep){
		this.sleep=sleep;
	}

	public void paint(Graphics g) {

		Color c = g.getColor();
		g.setColor(Color.red);
		g.drawString("Restart the game: F2", 10, 50);
		g.drawString("Fire:       J", 10, 70);
		g.drawString("Super Fire: K", 10, 90);
		g.drawString("The key control direction: A W D S", 10, 110);

		Font f1 = g.getFont();
		g.setFont(new Font("aaa", Font.BOLD, 25));
		g.drawString("The number of enemy tanks: ", 210, 70);
		g.setFont(new Font("aaa", Font.BOLD, 40));
		g.drawString("" + tanks.size(), 550, 70);
		g.setFont(f1);

		if (tanks.size() == 0 && home.isLive()&&myTank.isLive()) {
			Font f = g.getFont();
			g.setFont(new Font("aaa", Font.BOLD, 40));
			g.drawString("YOU WIN! ", 310, 300);
			g.setFont(f);
		}
		if (myTank.isLive() == false) {
			Font f = g.getFont();
			g.setFont(new Font("aaa", Font.BOLD, 40));
			tanks.clear();
			missiles.clear();
			g.drawString("GAME OVER! ", 270, 300);
			g.setFont(f);
		}
		g.setColor(c);

		for (int i = 0; i < rivers.size(); i++) {
			River r = rivers.get(i);
			r.draw(g);
		}
		for (int i = 0; i < rivers.size(); i++) {
			River r = rivers.get(i);
			myTank.collidesRiver(r);
			r.draw(g);
		}

		home.draw(g);
		myTank.draw(g);
		myTank.eat(b);
		myTank.eatLightning(lightning);
		myTank.eatSuperBloodBox(superBloodBox);
		myTank.eatRocket(rocketBag);
		for (int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i);
			for (int j = 0; j < wallsHome.size(); j++) {
				OrdinaryWall w = wallsHome.get(j);
				t.collidesWithWall(w);
				w.draw(g);
			}
			for (int a = 0; a < walls.size(); a++) {
				OrdinaryWall w = walls.get(a);
				t.collidesWithWall(w);
				w.draw(g);
			}
			for (int a = 0; a < metalWalls.size(); a++) {
				MetalWall w = metalWalls.get(a);
				t.collidesWithWall(w);
				w.draw(g);
			}
			for (int a = 0; a < rivers.size(); a++) {
				River r = rivers.get(a);
				t.collidesRiver(r);
				r.draw(g);
			}

			t.collidesWithTanks(tanks);
			t.collidesHome(home);
			t.draw(g);
		}
		for (int i = 0; i < missiles.size(); i++) {
			MissileInterface m = missiles.get(i);
			m.hitTanks(tanks);
			m.hitTank(myTank);
			m.hitHome();
			for (int j = 0; j < metalWalls.size(); j++) {
				MetalWall w = metalWalls.get(j);
				m.hitWall(w);
			}
			for (int j = 0; j < walls.size(); j++) {
				OrdinaryWall w = walls.get(j);
				m.hitWall(w);
			}
			for (int j = 0; j < wallsHome.size(); j++) {
				OrdinaryWall w = wallsHome.get(j);
				m.hitWall(w);
			}
			m.draw(g);
			// if(!m.isLive()) missiles.remove(m); // else m.draw(g);
		}

		for (int i = 0; i < walls.size(); i++) {
			OrdinaryWall w = walls.get(i);
			w.draw(g);
		}
		for (int i = 0; i < metalWalls.size(); i++) {
			MetalWall w = metalWalls.get(i);
			w.draw(g);
		}
		for (int i = 0; i < trees.size(); i++) {
			Tree tree = trees.get(i);
			tree.draw(g);
		}
		for (int i = 0; i < explodes.size(); i++) {
			Explode e = explodes.get(i);
			e.draw(g);
		}

		myTank.collidesWithTanks(tanks);
		myTank.collidesHome(home);

		for (int i = 0; i < walls.size(); i++) {
			OrdinaryWall w = walls.get(i);
			myTank.collidesWithWall(w);
			w.draw(g);
		}
		for (int i = 0; i < wallsHome.size(); i++) {
			OrdinaryWall w = wallsHome.get(i);
			myTank.collidesWithWall(w);
			w.draw(g);
		}
		for (int i = 0; i < metalWalls.size(); i++) {
			MetalWall w = metalWalls.get(i);
			myTank.collidesWithWall(w);
			w.draw(g);
		}

		b.draw(g);
		superBloodBox.draw(g);
		rocketBag.draw(g);
		lightning.draw(g);
	}

	public void update(Graphics g) {
		if (screenImage == null) {
			screenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics gScreen = screenImage.getGraphics();
		Color c = gScreen.getColor();
		gScreen.setColor(Color.BLACK);
		gScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gScreen.setColor(c);
		paint(gScreen);
		g.drawImage(screenImage, 0, 0, null);
	}

	//��ʼ����Ϸ���沢��ʼ��Ϸ
	public void lauchFrame(){
		//������Ϸ��̬ͼ������
		home.setTc(this);
		createFrame();
		//��ӵз�̹��
		TankFactory tankFactory=new BadTankFactory(this);
		for (int i = 0; i < 9; i++) {
			Tank t=tankFactory.createTank();
			if (i < 3) {
				t.setX(250 + 60 * i);
				t.setY(50);
				tanks.add(t);
			}
			else if (i < 6){
				t.setX(750);
				t.setY(150 + 60 * (i - 3));
				tanks.add(t);
			} else{
				t.setX(1);
				t.setY(150 + 60 * (i - 6));
				tanks.add(t);
			}
		}
//		for (int i = 0; i < 60; i++) {
//			if (i < 20)
//				tanks.add(new Tank.Tank(60 + 60 * i, 50, false, Client.Direction.D, this));
//			else if (i < 40)
//				tanks.add(new Tank.Tank(750, 60 * (i - 12), false, Client.Direction.D,
//						this));
//			else
//				tanks.add(new Tank.Tank(1,  60 * (i - 18), false, Client.Direction.D,
//						this));
//		}
		//������Ϸ�����С�����ڶ�λ������
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.setLocation(200, 60);
		this.setTitle("TankWar");
		//֧�ִ��ڹر�
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		//�̶���С�����������ӻ�����
		this.setResizable(false);
		this.setBackground(Color.GREEN);
		this.setVisible(true);
		//��Ӽ���֧��
		this.addKeyListener(new KeyMonitor());
		//��ʼ��Ϸ
		new Thread(new PaintThread(this,sleep)).start();
	}
	//������Ϸ��̬ͼ������
	public void createFrame() {
		//������ͨǽ��
		for (int i = 0; i < 27; i++) {
			if (i < 9)
				walls.add(new OrdinaryWall(296 + 22 * i, 300, this));
			else if (i < 18)
				walls.add(new OrdinaryWall(296 + 22 * (i - 9), 320, this));
			else
				walls.add(new OrdinaryWall(296 + 22 * (i - 18), 400, this));
		}
		//���ƽ���ǽ��
		for (int i = 0; i < 14; i++){
			if (i < 5)
				metalWalls.add(new MetalWall(120 + 36 * i, 110, this));
			else if (i < 10)
				metalWalls.add(new MetalWall(120 + 36 * (i - 5), 210, this));
			else
				metalWalls.add(new MetalWall(500 + 36 * (i - 10), 160, this));
		}
		//��������
		for (int i = 0; i < 48; i++) {
			if (i < 8)
				trees.add(new Tree(500 + 36 * i, 300, this));
			else if (i < 16)
				trees.add(new Tree(500 + 36 * (i - 8), 337, this));
			else if (i < 24)
				trees.add(new Tree(500 + 36 * (i - 16), 403, this));
			else if (i < 32)
				trees.add(new Tree(1 + 36 * (i - 24), 300, this));
			else if (i < 40)
				trees.add(new Tree(1 + 36 * (i - 32), 337, this));
			else
				trees.add(new Tree(1 + 36 * (i - 40), 403, this));
		}
		//���ƺ���
		for (int i = 0; i < 2; i++) {
			if (i < 1)
				rivers.add(new River(80, 440, this));
			else
				rivers.add(new River(670, 440, this));
		}
		//�����ϼұ���ǽ
		home.createHomeWall();
	}

	class KeyMonitor extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			myTank.keyPressed(e);
		}
	}
}
