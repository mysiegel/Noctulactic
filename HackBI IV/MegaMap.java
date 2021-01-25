package spacegame;

import java.util.Random;

public class MegaMap {
    // IMPORTANT: MAKE PROTECTED LATER
    protected Screen screen;

    protected Map universe;
    protected Map[] galaxies;
    protected Map[][] systems;

    public Planet[][][] planets;

    // 1 universe
    // 2 galaxies per universe
    // 7 systems per galaxy
    // 4 planets per system

    public MegaMap(int x, int y) {
        screen = new Screen(x, y);

        universe = new Map(1, x, y);
        galaxies = new Map[universe.coords.size()];
        systems = new Map[galaxies.length][];
        planets = new Planet[galaxies.length][][];

        for(int i = 0; i < galaxies.length; i++) {
            galaxies[i] = new Map(2, x, y);

            systems[i] = new Map[galaxies[i].coords.size()];
            planets[i] = new Planet[systems[i].length][];

            for(int j = 0; j < systems[i].length; j++) {
                systems[i][j] = new Map(3, x, y);

                planets[i][j] = new Planet[systems[i][j].coords.size()];

                for(int k = 0; k < systems[i][j].coords.size(); k++) {
                    planets[i][j][k] = new Planet(systems[i][j].coords.get(k).x, systems[i][j].coords.get(k).y, galaxies[i], systems[i][j]);

                }
            }
        }
    }

    //Overloaded update methods to keep stuff protected ig
    public void update(){

        screen.update(universe);

    }
    //set galaxy
    public void update(int num){

        if(num == -1){
            update();
            return;
        }

        screen.update(galaxies[num]);
        
    }
    //set system
    public void update(int n1, int n2){

        if(n2 == -1){
            update(n1);
            return;
        }

        screen.update(systems[n1][n2]);
        
    }
    //set map
    public void update(Map m){

        screen.update(m);
        
    }
    //set menu
    public void update(Menu m){

        screen.update(m);
        
    }

    public void printScreen(){

        screen.print();
        
    }

    public Planet getPlanet(int i, int j, int k) {
        return planets[i][j][k]; 
    }

    // dev function
    public String checkFrequencies() {
        int[] totals = new int[3];
        int total = 0;

        for(int i = 0; i < planets.length; i++) {
            for(int j = 0; j < planets[i].length; j++) {
                for(int k = 0; k < planets[i][j].length; k++) {
                    totals[planets[i][j][k].planetarySize - 1]++;
                    total++;
                }
            }
        }

        float[] freqs = new float[3];
        String str = "";
        for(int i = 0; i < freqs.length; i++) {
            freqs[i] = (float) totals[i] / total;
            str += i + ": " + freqs[i] + "\n";
        }

        return str;
    }
}