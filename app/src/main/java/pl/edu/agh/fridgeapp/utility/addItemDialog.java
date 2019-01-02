package pl.edu.agh.fridgeapp.utility;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import pl.edu.agh.fridgeapp.R;
import pl.edu.agh.fridgeapp.activities.MainActivity;
import pl.edu.agh.fridgeapp.data_classes.ExpiryDate;
import pl.edu.agh.fridgeapp.data_classes.FridgeItem;
import pl.edu.agh.fridgeapp.data_classes.ItemCategory;
import pl.edu.agh.fridgeapp.fridge.User;

public class addItemDialog extends DialogFragment {

    private MainActivity context;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater infalter = context.getLayoutInflater();
        View dialogView=infalter.inflate(R.layout.add_item_dialog,null);
        builder.setView(dialogView);

        ArrayAdapter<String> spinnerAdapter=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,context.getFridge().getCategories());
        Spinner categorySpinner=dialogView.findViewById(R.id.category_spinner);
        categorySpinner.setAdapter(spinnerAdapter);

        Button button=dialogView.findViewById(R.id.accept_item_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemCategory category=ItemCategory.valueOf(categorySpinner.getSelectedItem().toString().toUpperCase());
                String name=((EditText) dialogView.findViewById(R.id.item_name)).getText().toString();
                FridgeItem newItem=new FridgeItem(name, new ExpiryDate(3, 1, 2019), "Such an item it is!!", Double.parseDouble("22.11"), 2, category, new User("User"), new User("Wiesiek"));
                context.getFridge().addItem(newItem);
                dismiss();
            }
            });

        return builder.create();
    }

    public void setContext(MainActivity context) {
        this.context = context;
    }

}
