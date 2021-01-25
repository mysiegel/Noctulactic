package spacegame;

import java.util.ArrayList;

public class Player {
    // arraylist containing all fleets of player
    private ArrayList<Fleet> fleets;
    // arraylist containing all planets controlling player **this will really be pointers to instances in another arraylist
    private ArrayList<Planet> planets;
    // all logistical requests
    private ArrayList<LogisticalRequest> logisticalRequests;

    protected int fuel;

    private String name;

    public Player(String name) {
        fleets = new ArrayList<Fleet>();
        planets = new ArrayList<Planet>();
        logisticalRequests = new ArrayList<LogisticalRequest>();

        // logistical fleet
        //fleets.add(new Fleet());
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean hasShipyard() {
        for(Planet p : planets) {
            if(p.hasShipyard()) {
                return true;
            }
        }

        return false;
    }

    public Planet getPlanetWithShipyard() {
        for(Planet p : planets) {
            if(p.hasShipyard()) {
                return p;
            }
        }
        
        return null;
    }

    // dev
    public void print() {
        System.out.println("Fleets: ");
        for(Fleet f : fleets) {
            System.out.println(f);
        }

        System.out.println("Planets: ");
        for(Planet p : planets) {
            System.out.println(p);
        }

        System.out.println("Logistical Requests: ");
        for(LogisticalRequest r : logisticalRequests) {
            System.out.println(r);
        }
    }

    public void createLogisticalRequest(String resource, int quantity, Planet destination) {
        if(checkInStockpiles(resource, quantity)) {
            // TODO: optimize logistical requests by getting resources from closer planets
            // also planets will give resources if they have them- probably should make it if they have a surplus
            for(Planet p : planets) {
                if(p.stockpile.get(resource) >= quantity) {
                    logisticalRequests.add(new LogisticalRequest(p, destination, resource, quantity));
                    return;
                }
            }
        }
    }

    public boolean checkInStockpiles(String resource, int quantity) {
        int sum = 0;
        for(Planet p : planets) {
            sum += p.stockpile.get(resource);
        }

        return sum > quantity;
    }

    public void processLogisticalRequests() {
        ArrayList<LogisticalRequest> toRemove = new ArrayList<LogisticalRequest>();

        for(LogisticalRequest r : logisticalRequests) {
            switch(r.status) {
                case "PENDING": {
                    r.accept();
                    break;
                }
                case "TRANSIT": {
                    r.check();
                    break;
                }
                case "COMPLETE": {
                    toRemove.add(r);
                    break;
                }
            }
        }

        logisticalRequests.removeAll(toRemove);
    }

    public void updateFleets() {

    }

    public void updateControlledPlanets() {
        for(Planet p : planets) {
            p.planetUpdate();
            p.buildingsUpdate();
        }
    }

    public boolean started() {
        return planets.size() > 0;
    }

    // attaches planet to this player
    public void attachPlanet(Planet p) {
        planets.add(p);
    }
}

class LogisticalRequest {
    protected Planet source;
    protected Planet destination;
    protected String resource;
    protected int quantity;
    protected String status;

    public LogisticalRequest(Planet source, Planet destination, String resource, int quantity) {
        this.source = source;
        this.destination = destination;
        this.resource = resource;
        this.quantity = quantity;
        
        status = "PENDING";
    }

    public void accept() {
        source.stockpile.modify(resource, -quantity);
        status = "TRANSIT";
    }

    // check if completed
    public void check() {
        destination.stockpile.modify(resource, quantity);
        status = "COMPLETE";
    }

    public String toString() {
        return source.getName() + " --> " + destination.getName() + " (" + quantity + " " + resource + ") " + status;
    }
}