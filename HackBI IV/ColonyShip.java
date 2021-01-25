package spacegame;

public class ColonyShip extends Ship

{
	private static final int fuelCapacity = 200;
	private int population;
	
	public ColonyShip(int fuel, int weight, int population)
	{
        super(fuel, weight, weight / (fuelCapacity * 200));
        this.population = population; 

	}
	
	public void updatePopulation(int population)
	{
		this.population = population;
	}
}
