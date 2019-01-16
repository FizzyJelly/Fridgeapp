package pl.edu.agh.fridgeapp.model;

import java.io.Serializable;

public enum ItemCategory implements Serializable {

    DIARY("Diary"),MEAT("Meat"),VEGETABLE("Vegetable"),FRUITS("Fruits"), MISCELLANEOUS("Miscellaneous");

    private final String name;

    ItemCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
