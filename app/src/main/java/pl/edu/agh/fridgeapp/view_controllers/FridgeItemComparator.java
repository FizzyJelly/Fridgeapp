package pl.edu.agh.fridgeapp.view_controllers;

import java.util.Comparator;

import pl.edu.agh.fridgeapp.model.FridgeItem;
import pl.edu.agh.fridgeapp.model.SortOrder;

public class FridgeItemComparator implements Comparator<FridgeItem> {

    private final SortOrder order;

    public FridgeItemComparator(SortOrder order) {
        this.order = order;
    }

    @Override
    public int compare(FridgeItem f1, FridgeItem f2) {
        switch (order) {
            case BY_NAME:
                return byName(f1, f2);
            case BY_DATE:
                return byExpiryDate(f1, f2);
            case BY_CATEGORY:
                return byCategory(f1, f2);
        }

        return 0;
    }

    private int byName(FridgeItem o1, FridgeItem o2) {

        return o1.getName().compareTo(o2.getName());
    }

    private int byExpiryDate(FridgeItem o1, FridgeItem o2) {
        return o1.getExpiryDate().compareTo(o2.getExpiryDate());
    }

    private int byCategory(FridgeItem o1, FridgeItem o2) {
        return o1.getCategory().getName().compareTo(o2.getCategory().getName());
    }

}
