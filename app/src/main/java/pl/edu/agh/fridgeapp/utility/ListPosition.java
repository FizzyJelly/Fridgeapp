package pl.edu.agh.fridgeapp.utility;

public class ListPosition {
    public final int group;
    public final int child;

    public ListPosition(int group, int child) {
        this.group = group;
        this.child = child;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ListPosition) {
            if (((ListPosition) obj).group == this.group && ((ListPosition) obj).child == this.child) {
                return true;
            }

            return false;
        } else {
            return false;
        }
    }
}
