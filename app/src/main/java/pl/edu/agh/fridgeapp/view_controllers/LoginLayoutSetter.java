package pl.edu.agh.fridgeapp.view_controllers;

import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.ExecutionException;

import pl.edu.agh.fridgeapp.R;
import pl.edu.agh.fridgeapp.activities.MainActivity;
import pl.edu.agh.fridgeapp.model.Data;
import pl.edu.agh.fridgeapp.model.IncorrectLoginDataException;
import pl.edu.agh.fridgeapp.model.User;

public class LoginLayoutSetter implements ILayoutSetter {

    private final MainActivity context;

    public LoginLayoutSetter(MainActivity context) {
        this.context = context;
    }

    @Override
    public void setLayout() {
        context.setContentView(R.layout.login_layout);

        Button loginButton = context.findViewById(R.id.login_button);
        Button offlineButton = context.findViewById(R.id.offline_button);

        loginButton.setOnClickListener(v -> {
            if (obtainOnlineData())
                context.setLayoutSetter(new FridgeContentsLayoutSetter(context));
        });
        offlineButton.setOnClickListener(v -> {
            if (obtainOfflineData())
                context.setLayoutSetter(new FridgeContentsLayoutSetter(context));
        });
    }

    public boolean obtainOnlineData() {
        EditText usernameInput = context.findViewById(R.id.username_input);
        if (usernameInput.getText().length() != 0) {
            Data data = new Data(context);
            context.setAppData(data);
            context.setLocalUser(new User(usernameInput.getText().toString()));
            try {
                data.setConnectionData("192.168.0.59", 4444, 0, 0);
                data.loadRemoteData();

            } catch (InterruptedException | ExecutionException ex) {
                if (ex.getCause() instanceof IncorrectLoginDataException) {
                    Toaster.toast("Incorrect login data!");
                    return false;
                } else {
                    Toaster.toast("Couldn't load remote data,\ntry starting offline");
                    return false;
                }

            }

            return true;
        } else {
            Toaster.toast("Enter username first!");
            return false;
        }
    }

    public boolean obtainOfflineData() {
        EditText usernameInput = context.findViewById(R.id.username_input);
        if (usernameInput.getText().length() != 0) {
            Data data = new Data(context);
            context.setAppData(data);
            context.setLocalUser(new User(usernameInput.getText().toString()));
            try (ObjectInputStream inputStream = new ObjectInputStream(context.openFileInput(MainActivity.savePath))) {
                data.readExternal(inputStream);
            } catch (IOException | ClassNotFoundException | ClassCastException ex) {
                data.createNewTemporaryFridge();
            }

            return true;
        } else {
            Toaster.toast("Enter username first!");

            return false;
        }
    }
}



