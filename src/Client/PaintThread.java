package Client;

public class PaintThread implements Runnable {
    private int sleep;
    private TankClient tc;
    public PaintThread(TankClient tc,int sleep){
        this.sleep=sleep;
        this.tc=tc;
    }
    public void run() {
        while (true) {
            tc.repaint();
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}