package pl.edu.agh.fridgeapp.view_controllers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import pl.edu.agh.fridgeapp.R;
import pl.edu.agh.fridgeapp.activities.MainActivity;
import pl.edu.agh.fridgeapp.model.FridgeItem;

public class ItemDetailsDialog extends DialogFragment {

    private MainActivity context;
    private FridgeItem item;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = context.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.item_detail_dialog, null);
        builder.setView(dialogView);

        TextView nameView = dialogView.findViewById(R.id.name_view);
        TextView quantityView = dialogView.findViewById(R.id.quantity_view);
        TextView categoryView = dialogView.findViewById(R.id.category_view);
        TextView descriptionView = dialogView.findViewById(R.id.description_view);
        TextView dateView = dialogView.findViewById(R.id.expiry_date_view);
        TextView priceView = dialogView.findViewById(R.id.price_view);
        TextView ownerView = dialogView.findViewById(R.id.owner_view);


        String name = item.getName();
        String quantity = ((Integer) item.getQuantity()).toString();
        String category = item.getCategory().getName();
        String description = item.getDescription();
        String date = item.getExpiryDate().toString();
        String price = item.getPrice() + " PLN";
        String owner = item.getOwner().toString();

        nameView.setText(name);
        quantityView.setText(quantity);
        categoryView.setText(category);
        descriptionView.setText(description);
        dateView.setText(date);
        priceView.setText(price);
        ownerView.setText(owner);

        ImageButton increaseButton = dialogView.findViewById(R.id.increase_button);
        ImageButton decreaseButton = dialogView.findViewById(R.id.decrease_button);
        Button removeButton = dialogView.findViewById(R.id.remove_button);

        dialogView.setOnClickListener(v -> dismiss());

        increaseButton.setOnClickListener(v -> {
            context.getFridge().modifyItemQuantity(item, 1);
            quantityView.setText(((Integer) item.getQuantity()).toString());
        });

        decreaseButton.setOnClickListener(v -> {
            context.getFridge().modifyItemQuantity(item, -1);
            if (item.getQuantity() <= 0) {
                context.getFridge().removeItem(item);
                Toaster.toast("Item successfully removed");
                dismiss();
            }
            quantityView.setText(((Integer) item.getQuantity()).toString());
        });

        removeButton.setOnClickListener(v -> {
            context.getFridge().removeItem(item);
            Toaster.toast("Item successfully removed");
            dismiss();
        });

        return builder.create();
    }

    public void setContext(MainActivity context) {
        this.context = context;
    }

    public void setItem(FridgeItem item) {
        this.item = item;
    }
}
