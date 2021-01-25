package spacegame;

import java.util.ArrayList;
import java.util.Random;

public class Map{
	//level 1 - galaxies
	//level 2 - solar systems
	//level 3 - planets
	private int zoom, length, width;
	protected ArrayList<Coords> coords;
	protected char[][] screen;
	
	protected Map[] children;

	public Map(int zoom, int x, int y){

		this.zoom = zoom;
		screen = new char[y - 3][x];
		length = y - 3;
		width = x;
		
		for(int i = 0; i < screen.length; i++) {
			for(int j = 0; j < screen[i].length; j++) {
				screen[i][j] = ' ';
			}
		}
		
		init();
	}

	protected class Coords{
		int x, y;

		protected Coords(int x, int y){

			this.x = x;
			this.y = y;

		}

	}

	public void init() {
		int area = length * width;
		Random rand = new Random();
		
		int suggX = 0, suggY = 0;

		coords = new ArrayList<Coords>();
		Coords c;


		switch(zoom){

			case 1:

				outerloop:
				for(int i = 0; i < area / 225; i++){

					suggX = rand.nextInt(width - 4);
					suggY = rand.nextInt(length - 2);

					for(int j = 0; j < coords.size(); j++){

						c = coords.get(j);

						if((suggX < c.x + 7 &&
						   suggX > c.x - 7) &&
						   (suggY < c.y + 5 &&
						   suggY > c.y - 3) ||
						   (suggX + 5 > width) || (suggY + 3 > length)){

							i--;
							continue outerloop;

						}

					}

					coords.add(new Coords(suggX, suggY));

				}

				break;

			case 2:

				outerloop:
				for(int i = 0; i < area / 64; i++){

					suggX = rand.nextInt(width - 2);
					suggY = rand.nextInt(length);

					for(int j = 0; j < coords.size(); j++){
						c = coords.get(j);

						if((suggX < c.x + 5 &&
						   suggX > c.x - 5) &&
						   (suggY < c.y + 3 &&
						   suggY > c.y - 2) ||
						   (suggX + 4 > width) || (suggY + 1 > length)){

							i--;
							continue outerloop;

						}

					}

					coords.add(new Coords(suggX, suggY));

				}

				break;

			case 3:

				outerloop:
				for(int i = 0; i < area / 81; i++){

					suggX = rand.nextInt(width - 2);
					suggY = rand.nextInt(length - 1);

					for(int j = 0; j < coords.size(); j++){

						c = coords.get(j);

						if((suggX < c.x + 6 &&
						   suggX > c.x - 6) &&
						   (suggY < c.y + 5 &&
						   suggY > c.y - 4) ||
						   (suggX + 4 > width) || (suggY + 1 > length)){

							i--;
							continue outerloop;

						}

					}

					coords.add(new Coords(suggX, suggY));

				}

		}
		
		format();

	}

	private void format(){
		char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

		Coords c;

		switch(zoom){

			case 1:

				for(int i = 0; i < coords.size(); i++){
					c = coords.get(i);
					
					screen[c.y][c.x] = '/';
					screen[c.y][c.x + 2] = '|';
					screen[c.y][c.x + 4] = '\\';

					screen[c.y + 1][c.x] = '-';
					screen[c.y + 1][c.x + 2] = alphabet[i];
					screen[c.y + 1][c.x+ 4] = '-';

					screen[c.y + 2][c.x] = '\\';
					screen[c.y + 2][c.x + 2] = '|';
					screen[c.y + 2][c.x + 4] = '/';
					

				}

				break;

			case 2:

				for(int i = 0; i < coords.size(); i++){
					c = coords.get(i);

					screen[c.y][c.x] = '[';
					screen[c.y][c.x + 1] = alphabet[i];
					screen[c.y][c.x + 2] = ']';

				}

				break;

			case 3:

				for(int i = 0; i < coords.size(); i++){
					c = coords.get(i);

					screen[c.y][c.x] = alphabet[i];
					screen[c.y][c.x + 1] = alphabet[i];
					screen[c.y][c.x + 2] = alphabet[i];

					screen[c.y + 1][c.x] = alphabet[i];
					screen[c.y + 1][c.x + 1] = alphabet[i];
					screen[c.y + 1][c.x + 2] = alphabet[i];

				}

		}

	}

	public char[][] getScreen(){

		return screen;

	}

}

/*

PLANET - Only visible on zoom level3
aaa
aaa    

SOLAR SYSTEM - Only visible on zoom level2
[o]

GALAXY - Only visible on zoom level1
/ | \
- @ -
\ | /

*/
