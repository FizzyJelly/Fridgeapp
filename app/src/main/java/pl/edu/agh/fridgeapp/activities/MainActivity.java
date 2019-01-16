package pl.edu.agh.fridgeapp.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import pl.edu.agh.fridgeapp.view_controllers.Toaster;
import pl.edu.agh.fridgeapp.model.Data;
import pl.edu.agh.fridgeapp.model.Refrigerator;
import pl.edu.agh.fridgeapp.model.User;
import pl.edu.agh.fridgeapp.view_controllers.FinanceSummaryLayoutSetter;
import pl.edu.agh.fridgeapp.view_controllers.FridgeContentsLayoutSetter;
import pl.edu.agh.fridgeapp.view_controllers.ILayoutSetter;
import pl.edu.agh.fridgeapp.view_controllers.LoginLayoutSetter;

public class MainActivity extends AppCompatActivity {

    private ILayoutSetter layoutSetter;
    public static String savePath = "savePath";
    private Data appData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toaster.setContext(this);

        appData = new Data(this);

        try (ObjectInputStream inputStream = new ObjectInputStream(openFileInput(savePath))) {
            appData.readExternal(inputStream);
            setLayoutSetter(new FridgeContentsLayoutSetter(this));
        } catch (ClassNotFoundException | IOException | ClassCastException ex) {
            ex.printStackTrace();
            setLayoutSetter(new LoginLayoutSetter(this));
        }

    }


    @Override
    protected void onStop() {
        super.onStop();

        try (ObjectOutputStream outputStream = new ObjectOutputStream(openFileOutput(savePath, Context.MODE_PRIVATE))) {
            appData.writeExternal(outputStream);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        if (layoutSetter instanceof FinanceSummaryLayoutSetter) {
            setLayoutSetter(new FridgeContentsLayoutSetter(this));
        }
    }

    public void setAppData(Data appData) {
        this.appData = appData;
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
        if (this.layoutSetter instanceof LoginLayoutSetter && appData!=null && appData.getCurrentFridge()!=null) {
            try (ObjectOutputStream outputStream = new ObjectOutputStream(openFileOutput(savePath, Context.MODE_PRIVATE))) {
                appData.writeExternal(outputStream);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        this.setLayout();
    }


}
