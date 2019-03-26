package Environment;

import Tank.Tank;
import java.awt.*;
import java.util.*;
import java.util.List;

public interface MissileInterface {
//    public static final Map<String, Image> imgs = new HashMap<String, Image>();
//    public static final Toolkit tk = Toolkit.getDefaultToolkit();
//    public static final Image[] missileImages = new Image[5];

    void draw(Graphics g);
    void move();
    boolean isLive();
    Rectangle getRect();
    boolean hitTank(Tank t);
    boolean hitTanks(List<Tank> tanks);
     boolean hitWall(OrdinaryWall w);
     boolean hitWall(MetalWall w);
     boolean hitHome();

     int getX();

     int getY();
     void setX(int x);
     void setY(int y);

    int getXSPEED();

    int getYSPEED();

    Map<String, Image> getImgs();



}
