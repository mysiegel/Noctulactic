package spacegame;

public abstract class Building {
    protected String name;
    protected Planet location;

    // create new building given name and location
    public Building(String n, Planet l) {
        name = n;
        location = l;
    }

    public abstract void onBuild();

    @Override
    public String toString() {
        return name;
    }
}