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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appData = new Data(new Refrigerator("fridge"));
        Toaster.setContext(this);

        loadSavedContents();


        if (getLocalUser() == null) {
            setLayoutSetter(new LoginLayoutSetter(this));

        } else {
            setLayoutSetter(new FridgeContentsLayoutSetter(this));

        }
    }


    @Override
    protected void onStop() {
        super.onStop();

        try (ObjectOutputStream outputStream = new ObjectOutputStream(openFileOutput(getFridge().getName(), Context.MODE_PRIVATE))) {
            outputStream.writeObject(getFridge().getItems());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public Refrigerator getFridge() {
        return appData.getFridge();
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

    public void loadSavedContents() {
        try (ObjectInputStream inputStream = new ObjectInputStream(openFileInput(getFridge().getName()))) {
            this.getFridge().setItems((List<FridgeItem>) inputStream.readObject());
        } catch (IOException | ClassCastException | ClassNotFoundException ex) {
            Toaster.toast("Unable to load saved content");
            getFridge().setItems(new ArrayList<>());
            ex.printStackTrace();
        }

    }

}
