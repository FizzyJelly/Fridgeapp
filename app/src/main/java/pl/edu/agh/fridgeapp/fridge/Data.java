package pl.edu.agh.fridgeapp.fridge;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pl.edu.agh.fridgeapp.client.Toaster;
import pl.edu.agh.fridgeapp.data_classes.FridgeItem;

public class Data {

    private List<ServerInfo> servers;
    private User localUser;
    private Refrigerator fridge;
    public Data(Refrigerator fridge) {
        this.fridge = fridge;
    }

    public void addServer(ServerInfo server) {
        servers.add(server);
    }

    public void removeServer(ServerInfo server) {
        servers.remove(server);
    }

    public User getLocalUser() {
        return localUser;
    }

    public void setLocalUser(User localUser) {
        this.localUser = localUser;
    }

    public Refrigerator getCurrentFridge() {
        return fridge;
    }


}
