package pl.edu.agh.fridgeapp.fridge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import pl.edu.agh.fridgeapp.data_classes.ExpiryDate;
import pl.edu.agh.fridgeapp.data_classes.FridgeItem;
import pl.edu.agh.fridgeapp.data_classes.ItemCategory;
import pl.edu.agh.fridgeapp.data_classes.SortOrder;
import pl.edu.agh.fridgeapp.utility.FridgeItemComparator;

public class Refrigerator extends Observable {

    private String name;
    private List<FridgeItem> items;
    private List<ItemCategory> filter;
    private SortOrder sortOrder;

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

    public Refrigerator(String name) {
        this.name = name;
        this.filter = new ArrayList<>();
        this.sortOrder = SortOrder.BY_NAME;
    }

    public void setExampleList() {
        this.items = new ArrayList<>();

        String[] names = {"yogurt", "sausage", "salad", "strawberry", "cookie"};
        String[] adjectives = {"Delicious", "Salty", "Outrageous", "Granny's", "Monstrous"};

        User user = new User("Bot");
        for (int i = 0; i < 20; i++) {
            int nameNumber = ThreadLocalRandom.current().nextInt(0, 5);
            String name = adjectives[ThreadLocalRandom.current().nextInt(0, 5)] + " " + names[nameNumber];
            ExpiryDate date = new ExpiryDate(ThreadLocalRandom.current().nextInt(10, 20), 1, 2019);
            Double priceLong = ThreadLocalRandom.current().nextDouble(3, 20) * 100.0;
            Integer temp = priceLong.intValue();
            Double price = temp / 100.0;
            int quantity = ThreadLocalRandom.current().nextInt(1, 5);
            ItemCategory category = ItemCategory.values()[nameNumber];
            addItem(new FridgeItem(name, date, null, price, quantity, category, user, user));
        }
    }

    public void setItems(List<FridgeItem> items) {
        this.items = items;
        setChanged();
        notifyObservers();
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

    public void removeItem(FridgeItem item) {
        items.remove(item);
        this.setChanged();
        this.notifyObservers();
    }

    public void setFilter(List<ItemCategory> filter) {
        this.filter = filter;
        this.setChanged();
        this.notifyObservers();
    }

    public void modifyItemQuantity(FridgeItem item, int offset) {
        item.setQuantity(item.getQuantity() + offset);
        this.setChanged();
        this.notifyObservers();
    }


    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
        this.setChanged();
        this.notifyObservers();
    }


    public String getName() {
        return name;
    }
}
