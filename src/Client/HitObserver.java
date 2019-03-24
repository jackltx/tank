package Client;

import Environment.Missile;
import Factory.BadTankFactory;
import Factory.TankFactory;
import Tank.Tank;

import java.util.Observable;
import java.util.Observer;
public class HitObserver implements Observer {
    private Observable observable;
    private TankClient tc;
    public HitObserver(Observable observable,TankClient tc){
        this.observable=observable;
        this.tc=tc;
        observable.addObserver(this);
    }
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Missile){
            TankFactory tankFactory=new BadTankFactory(tc);
            Tank another=tankFactory.createTank();
            another.setX(1);
            another.setY(1);
            tc.tanks.add(another);
        }
    }
}
