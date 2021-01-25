package spacegame;

import java.util.Random;
import java.util.HashMap;
import java.util.ArrayList;

public class Planet{
	//ore - raw materials
	//nat - organic material/food (natural)
	//      must be < water
	//water - water
	//pop - population
	//hab - habitability (calc'd in habCalc())
	//   ALL VALUES SHOULD BE < 100 and > 0
	private int ore;
	private float nat, water;
	public float pop;
	private float hab;
	
	// IMPORTANT: CHANGE TO PROTECTED LATER
	public float growthModifier;
	public int housing;

	// IMPORTANT: CHANGE TO PROTECTED LATER
	public Stockpile stockpile;
	
	protected HashMap<String, Integer> resources;

	private String name;
	
	// position in universe
	protected Map galaxy, system;
	protected int x, y;

	// IMPORTANT: CHANGE TO PROTECTED LATER
	public Player controller;

	// buildings on planet- can only construct a certain amount of buildings
	// null indicates empty slot for building
	private Building[] buildings;

	public int planetarySize;
	
	//Only for testing
	public Planet(float nat, float water, String name, int x, int y, Map galaxy, Map system){
		this.ore = ore;
		this.nat = nat;
		this.water = water;
		this.name = name;
		pop = 0;

		hab = habCalc();

		// generate random size for planet (1 - 3)
		planetarySize = (int) (Math.random() * 3) + 1;

		buildings = new Building[planetarySize * 2];

		stockpile = new Stockpile();

		this.x = x;
		this.y = y;

		this.galaxy = galaxy;
		this.system = system;

		growthModifier = 1.0f;
		housing = 0;
	}
	
	//randomised vals
	public Planet(int x, int y, Map galaxy, Map system) {
		this(new Random().nextInt(101), new Random().nextInt(101), "test", x, y, galaxy, system);
		
		Random rand = new Random();
		resources = new HashMap<String, Integer>();
		resources.put("ore", rand.nextInt(6));
		resources.put("food", rand.nextInt((int) (8 * (water * nat / 10000)) + 1) + 1);
	}

	public void colonize(Player colonizer) {
		controller = colonizer;
		pop += 1;
	}

	public int capCalc() {
		return (int) Math.pow(1.10, 100 + housing) / (4 - planetarySize);
	}

	public boolean hasShipyard() {
		for(Building b : buildings) {
			if(b != null && b.name == "Shipyard") {
				return true;
			}
		}

		return false;
	}

	public boolean canBuild() {
		int unmodifiedPlanetaryCap = (int) Math.pow(1.10, 100) / (4 - planetarySize);
		System.out.println(planetarySize + ": " + unmodifiedPlanetaryCap);

		if(pop > unmodifiedPlanetaryCap) {
			return countBuildings() < buildings.length;
		}

		return countBuildings() < (int) ((float)pop / unmodifiedPlanetaryCap * buildings.length) + 1;
	}

	public String getName() {
		return name;
	}

	//testing only
	public void setPop(int pop){
		this.pop = pop;
	}

	private float habCalc(){
		// to be fixed
		return 100 * (float) Math.sqrt(nat * water / 10000);
	}

	public String toString(){

		String ret = "Planet (" + name + ") Overview:";
		ret += "\nCONTROLLED BY: " + ((controller != null) ? controller.getName() : "NOBODY");
		ret += "\nNAT: " + nat;
		ret += "\nWATER: " + water;

		ret += "\nHABITABILITY: " + hab + "%";

		ret += "\nCURRENT POPULATION: " + pop + " (" + capCalc() + ")";

		ret += "\nBUILDINGS:\n";

		for(Building b : buildings) {
			if(b != null) {
				ret += "\t" + b.toString() + "\n";
			}
		}
		ret += "\nSTOCKPILE:\n" + stockpile.toString();

		return ret;

	}

	public String[] toStringArray(){

		ArrayList<String> ret = new ArrayList<>();

		ret.add("Planet (" + name + ") Overview:");
		ret.add("CONTROLLED BY: " + ((controller != null) ? controller.getName() : "NOBODY"));
		ret.add("NAT: " + nat);
		ret.add("WATER: " + water);

		ret.add("HABITABILITY: " + Math.round(hab * 100000) / 100000 + "%");

		ret.add("CURRENT POPULATION: " + pop);
		ret.add("RESOURCES:");

		resources.forEach((resource, amount) -> {
			ret.add("\t" + resource + ": " + amount);
		});

		ret.add("BUILDINGS:");

		for(Building b : buildings) {
			if(b != null) {
				ret.add("\t" + b.toString());
			}
		}
		ret.add("");
		ret.add("STOCKPILE:");
		ret.add(stockpile.toString());

		String[] retArr = new String[ret.size()];
		for(int i = 0; i < ret.size(); i++){

			retArr[i] = ret.get(i);

		}

		return retArr;

	}


	// returns if a planet has space for any more buildings
	public int countBuildings() {
		int t = 0;
		for(Building b : buildings) {
			if(b != null) {
				t++;
			}
		}

		return t;
	}

	// attaches building to planet
	public void attachBuilding(Building b) {
		int i = 0;
		
		while(buildings[i] != null) {
			i++;
		}

		buildings[i] = b;
		b.onBuild();
	}

	// call to evalutate all processes of buildings
	public void buildingsUpdate() {
		for(Building b : buildings) {
			// SUPER SCUFFED FIX LATER?
			if(b != null && b.getClass().getSuperclass().getSimpleName().equals("ProcessorBuilding")) {
				((ProcessorBuilding) b).process();
			}
		}
	}

	public void planetUpdate() {
		resources.forEach((resource, amount) -> {
			stockpile.modify(resource, amount);
		});

		// population growth
		float consumed = pop / 875;
		float net = stockpile.get("food") - consumed;

		if(net < 0) {
			stockpile.set("food", 0);
		} else {
			stockpile.modify("food", -(int) Math.ceil(consumed));
		}

		float new_pop = (float) (pop * (1 / (5 + 5 * Math.exp(-2 * net)) + 0.9));

		pop = new_pop;
	}

	public void setController(Player p) {
		controller = p;
	}

}

class MutableString {
	String internal;

	public MutableString() {
		this("");
	}

	public MutableString(String s) {
		internal = s;
	}

	public void add(String s) {
		internal += s;
	}

	public String toString() {
		return internal;
	}
}