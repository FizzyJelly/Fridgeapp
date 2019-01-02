package pl.edu.agh.fridgeapp.client;

import android.widget.Toast;

import pl.edu.agh.fridgeapp.activities.MainActivity;

public class Toaster {
    private static MainActivity context;

    public static void setContext(MainActivity context) {
        Toaster.context = context;
    }

    public static void toast(final String message){
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
            }
        });
    }

}