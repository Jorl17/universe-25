package universe25.Agents;

import universe25.Agents.Pheromones.Pheromone;
import universe25.Agents.Regions.Hive;
import universe25.Agents.Regions.Region;
import universe25.World.World;

import java.util.ArrayList;

/**
 * Created by jorl17 on 17/08/15.
 */
public abstract class Species<T>/*<P extends SpeciesParameters>*/ {
    private String name;
    private static ArrayList<Pheromone> allPheromones = new ArrayList<>();
    private static ArrayList<Region> allRegions = new ArrayList<>();
    private ArrayList<Pheromone> pheromones = new ArrayList<>();
    private ArrayList<Region> regions = new ArrayList<>();

    private World world;
    private Hive<Species> hive;
    private int initialHiveWidth, initialHiveHeight;
    //private P parameters;

    public Species(String name,/*, P parameters*/int initialHiveWidth, int initialHiveHeight) {
        this.name = name;
        //this.parameters = parameters;
        this.initialHiveWidth = initialHiveWidth;
        this.initialHiveHeight = initialHiveHeight;
    }

    public Species(String name/*, P parameters*/) {
        this(name, 10, 10);
    }

    public abstract T newIndividual();

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

    public void initialize(World world) {
        this.world = world;
        this.hive = new Hive<>(world.getRegionsLayer(), this, initialHiveWidth, initialHiveHeight);
        hive.putInLayer();
    }

    public static ArrayList<Region> getAllRegions() {
        return allRegions;
    }

    public static ArrayList<Pheromone> getAllPheromones() {
        return allPheromones;
    }

    public Hive<Species> getHive() {
        return hive;
    }

    /*public P getParameters() {
        return parameters;
    }

    public SpeciesParameter getParameter(String name) {
        return parameters.get(name);
    }*/
}
