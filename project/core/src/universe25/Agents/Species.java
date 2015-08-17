package universe25.Agents;

import universe25.Agents.Pheromones.Pheromone;
import universe25.Agents.Regions.Region;

import java.util.ArrayList;

/**
 * Created by jorl17 on 17/08/15.
 */
public abstract class Species {
    private String name;
    private static ArrayList<Pheromone> allPheromones = new ArrayList<>();
    private static ArrayList<Region> allRegions = new ArrayList<>();
    private ArrayList<Pheromone> pheromones = new ArrayList<>();
    private ArrayList<Region> regions = new ArrayList<>();


    public Species(String name) {
        this.name = name;
    }

    public abstract Species newIndividual();

    public String getName() {
        return name;
    }

    public ArrayList<Pheromone> getPheromones() {
        return pheromones;
    }

    protected void addSpeciesPheromone(Pheromone p) {
        pheromones.add(p);
        allPheromones.add(p);
    }

    public ArrayList<Region> getRegions() {
        return regions;
    }

    protected void addRegion(Region r) {
        regions.add(r);
        allRegions.add(r);
    }

    public static ArrayList<Region> getAllRegions() {
        return allRegions;
    }

    public static ArrayList<Pheromone> getAllPheromones() {
        return allPheromones;
    }
}