package pl.edu.agh.fridgeapp.model;

import java.io.Serializable;

public class FridgeItem implements Serializable{

    private String name;
    private Date addedDate;
    private Date expiryDate;
    private String description;
    private double price;
    private int quantity;
    private ItemCategory category;
    private User owner;
    private User buyer;

    public FridgeItem(String name,Date addDate, Date expiryDate, String description, double price, int quantity, ItemCategory category, User owner, User buyer) {
        this.name = name;
        this.addedDate=addDate;
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

    public Date getExpiryDate() {
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

    public Date getAddedDate() {
        return addedDate;
    }
}
