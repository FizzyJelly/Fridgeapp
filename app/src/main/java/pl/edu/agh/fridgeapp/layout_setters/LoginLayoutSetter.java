package pl.edu.agh.fridgeapp.layout_setters;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.fridgeapp.R;
import pl.edu.agh.fridgeapp.activities.MainActivity;
import pl.edu.agh.fridgeapp.client.Toaster;
import pl.edu.agh.fridgeapp.fridge.User;

public class LoginLayoutSetter implements ILayoutSetter {

    private final MainActivity context;

    public LoginLayoutSetter(MainActivity context) {
        this.context = context;
    }

    @Override
    public void setLayout() {
        context.setContentView(R.layout.login_layout);

        EditText usernameInput = context.findViewById(R.id.username_input);

        Button loginButton = context.findViewById(R.id.login_button);

        loginButton.setOnClickListener(v -> {
            if (usernameInput.getText().length() != 0) {
                context.setLocalUser(new User(usernameInput.getText().toString()));
                if(context.getFridge().getOwners().isEmpty()){
                    List<User> newOwner=new ArrayList<>();
                    newOwner.add(context.getLocalUser());
                    context.getFridge().setOwners(newOwner);
                }

                context.setLayoutSetter(new FridgeContentsLayoutSetter(context));
            } else {
                Toaster.toast("Enter username first!");
            }
        });
    }
}
