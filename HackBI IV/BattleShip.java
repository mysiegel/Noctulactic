package spacegame;

public class BattleShip extends Ship 
{
    
    /* Speed is a measurement of how many units a ship can go in the map
    before losing a unit of fuel. Value can be updated when amount of fuel is finalized.*/
    private static final int fuelCapacity = 150; 

    public BattleShip(int fuel, int weight)
    {
        super(fuel, fuelCapacity, weight / (fuelCapacity * 150));
    }
}    


