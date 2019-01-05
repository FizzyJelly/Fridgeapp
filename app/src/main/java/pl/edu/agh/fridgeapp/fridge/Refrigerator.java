package pl.edu.agh.fridgeapp.fridge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.stream.Collectors;

import pl.edu.agh.fridgeapp.data_classes.ExpiryDate;
import pl.edu.agh.fridgeapp.data_classes.FridgeItem;
import pl.edu.agh.fridgeapp.data_classes.ItemCategory;
import pl.edu.agh.fridgeapp.data_classes.SortOrder;
import pl.edu.agh.fridgeapp.utility.FridgeItemComparator;

public class Refrigerator extends Observable {

    private List<FridgeItem> items;
    private List<ItemCategory> filter;
    private SortOrder sortOrder;
    private static User common=new User("Common");

    public List<FridgeItem> getItems() {
        List<FridgeItem> filtered = new ArrayList<>(items);

        if (!filter.isEmpty()) {

            Iterator<FridgeItem> iterator = filtered.iterator();
            FridgeItem temp;
            while (iterator.hasNext()) {
                temp = iterator.next();
                if (!filter.contains(temp.getCategory())) {
                    iterator.remove();
                }
            }

        }
        Collections.sort(filtered, new FridgeItemComparator(sortOrder));
        return filtered;
    }

    public Refrigerator() {
        this.filter = new ArrayList<>();
        this.sortOrder = SortOrder.BY_NAME;
    }

    public void setExampleList() {
        this.items = new ArrayList<>();

        for (int i = 0; i < 1; i++) {
            items.add(new FridgeItem("Cheese", new ExpiryDate(6, 1, 2019), "That's a pretty smelly cheese", Double.parseDouble("20.11"), 2, ItemCategory.DIARY, new User("Zbyszek"), new User("Andrzej")));
        }
    }


    public List<String> getCategories() {
        List<String> categories = new ArrayList<>();

        for (ItemCategory cat : ItemCategory.values()) {
            categories.add(cat.getName());
        }

        return categories;
    }


    public void addItem(FridgeItem item) {
        items.add(item);
        this.setChanged();
        this.notifyObservers();
    }

    public void removeItem(FridgeItem item){
        items.remove(item);
        this.setChanged();
        this.notifyObservers();
    }

    public void setFilter(List<ItemCategory> filter) {
        this.filter = filter;
        this.setChanged();
        this.notifyObservers();
    }

    public void modifyItemQuantity(FridgeItem item, int offset){
        item.setQuantity(item.getQuantity()+offset);
        this.setChanged();
        this.notifyObservers();
    }


    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
        this.setChanged();
        this.notifyObservers();
    }

    public static User getCommon() {
        return common;
    }
}
