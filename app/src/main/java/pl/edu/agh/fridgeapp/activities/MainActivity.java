package pl.edu.agh.fridgeapp.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import pl.edu.agh.fridgeapp.R;
import pl.edu.agh.fridgeapp.client.Toaster;
import pl.edu.agh.fridgeapp.fridge.Refrigerator;
import pl.edu.agh.fridgeapp.layout_setters.FridgeContentsLayoutSetter;
import pl.edu.agh.fridgeapp.layout_setters.ILayoutSetter;

public class MainActivity extends AppCompatActivity {

    private ILayoutSetter layoutSetter;
    private Refrigerator fridge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.fridge=new Refrigerator();

        Toaster.setContext(this);
        layoutSetter=new FridgeContentsLayoutSetter(this);
        layoutSetter.setLayout();
    }

    public Refrigerator getFridge() {
        return fridge;
    }
}
