package pl.edu.agh.fridgeapp.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.fridgeapp.client.Toaster;
import pl.edu.agh.fridgeapp.data_classes.FridgeItem;
import pl.edu.agh.fridgeapp.fridge.Data;
import pl.edu.agh.fridgeapp.fridge.Refrigerator;
import pl.edu.agh.fridgeapp.fridge.User;
import pl.edu.agh.fridgeapp.layout_setters.FridgeContentsLayoutSetter;
import pl.edu.agh.fridgeapp.layout_setters.ILayoutSetter;
import pl.edu.agh.fridgeapp.layout_setters.LoginLayoutSetter;

public class MainActivity extends AppCompatActivity {

    private ILayoutSetter layoutSetter;
    private Data appData;
    private static String savePath ="savedContent";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toaster.setContext(this);
        appData = new Data(loadSavedContents());



        if (getLocalUser() == null) {
            setLayoutSetter(new LoginLayoutSetter(this));

        } else {
            setLayoutSetter(new FridgeContentsLayoutSetter(this));

        }
    }


    @Override
    protected void onStop() {
        super.onStop();

        try (ObjectOutputStream outputStream = new ObjectOutputStream(openFileOutput(savePath, Context.MODE_PRIVATE))) {
            getFridge().setCategoryFilter(new ArrayList<>());
            outputStream.writeObject(getFridge().getName());
            outputStream.writeObject(getFridge().getItems());
            outputStream.writeObject(getFridge().getOwners());

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public Refrigerator getFridge() {
        return appData.getCurrentFridge();
    }

    public User getLocalUser() {
        return appData.getLocalUser();
    }

    public void setLocalUser(User user) {
        appData.setLocalUser(user);
    }

    private void setLayout() {
        this.layoutSetter.setLayout();
    }

    public void setLayoutSetter(ILayoutSetter layoutSetter) {
        this.layoutSetter = layoutSetter;
        this.setLayout();
    }

    public Data getAppData() {
        return appData;
    }

    public Refrigerator loadSavedContents() {
        Refrigerator fridge;

        try (ObjectInputStream inputStream = new ObjectInputStream(openFileInput(savePath))) {
            fridge=new Refrigerator((String) inputStream.readObject());
            fridge.setItems((List<FridgeItem>) inputStream.readObject());
            List<User> owners=new ArrayList<>();
            List<String> names=(List<String>) inputStream.readObject();
            for(String name: names){
                owners.add(new User(name));
            }
            fridge.setOwners(owners);
        } catch (IOException | ClassCastException | ClassNotFoundException ex) {
            Toaster.toast("Unable to load saved content");
            ex.printStackTrace();
            fridge=new Refrigerator("Temporary");
            fridge.setItems(new ArrayList<>());
            fridge.setOwners(new ArrayList<>());
        }

        return fridge;
    }



}
