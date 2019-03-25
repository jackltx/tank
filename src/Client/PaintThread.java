package Client;

public class PaintThread implements Runnable {
    private TankClient tc;
    public PaintThread(TankClient tc){
        this.tc=tc;
    }
    public void run() {
        while (true) {
            tc.repaint();
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}