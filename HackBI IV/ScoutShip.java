package spacegame; 

public class ScoutShip extends Ship
{
    /* Speed is a measurement of how many units a ship can go in the map
    before losing a unit of fuel. Value can be updated when amount of fuel is finalized.*/    
    private static final int fuelCapacity = 100; 

    public ScoutShip(int fuel, int weight)
    {
        super(fuel, weight, weight / (fuelCapacity * 100));
    }

}
