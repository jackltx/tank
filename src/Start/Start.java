package Start;

import Client.TankClient;

public class Start {
    public static void main(String[] args) {
        TankClient tc = new TankClient(50);
        tc.lauchFrame();
    }
}
