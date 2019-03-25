package Factory;

import Client.Direction;
import Client.TankClient;
import Tank.GoodTank;
import Tank.Tank;

public class GoodTankFactory implements TankFactory {
    private TankClient tc;
    public GoodTankFactory(TankClient tc){
        this.tc=tc;
    }
    @Override
    public Tank createTank() {
        GoodTank goodTank=new GoodTank(380, 480, tc);
        return goodTank;
    }
}
