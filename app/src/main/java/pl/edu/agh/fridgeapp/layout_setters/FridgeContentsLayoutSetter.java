package pl.edu.agh.fridgeapp.layout_setters;

import android.app.FragmentManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import pl.edu.agh.fridgeapp.R;
import pl.edu.agh.fridgeapp.activities.MainActivity;
import pl.edu.agh.fridgeapp.fridge.Refrigerator;
import pl.edu.agh.fridgeapp.utility.CheckableListAdapter;
import pl.edu.agh.fridgeapp.utility.ContentsListAdapter;
import pl.edu.agh.fridgeapp.utility.AddItemDialog;

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

        fridge.setExampleList();


        View displayView = context.getLayoutInflater().inflate(R.layout.fridge_contents_layout, null);
        context.setContentView(displayView);

        DrawerLayout drawer = context.findViewById(R.id.fridge_contents_drawer);

        //Toolbar settings
        Toolbar toolbar = displayView.findViewById(R.id.fridge_contents_toolbar);
        context.setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            drawer.openDrawer(GravityCompat.START);
        });

        //Drawer settings
        categoryList = context.findViewById(R.id.category_list);
        adapter = new CheckableListAdapter(getDrawerHeaders(), getDrawerChildren(fridge.getCategories()), context, categoryList);
        categoryList.setAdapter(adapter);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                fridge.setFilter(adapter.getSelectedCategories());
                fridge.setSortOrder(adapter.getSelectedSortOrder());
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        //Add contents update on Drawer.closed


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

        Map<String, Boolean> listGroups = new TreeMap<>();
        listGroups.put("Categories", true);
        listGroups.put("Sort by", false);
        return listGroups;
    }

    public List<List<String>> getDrawerChildren(List<String> categories) {
        List<List<String>> drawerChildren = new ArrayList<>();
        drawerChildren.add(categories);

        List<String> sortWays = new ArrayList<>();
        sortWays.add("Name");
        sortWays.add("Expiry Date");
        sortWays.add("Category");

        drawerChildren.add(sortWays);

        return drawerChildren;
    }


}
