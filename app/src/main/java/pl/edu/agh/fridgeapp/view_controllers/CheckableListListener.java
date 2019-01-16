package pl.edu.agh.fridgeapp.view_controllers;

import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import java.util.List;

import pl.edu.agh.fridgeapp.R;

public class CheckableListListener implements ExpandableListView.OnChildClickListener {

    private final CheckableListAdapter listAdapter;
    private final List<Boolean> groupSingleSelectable;

    public CheckableListListener(CheckableListAdapter listAdapter, List<Boolean> groupSingleSelectable) {
        this.listAdapter = listAdapter;
        this.groupSingleSelectable = groupSingleSelectable;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

        CheckedTextView temp;
        if (v instanceof FrameLayout) {
            if (((FrameLayout) v).getChildAt(0) instanceof CheckedTextView) {
                temp = (CheckedTextView) (((FrameLayout) v).getChildAt(0));


                if(groupSingleSelectable.get(groupPosition)){
                    handleSingleSelectable(temp,groupPosition,childPosition);
                } else {
                    handleMultipleSelectable(temp,groupPosition,childPosition);
                }
                listAdapter.updateOnCheck();
            }
        }
        return true;
    }


    private void handleSingleSelectable(CheckedTextView view, int group, int child){
        if(!view.isChecked()){
            uncheckAll(group);
            check(view,group,child);
        }
    }
    private void handleMultipleSelectable(CheckedTextView view,int group, int child){
        if(view.isChecked()){
            uncheck(view,group,child);
        } else {
            check(view,group,child);
        }

    }
    private void check(CheckedTextView view, int group, int child) {
        view.setChecked(true);
        view.setCheckMarkDrawable(R.drawable.checkmark_icon);
        listAdapter.setChildChecked(group,child,true);

    }

    private void uncheck(CheckedTextView view,int group, int child) {
        view.setChecked(false);
        view.setCheckMarkDrawable(null);
        listAdapter.setChildChecked(group,child,false);
    }

    private void uncheckAll(int group) {
        CheckedTextView temp;
        for (int i = 0; i < listAdapter.getChildrenCount(group); i++) {
            temp = (CheckedTextView) (((FrameLayout) listAdapter.getChildView(group, i, false, null, null)).getChildAt(0));
            uncheck(temp,group,i);
        }

    }
}
