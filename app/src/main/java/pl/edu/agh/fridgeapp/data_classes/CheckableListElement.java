package pl.edu.agh.fridgeapp.data_classes;

import android.view.View;

public class CheckableListElement {

    private final View view;
    private boolean isChecked;
    private String name;

    public CheckableListElement(View v, String name) {
        this.view = v;
        this.name = name;
    }
    public View getView() {
        return view;
    }

    public boolean isChecked() {

        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getName() {
        return name;
    }
}
