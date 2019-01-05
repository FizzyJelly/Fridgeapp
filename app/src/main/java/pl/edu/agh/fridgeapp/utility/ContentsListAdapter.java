package pl.edu.agh.fridgeapp.utility;

import android.app.FragmentManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import pl.edu.agh.fridgeapp.R;
import pl.edu.agh.fridgeapp.activities.MainActivity;
import pl.edu.agh.fridgeapp.data_classes.FridgeItem;
import pl.edu.agh.fridgeapp.data_classes.ItemCategory;

public class ContentsListAdapter extends RecyclerView.Adapter implements Observer {

    private List<FridgeItem> fridgeContents;
    private MainActivity context;


    @Override
    public void update(Observable o, Object arg) {
        this.fridgeContents=context.getFridge().getItems();
        this.notifyDataSetChanged();
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {

        public ItemHolder(LinearLayout layout) {
            super(layout);
        }
    }

    public ContentsListAdapter(MainActivity context) {
        this.context=context;
        fridgeContents=this.context.getFridge().getItems();

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout l = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.contents_list_item, parent, false);

        ItemHolder holder = new ItemHolder(l);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        LinearLayout layout = (LinearLayout) holder.itemView;

        TextView name = layout.findViewById(R.id.item_name_input);
        TextView quantity = layout.findViewById(R.id.quantity);
        TextView expiryDate = layout.findViewById(R.id.expiry_date);
        TextView owner = layout.findViewById(R.id.owner);
        ImageView categoryImage = layout.findViewById(R.id.category_image);

        name.setText(fridgeContents.get(position).getName());
        quantity.setText("Quantity: " + ((Integer) fridgeContents.get(position).getQuantity()).toString());
        expiryDate.setText("Expires on: " + fridgeContents.get(position).getExpiryDate().toString());
        owner.setText("Owner: " + fridgeContents.get(position).getOwner().toString());
        ItemCategory category=fridgeContents.get(position).getCategory();

        switch (category){
            case MEAT: categoryImage.setBackgroundColor(Color.parseColor("#FF1111")); break;
            case DIARY: categoryImage.setBackgroundColor(Color.parseColor("#FFFF66")); break;
            case FRUITS: categoryImage.setBackgroundColor(Color.parseColor("#FF69B4")); break;
            case VEGETABLE: categoryImage.setBackgroundColor(Color.parseColor("#66CD00")); break;
            default: categoryImage.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemDetailsDialog dialog=new ItemDetailsDialog();
                dialog.setContext(context);
                dialog.setItem(fridgeContents.get(position));
                FragmentManager fm=context.getFragmentManager();
                dialog.show(fm,"Item details");
            }
        });
    }

    @Override
    public int getItemCount() {
        return fridgeContents.size();
    }
}
