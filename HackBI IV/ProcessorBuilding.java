package spacegame;

public abstract class ProcessorBuilding extends Building {
    public ProcessorBuilding(String n, Planet l) {
        super(n, l);
    }

    abstract public void process();
}