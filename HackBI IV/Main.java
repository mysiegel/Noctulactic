import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import spacegame.MegaMap;
import spacegame.Menu;
import spacegame.HumanPlayer;
import spacegame.Planet;
import spacegame.ProcessorBuilding;
import spacegame.Building;
import spacegame.Menu;
import spacegame.Player;

public class Main{
	public static void nextTurn(Player player) {
		player.processLogisticalRequests();
		player.updateControlledPlanets();
		player.updateFleets();
	}

	public static void main(String[] args) {

		int zoom = 1;

		Scanner input = new Scanner(System.in);
		
		MegaMap map = new MegaMap(55, 20);
		System.out.print("Enter your empire's name: ");
		HumanPlayer user = new HumanPlayer(input.nextLine());
		
		Menu title = new Menu(55,20);
		String[] nameTxt = {"",
						"  _   _            _         _            _   _      ",
						" | \\ | |          | |       | |          | | (_)     ",
						" |  \\| | ___   ___| |_ _   _| | __ _  ___| |_ _  ___ ", 
						" | . ` |/ _ \\ / __| __| | | | |/ _` |/ __| __| |/ __|",
						" | |\\  | (_) | (__| |_| |_| | | (_| | (__| |_| | (__ ",
						" |_| \\_|\\___/ \\___|\\__|\\__,_|_|\\__,_|\\___|\\__|_|\\___|",
						"",
						"         A Text-Based ASCII Empire Manager!           ",
						"           Kieran Slattery and Mira Siegel            ",
						"",
						"",
						"",
						"",
						"                 Press Enter to Begin                 "};

		title.addText(nameTxt);

		map.update(title);
		map.printScreen();
		input.nextLine();

		map.update();

		int galaxy = -1;
		int system = -1;
		Planet planet = null;

		while(true) {

			map.printScreen();
			String cmd = input.nextLine();
			
			switch(cmd) {
				case "b": {
					break;
				}

				case "-": {

					switch(zoom){

						case 1:
							galaxy = -1;
							System.out.println("Cannot zoom out further");
							break;

						case 2:
							zoom--;
							system = -1;
							map.update();
							break;

						case 3:
							planet = null;
							zoom--;
							map.update(galaxy);

					}

					break;

				}

				case "+": {

					if(zoom == 3){

						System.out.println("Cannot zoom in further.");
							break;

					}

					System.out.println("Type the letter of the area you want to zoom into.");

					String alphabet = "abcdefghijklmnopqrstuvwxyz";

					// prompt for which map
					char selected = input.nextLine().charAt(0);
					int index = alphabet.indexOf(selected);

					switch(zoom){

						case 1:
							zoom++;
							galaxy = index;
							map.update(galaxy);
							break;

						case 2:
							zoom++;
							system = index;
							map.update(galaxy, system);
							break;

					}

					break;

				}

				case "select": {

					if(zoom != 3){

						System.out.println("Must be on planetary level - Zoom in more.");
						break;

					}

					System.out.println("Type the letter of the planet you want to focus on.");

					String alphabet = "abcdefghijklmnopqrstuvwxyz";

					// prompt for which map
					char selected = input.nextLine().charAt(0);
					int index = alphabet.indexOf(selected);

					planet = map.getPlanet(galaxy, system, index);

					Menu planetScreen = new Menu(55, 20);
					String[] planetInfo = planet.toStringArray();
					planetScreen.addText(planetInfo);
					
					String pCmd;

					boolean exit = false;

					while(!exit) {
						map.update(planetScreen);
						map.printScreen();

						pCmd = input.nextLine();

						switch(pCmd) {
							case "build": {
								final Planet buildReference = planet;

								Menu m = new Menu(55,20);
								String[] str = {"PLANET BUILD MENU", "1 - Forge", "2 - Farm", "3- Shipyard", "4- City", "5- Mine"};
								m.addText(str);
								map.update(m);
								map.printScreen();

								System.out.println("Type the number you want to buy or 'exit' to exit the menu.");
								String otherSelected = input.nextLine();

								Building generic;

								if(otherSelected.equals("exit")) {
									break;
								}

								if(planet.stockpile.get("alloy") >= 25) {
									planet.stockpile.modify("alloy", -25);
								} else {
									user.createLogisticalRequest("alloy", 25, planet);
									System.out.println("Not enough alloy to construct building");
									break;
								}

								if(planet.countBuildings() == planet.planetarySize * 2) {
									System.out.println("Cannot build more on this planet");
									break;
								}

								switch(otherSelected){
									case "1": {
										generic = new ProcessorBuilding("Forge", planet) {
											public void onBuild() {

											}

											public void process() {
												if(location.stockpile.get("ore") >= 2) {
													location.stockpile.modify("ore", -2);
													location.stockpile.modify("alloy", 1);
												} else {
													location.controller.createLogisticalRequest("ore", 2, location);
												}
											}
										};

										planet.attachBuilding(generic);

										break;
									}

									case "2": {
										generic = new ProcessorBuilding("Forge", planet) {
											public void onBuild() {

											}

											public void process() {
												location.stockpile.modify("food", 2);
											}
										};

										planet.attachBuilding(generic);

										break;
									}
									
									case "3": {
										generic = new Building("Shipyard", planet) {
											public void onBuild() {
											}
										};
										
										planet.attachBuilding(generic);

										break;
									}

									case "4": {
										generic = new Building("City", planet) {
											public void onBuild() {
												location.housing++;
											}
										};
										
										planet.attachBuilding(generic);

										break;
									}

									case "5": {
										generic = new ProcessorBuilding("Mine", planet) {
											public void onBuild() {

											}

											public void process() {
												location.stockpile.modify("ore", 2);
											}
										};
										
										planet.attachBuilding(generic);

										break;
									}
								}

								break;
							}

							case "colonize": {
								// check if owned planets have a shipyard
								if(!user.hasShipyard()) {
									System.out.println("You must have a shipyard to colonize other planets");
									break;
								}

								Planet source = user.getPlanetWithShipyard();
								source.pop -= 10;

								planet.colonize(user);
								planet.setPop(10);
								user.attachPlanet(planet);
								break;
							}
							
							case "start": {
								if(!user.started()) {
									System.out.println("Starting on planet " + planet.getName());
									planet.colonize(user);
									user.attachPlanet(planet);

									planet.setPop(150);
									planet.stockpile.set("alloy", 50);
									planet.stockpile.set("food", 50);
									planet.stockpile.set("fuel", 50);
								}
								break;
							}

							case "exit": {
								map.update(galaxy, system);
								exit = true;
								break;
							}
							
							case "": {
								nextTurn(user);
								break;
							}
						}

						planetScreen.addText(planet.toStringArray());
					}

					break;
				}

				case "help": {

					Menu help = new Menu(55,20);
					String[] info1 = {"  _    _      _       ",
									  " | |  | |    | |      ",
									  " | |__| | ___| |_ __  ",
									  " |  __  |/ _ \\ | '_ \\ ",
									  " | |  | |  __/ | |_) |",
									  " |_|  |_|\\___|_| .__/ ",
									  "               | |    ",
									  "               |_|    ",
									  "",
									  "BASIC COMMANDS:",
									  "\"+\" - Zooms in",
									  "\"-\" - Zooms out",
									  "\"help\" - Accesses this message!",
									  "",
									  "",
									  "Type > to navigate to the next page.",
									  "Type 'exit' to return to space!"};
					String[] info2 = {"  _    _      _       ",
									  " | |  | |    | |      ",
									  " | |__| | ___| |_ __  ",
									  " |  __  |/ _ \\ | '_ \\ ",
									  " | |  | |  __/ | |_) |",
									  " |_|  |_|\\___|_| .__/ ",
									  "               | |    ",
									  "               |_|    ",
									  "",
									  "MANAGEMENT COMMANDS:",
									  "\"select\" - Selects a planet",
									  "\"build\" - Builds on a selected planet",
									  "\"ship\" - Allows you to send a shipment to a planet",
									  "\"info\" - Shows the info from the selected planet",
									  "",
									  "Type < to navigate to the previous page.",
									  "Type 'exit' to return to space!"};

					help.addText(info1);
					String strinput = "";

					while(true){

						map.update(help);
						map.printScreen();

						strinput = input.nextLine();

						if(strinput.equals("<"))
							help.addText(info1);
						if(strinput.equals(">"))
							help.addText(info2);
						if(strinput.equals("exit")){
							map.update(galaxy, system);
							break;
						}

					}

				}

				case "": {
					nextTurn(user);
				}

			}
		}
	}


/*

					
			}

			map.printScreen();

		}
	}*/
}
