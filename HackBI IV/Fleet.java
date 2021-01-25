package spacegame;

import java.util.ArrayList;

public class Fleet{
	ArrayList<Ship> ships;
	Planet start, dest;

	//ships, starting point, destination
	public Fleet(ArrayList<Ship> ships, Planet start, Planet dest){

		this.ships = ships;
		this.start = start;
		this.dest = dest;

	}

	public Fleet(Planet start, Planet dest){

		this(new ArrayList<Ship>(), start, dest);

	}

	public void addShip(Ship s){

		ships.add(s);

	}

}
