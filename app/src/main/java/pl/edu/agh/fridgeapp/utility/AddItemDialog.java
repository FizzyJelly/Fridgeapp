package pl.edu.agh.fridgeapp.utility;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;

import pl.edu.agh.fridgeapp.R;
import pl.edu.agh.fridgeapp.activities.MainActivity;
import pl.edu.agh.fridgeapp.client.Toaster;
import pl.edu.agh.fridgeapp.data_classes.ExpiryDate;
import pl.edu.agh.fridgeapp.data_classes.FridgeItem;
import pl.edu.agh.fridgeapp.data_classes.ItemCategory;
import pl.edu.agh.fridgeapp.fridge.Refrigerator;
import pl.edu.agh.fridgeapp.fridge.User;

public class AddItemDialog extends DialogFragment {

    private MainActivity context;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater infalter = context.getLayoutInflater();
        View dialogView = infalter.inflate(R.layout.add_item_dialog, null);
        builder.setView(dialogView);

        EditText itemNameInput = dialogView.findViewById(R.id.item_name_input);
        EditText quantityInput = dialogView.findViewById(R.id.quantity_input);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, context.getFridge().getCategories());
        Spinner categorySpinner = dialogView.findViewById(R.id.category_spinner);

        CheckBox commonCheckbox = dialogView.findViewById(R.id.common_checkbox);

        EditText descriptionInput = dialogView.findViewById(R.id.description_input);

        EditText priceInput = dialogView.findViewById(R.id.price_input);

        categorySpinner.setAdapter(spinnerAdapter);

        EditText dateEdit = dialogView.findViewById(R.id.expiry_date_input);
        dateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment dateDialog = new DatePickerFragment();
                dateDialog.setDateEdit(dateEdit);
                dateDialog.show(context.getFragmentManager(), new String("get"));
            }
        });

        Button button = dialogView.findViewById(R.id.accept_item_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder missing = new StringBuilder();
                String name = "";
                ExpiryDate expiryDate = new ExpiryDate(0, 0, 0);
                Integer quantity = 0;
                ItemCategory category = ItemCategory.valueOf(categorySpinner.getSelectedItem().toString().toUpperCase());
                if (itemNameInput.getText().length() == 0) {
                    missing.append("Item name \n");

                } else {
                    name = itemNameInput.getText().toString();
                }
                if (quantityInput.getText().length() == 0) {
                    missing.append("Quantity \n");
                } else {
                    quantity = Integer.parseInt(quantityInput.getText().toString());
                }
                if (dateEdit.getText().length() == 0) {
                    missing.append("Expiry date \n");
                } else {
                    int year = Integer.parseInt(dateEdit.getText().toString().substring(0, 2));
                    int month = Integer.parseInt(dateEdit.getText().toString().substring(3, 5));
                    int day = Integer.parseInt(dateEdit.getText().toString().substring(6, 10));
                    expiryDate = new ExpiryDate(year, month, day);
                }
                if (missing.length() == 0) {
                    User user;
                    if (commonCheckbox.isChecked()) {
                        user = new User("Common");
                    } else {
                        user = context.getLocalUser();
                    }
                    Double price = Double.parseDouble(priceInput.getText().toString());
                    FridgeItem newItem = new FridgeItem(name, expiryDate, descriptionInput.getText().toString(), price, quantity, category, user, context.getLocalUser());
                    context.getFridge().addItem(newItem);

                    dismiss();
                } else {
                    missing.insert(0, "Item is missing following attributes: \n");
                    Toaster.toast(missing.toString());
                }
            }

        });

        return builder.create();
    }

    public void setContext(MainActivity context) {
        this.context = context;
    }

}
