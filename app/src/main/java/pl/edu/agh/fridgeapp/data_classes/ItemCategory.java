package pl.edu.agh.fridgeapp.data_classes;

public enum ItemCategory {

    DIARY("Diary"),MEAT("Meat"),VEGETABLE("Vegetable"),FRUITS("Fruits"), MISCELLANEOUS("Miscellaneous");

    private final String name;

    ItemCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
