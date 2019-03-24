package Factory;

import Client.Direction;
import Client.TankClient;
import Tank.*;

public class BadTankFactory implements TankFactory {
    private TankClient tc;
    public BadTankFactory(TankClient tc){
        this.tc=tc;
    }
    @Override
    public Tank createTank() {
        BadTank badTank=new BadTank(380, 480, Direction.D, tc);
        return badTank;
    }
}
