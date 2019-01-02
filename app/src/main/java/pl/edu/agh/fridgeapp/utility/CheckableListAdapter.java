package pl.edu.agh.fridgeapp.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import pl.edu.agh.fridgeapp.R;
import pl.edu.agh.fridgeapp.data_classes.CheckableListElement;
import pl.edu.agh.fridgeapp.data_classes.ItemCategory;
import pl.edu.agh.fridgeapp.data_classes.SortOrder;

public class CheckableListAdapter extends BaseExpandableListAdapter {

    private List<TextView> groups;
    private List<Boolean> groupSingleSelectable;
    private List<List<CheckableListElement>> children;
    private final Context context;
    private final ExpandableListView list;

    public CheckableListAdapter(Map<String, Boolean> listGroups, List<List<String>> childTexts, Context context, ExpandableListView list) {

        this.context = context;
        groups = new ArrayList<>();
        groupSingleSelectable = new ArrayList<>();
        groupSingleSelectable.addAll(listGroups.values());
        Collections.reverse(groupSingleSelectable);
        children = new ArrayList<>();
        this.list = list;

        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView tempGroup;
        List<CheckableListElement> tempChildren;
        FrameLayout tempChildLayout;

        for (String groupText : listGroups.keySet()) {

            tempGroup = (TextView) inflater.inflate(R.layout.list_group, null);
            tempGroup.setText(groupText);
            groups.add(tempGroup);

            for (List<String> childrenOfGroup : childTexts) {

                tempChildren = new ArrayList<>();

                for (String childText : childrenOfGroup) {
                    tempChildLayout = (FrameLayout) inflater.inflate(R.layout.checked_list_item, null);
                    ((CheckedTextView) tempChildLayout.findViewById(R.id.checkedListItemLabel)).setText(childText);

                    tempChildren.add(new CheckableListElement(tempChildLayout, childText));
                }
                children.add(tempChildren);
            }
        }

        CheckedTextView defaultSort=(CheckedTextView) ((FrameLayout) children.get(1).get(0).getView()).getChildAt(0);
        defaultSort.setChecked(true);
        defaultSort.setCheckMarkDrawable(R.drawable.checkmark_icon);
        setChildChecked(1,0,true);

        setListeners();
    }

    public List<ItemCategory> getSelectedCategories() {
        List<ItemCategory> selected = new ArrayList<>();
        for (CheckableListElement child : children.get(0)) {
            if (child.isChecked()) {
                selected.add(ItemCategory.valueOf(child.getName().toUpperCase()));
            }
        }
        return selected;
    }

    public SortOrder getSelectedSortOrder() {
        String selected = "";

        for (CheckableListElement child : children.get(1)) {
            if (child.isChecked()) {
                selected = child.getName();
            }
        }

        switch (selected) {
            case "Category":
                return SortOrder.BY_CATEGORY;
            case "Expiry Date":
                return SortOrder.BY_DATE;
            default:
                return SortOrder.BY_NAME;
        }
    }

    public void setChildChecked(int group, int child, boolean checked){
        children.get(group).get(child).setChecked(checked);
    }

    public void setListeners() {
        list.setOnChildClickListener(new CheckableListListener(this, groupSingleSelectable));
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return children.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return children.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View
            convertView, ViewGroup parent) {

        convertView = groups.get(groupPosition);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View
            convertView, ViewGroup parent) {
        convertView = children.get(groupPosition).get(childPosition).getView();
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
