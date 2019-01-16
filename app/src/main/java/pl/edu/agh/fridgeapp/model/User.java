package pl.edu.agh.fridgeapp.model;

import java.io.Serializable;

public class User implements Serializable {

    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User){
            return ((User) obj).name.equals(this.name);
        }
        return false;
    }
}
