package pl.edu.agh.fridgeapp.view_controllers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;

import pl.edu.agh.fridgeapp.R;
import pl.edu.agh.fridgeapp.activities.MainActivity;
import pl.edu.agh.fridgeapp.model.Date;
import pl.edu.agh.fridgeapp.model.FridgeItem;
import pl.edu.agh.fridgeapp.model.ItemCategory;
import pl.edu.agh.fridgeapp.model.User;

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

        CheckBox sharedCheckbox = dialogView.findViewById(R.id.shared_checkbox);

        EditText descriptionInput = dialogView.findViewById(R.id.description_input);

        EditText priceInput = dialogView.findViewById(R.id.price_input);

        categorySpinner.setAdapter(spinnerAdapter);

        EditText dateEdit = dialogView.findViewById(R.id.expiry_date_input);
        dateEdit.setOnClickListener(v -> {
            DatePickerFragment dateDialog = new DatePickerFragment();
            dateDialog.setDateEdit(dateEdit);
            dateDialog.show(context.getFragmentManager(), new String("get"));
        });

        Button button = dialogView.findViewById(R.id.accept_item_button);
        button.setOnClickListener(v -> {
            StringBuilder missing = new StringBuilder();
            String name = "";
            Date date = new Date(0, 0, 0);
            Integer quantity = 0;
            Double price = 0.0;
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

            if (priceInput.getText().length() == 0) {
                missing.append("Price \n");
            } else {
                price = Double.parseDouble(priceInput.getText().toString());
            }
            if (dateEdit.getText().length() == 0) {
                missing.append("Expiry date \n");
            } else {
                int year = Integer.parseInt(dateEdit.getText().toString().substring(0, 2));
                int month = Integer.parseInt(dateEdit.getText().toString().substring(3, 5));
                int day = Integer.parseInt(dateEdit.getText().toString().substring(6, 10));
                date = new Date(year, month, day);
            }
            if (missing.length() == 0) {
                User user;
                if (sharedCheckbox.isChecked()) {
                    user = new User("Shared");
                } else {
                    user = context.getLocalUser();
                }
                int addYear = Calendar.getInstance().get(Calendar.YEAR);
                int addMonth = Calendar.getInstance().get(Calendar.MONTH);
                int addDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                Date addedDate = new Date(addDay, addMonth, addYear);
                FridgeItem newItem = new FridgeItem(name, addedDate, date, descriptionInput.getText().toString(), price, quantity, category, user, context.getLocalUser());
                context.getFridge().addItem(newItem);

                dismiss();
            } else {
                missing.insert(0, "Item is missing following attributes: \n");
                Toaster.toast(missing.toString());
            }
        });

        return builder.create();
    }

    public void setContext(MainActivity context) {
        this.context = context;
    }

}
