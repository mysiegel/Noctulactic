package spacegame;

public abstract class Ship
{
	/*  fuel stores current amount of fuel.
		weight stores the weight of the ship (used for speed calc)
		speed stores the speed of the ship
	*/
	protected int fuel, weight;
	protected final int speed;
	
	public Ship(int fuel, int weight, int speed)
	{

		this.fuel = fuel;
		this.weight = weight;
        this.speed = speed;  

	}

}
