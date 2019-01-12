package pl.edu.agh.fridgeapp.data_classes;

import java.io.Serializable;

import pl.edu.agh.fridgeapp.fridge.User;

public class FridgeItem implements Serializable{

    private String name;
    private ExpiryDate expiryDate;
    private String description;
    private double price;
    private int quantity;
    private ItemCategory category;
    private User owner;
    private User buyer;

    public FridgeItem(String name, ExpiryDate expiryDate, String description, double price, int quantity, ItemCategory category, User owner, User buyer) {
        this.name = name;
        this.expiryDate = expiryDate;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.owner = owner;
        this.buyer = buyer;
    }


    public String getName() {
        return name;
    }

    public ExpiryDate getExpiryDate() {
        return expiryDate;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ItemCategory getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public User getOwner() {
        return owner;
    }

    public User getBuyer() {
        return buyer;
    }
}
