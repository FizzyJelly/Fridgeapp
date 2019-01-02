package pl.edu.agh.fridgeapp.utility;

import java.util.Comparator;

import pl.edu.agh.fridgeapp.data_classes.FridgeItem;
import pl.edu.agh.fridgeapp.data_classes.SortOrder;

public class FridgeItemComparator implements Comparator {

    private final SortOrder order;

    public FridgeItemComparator(SortOrder order) {
        this.order = order;
    }

    @Override
    public int compare(Object o1, Object o2) {
        if (o1 instanceof FridgeItem && o2 instanceof FridgeItem) {

            FridgeItem f1=(FridgeItem) o1;
            FridgeItem f2=(FridgeItem) o2;
            switch (order) {
                case BY_NAME:
                    return byName(f1, f2);
                case BY_DATE:
                    return byExpiryDate(f1, f2);
                case BY_CATEGORY:
                    return byCategory(f1, f2);
            }
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
