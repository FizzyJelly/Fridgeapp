package pl.edu.agh.fridgeapp.fridge;

import java.util.ArrayList;
import java.util.List;

public class Data {

    private List<ServerInfo> servers;
    private  User localUser;
    private Refrigerator fridge;

    public Data(Refrigerator fridge) {
        this.fridge = fridge;
    }

    public void addServer(ServerInfo server){
        servers.add(server);
    }

    public void removeServer(ServerInfo server){
        servers.remove(server);
    }

    public User getLocalUser() {
        return localUser;
    }

    public void setLocalUser(User localUser) {
        this.localUser = localUser;
    }

    public Refrigerator getFridge() {
        return fridge;
    }
}
