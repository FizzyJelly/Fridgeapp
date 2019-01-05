package pl.edu.agh.fridgeapp.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.concurrent.Future;

import pl.edu.agh.fridgeapp.R;
import pl.edu.agh.fridgeapp.client.Toaster;
import pl.edu.agh.fridgeapp.fridge.Data;
import pl.edu.agh.fridgeapp.fridge.Refrigerator;
import pl.edu.agh.fridgeapp.fridge.User;
import pl.edu.agh.fridgeapp.layout_setters.FridgeContentsLayoutSetter;
import pl.edu.agh.fridgeapp.layout_setters.ILayoutSetter;
import pl.edu.agh.fridgeapp.layout_setters.LoginLayoutSetter;

public class MainActivity extends AppCompatActivity {

    private ILayoutSetter layoutSetter;
    private Data appData;
    public static boolean first = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appData = new Data(new Refrigerator());
        Toaster.setContext(this);

        if (getLocalUser() == null) {
            setLayoutSetter(new LoginLayoutSetter(this));

        } else {
            setLayoutSetter(new FridgeContentsLayoutSetter(this));

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

}
