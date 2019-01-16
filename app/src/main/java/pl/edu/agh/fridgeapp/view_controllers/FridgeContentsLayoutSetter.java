package pl.edu.agh.fridgeapp.view_controllers;

import android.app.FragmentManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import pl.edu.agh.fridgeapp.R;
import pl.edu.agh.fridgeapp.activities.MainActivity;
import pl.edu.agh.fridgeapp.model.Refrigerator;
import pl.edu.agh.fridgeapp.model.User;

public class FridgeContentsLayoutSetter implements ILayoutSetter {

    private final MainActivity context;
    private final Refrigerator fridge;

    private CheckableListAdapter adapter;
    private ExpandableListView categoryList;

    private ContentsListAdapter contentsAdapter;
    private RecyclerView contentsList;

    public FridgeContentsLayoutSetter(MainActivity rootActivity) {
        this.context = rootActivity;
        this.fridge = context.getFridge();
    }

    @Override
    public void setLayout() {

        //fridge.setExampleList();


        View displayView = context.getLayoutInflater().inflate(R.layout.fridge_contents_layout, null);
        context.setContentView(displayView);

        DrawerLayout drawer = context.findViewById(R.id.fridge_contents_drawer);
        FrameLayout drawerFrame = context.findViewById(R.id.drawer_frame);

        //Toolbar settings
        Toolbar toolbar = displayView.findViewById(R.id.fridge_contents_toolbar);
        context.setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            drawer.openDrawer(GravityCompat.START);
        });
        ImageButton financeButton = displayView.findViewById(R.id.finance_button);
        financeButton.setOnClickListener(v -> {
            context.setLayoutSetter(new FinanceSummaryLayoutSetter(context));
        });

        //Drawer settings (content of the drawer)
        drawerFrame.setOnClickListener(v -> {
            //consumes the click
        });
        categoryList = context.findViewById(R.id.category_list);
        adapter = new CheckableListAdapter(getDrawerHeaders(), getDrawerChildren(), context, categoryList);
        categoryList.setAdapter(adapter);
        categoryList.expandGroup(0);

        Button changeUserButton = context.findViewById(R.id.change_user_button);
        changeUserButton.setOnClickListener(v -> {
            context.setLayoutSetter(new LoginLayoutSetter(context));
        });


        //Initial items list
        contentsAdapter = new ContentsListAdapter(context);
        contentsList = context.findViewById(R.id.contents_list);
        contentsList.setAdapter(contentsAdapter);
        contentsList.setLayoutManager(new LinearLayoutManager(context));


        //Add item dialog

        FloatingActionButton addButton = context.findViewById(R.id.item_add_button);
        View popupView = LayoutInflater.from(context).inflate(R.layout.add_item_dialog, null);
        AddItemDialog dialog = new AddItemDialog();
        dialog.setContext(context);
        FragmentManager fm = context.getFragmentManager();
        addButton.setOnClickListener(v -> {
            dialog.show(fm, null);
        });

        //Setting observers
        fridge.addObserver(contentsAdapter);

    }

    public Map<String, Boolean> getDrawerHeaders() {

        Map<String, Boolean> listGroups = new LinkedHashMap<>();
        listGroups.put("Categories", false);
        listGroups.put("Sort by", true);
        listGroups.put("Owners", false);
        return listGroups;
    }

    private List<List<String>> getDrawerChildren() {
        List<List<String>> drawerChildren = new ArrayList<>();
        drawerChildren.add(fridge.getCategories());

        List<String> sortWays = new ArrayList<>();
        sortWays.add("Name");
        sortWays.add("Expiry Date");
        sortWays.add("Category");

        drawerChildren.add(sortWays);


        List<String> ownerNames = new ArrayList<>();
        for (User user : fridge.getOwners()) {
            ownerNames.add(user.getName());
        }
        ownerNames.add("Shared");
        drawerChildren.add(ownerNames);


        return drawerChildren;
    }


}
