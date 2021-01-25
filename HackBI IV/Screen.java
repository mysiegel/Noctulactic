package spacegame;

import java.util.Scanner;

public class Screen{
	char[][] screen;
	int x, y;
	
	public Screen(int x, int y){

		this.screen = new char[y][x];
		this.x = x;
		this.y = y;

		for(int i = 0; i < x; i++){

			screen[0][i] = '=';
			screen[y-1][i] = '=';

		}

	}

	public void print(){

		for(int i = 0; i < y; i++){

			for(int j = 0; j < x; j++){

			 	System.out.print(screen[i][j]);
			
			}

			System.out.println();

		}

	}

	public void update(Map m){
		char[][] updated = m.getScreen();

		for(int i = 1; i < y - 2; i++){

			for(int j = 0; j < x; j++){

				screen[i][j] = updated[i-1][j];

			}

		}

	}

	public void update(Menu m){
		char[][] updated = m.getScreen();

		for(int i = 1; i < y - 2; i++){

			for(int j = 0; j < x; j++){

				screen[i][j] = updated[i-1][j];

			}

		}

	}

	public static void main(String[] args){

		Scanner input = new Scanner(System.in);

		Screen d = new Screen(30,20);
		

	}

}
