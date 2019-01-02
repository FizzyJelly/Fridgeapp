package pl.edu.agh.fridgeapp.fridge;

import java.util.ArrayList;
import java.util.List;

public class Data {

    private List<ServerInfo> servers;


    public Data() {
        this.servers = new ArrayList<ServerInfo>();
        servers.add(new ServerInfo("192.168.0.59",4444));
    }

    public void addServer(ServerInfo server){
        servers.add(server);
    }

    public void removeServer(ServerInfo server){
        servers.remove(server);
    }

}
