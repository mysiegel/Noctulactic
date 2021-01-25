package spacegame;

public class Menu{
	private int length, width;
	protected char[][] screen;
	private String[] strs;
	private int choice;

	public Menu(int x, int y){

		screen = new char[y - 3][x];
		length = y - 3;
		width = x;

	}

	public void addText(String[] input){

		strs = input;
		screen = new char[length][width];

		for(int i = 0; i < length; i++) {

			for(int j = 0; j < width; j++) {

				if(i < strs.length && j < strs[i].length()){

			  	    screen[i][j] = strs[i].charAt(j);

				} else {

					screen[i][j] = ' ';
				
				}

			}

		}

	}

	public void editOption(int optNum, String newText){

		if(optNum > strs.length)
			return;

		strs[optNum] = newText;

	}

	public String getOption(int optNum){

		if(optNum > strs.length)
			return null;

		return strs[optNum];

	}

	public void choose(int choice){

		this.choice = choice;

	}

	public int getChoice(){

		return choice;

	}

	public char[][] getScreen(){

		return screen;

	}

}