package pl.edu.agh.fridgeapp.model;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import pl.edu.agh.fridgeapp.view_controllers.FridgeItemComparator;

public class Refrigerator extends Observable implements Externalizable {

    private String name;
    private List<FridgeItem> items;
    private List<ItemCategory> categoryFilter;
    private List<User> ownerFilter;
    private List<User> owners;
    private SortOrder sortOrder;

    public List<FridgeItem> getItems() {
        List<FridgeItem> filtered = new ArrayList<>(items);

        if (!categoryFilter.isEmpty()) {

            Iterator<FridgeItem> iterator = filtered.iterator();
            FridgeItem temp;
            while (iterator.hasNext()) {
                temp = iterator.next();
                if (!categoryFilter.contains(temp.getCategory())) {
                    iterator.remove();
                }
            }

        }
        if (!ownerFilter.isEmpty()) {

            Iterator<FridgeItem> iterator = filtered.iterator();
            FridgeItem temp;
            while (iterator.hasNext()) {
                temp = iterator.next();
                if (!(temp.getOwner().getName().equals("Shared") || ownerFilter.contains(temp.getOwner()))) {
                    iterator.remove();
                }
            }

        }
        Collections.sort(filtered, new FridgeItemComparator(sortOrder));
        return filtered;
    }

    public void setOwners(List<User> owners) {
        this.owners = owners;
    }

    public void addNewUser(User newUser) {
        if (!owners.contains(newUser))
            owners.add(newUser);
    }


    public Refrigerator() {
        this.categoryFilter = new ArrayList<>();
        this.sortOrder = SortOrder.BY_NAME;
        this.ownerFilter = new ArrayList<>();
    }

    public Refrigerator(String name) {
        this();
        this.name = name;
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

    public void setCategoryFilter(List<ItemCategory> filter) {
        this.categoryFilter = filter;
        if (!this.hasChanged()) {
            this.setChanged();
            this.notifyObservers();
        }
    }

    public void modifyItemQuantity(FridgeItem item, int offset) {
        item.setQuantity(item.getQuantity() + offset);
        this.setChanged();
        this.notifyObservers();
    }


    public List<User> getOwners() {
        List<User> ownersCopy = new ArrayList<>(owners);
        return ownersCopy;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
        if (!this.hasChanged()) {
            this.setChanged();
            this.notifyObservers();
        }

    }

    public void setOwnerFilter(List<User> filter) {
        this.ownerFilter = filter;
        if (!this.hasChanged()) {
            this.setChanged();
            this.notifyObservers();
        }
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
        out.writeObject(items);
        out.writeObject(owners);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.name = (String) in.readObject();
        this.items = (List<FridgeItem>) in.readObject();
        this.owners = (List<User>) in.readObject();
    }

    public String getName() {
        return name;
    }

}
