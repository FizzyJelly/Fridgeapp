package pl.edu.agh.fridgeapp.model;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import pl.edu.agh.fridgeapp.activities.MainActivity;

public class Data implements Externalizable {

    private User localUser;
    private Refrigerator fridge;
    private MainActivity context;
    private FridgeClient networkClient;

    public Data(MainActivity context) {
        this.context = context;
    }

    public void setConnectionData(String host, int portNumber, int loginHash, int passHash) {
        networkClient = new FridgeClient(host, portNumber, loginHash, passHash);
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


    public void loadRemoteData() throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Refrigerator> futureFridge = executor.submit(networkClient);

        this.fridge = futureFridge.get();
    }

    public void createNewTemporaryFridge() {
        fridge = new Refrigerator("Temporary");
        fridge.setItems(new ArrayList<>());
        List<User> newOwner = new ArrayList<>();
        newOwner.add(getLocalUser());
        fridge.setOwners(newOwner);
    }


    public void loadFridge(ObjectInput in) throws IOException, ClassNotFoundException {
        fridge = new Refrigerator();
        fridge.readExternal(in);
        fridge.addNewUser(localUser);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(localUser);
        fridge.writeExternal(out);

    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        User loadedUser= (User) in.readObject();
        if(localUser==null||localUser.equals(loadedUser))
            this.localUser=loadedUser;
        loadFridge(in);
    }
}
