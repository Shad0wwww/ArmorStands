package dk.shadow.utils;

import dk.shadow.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;

public class CreateLocations {
    private final ArrayList<Location> crateLocations = new ArrayList<>();
    private final ArrayList<Location> signLocations = new ArrayList<>();

    public void reloadLocations(){
        crateLocations.clear();
        try{
            for (String id : Main.locationYML.getConfigurationSection("Spawn.armorstand").getKeys(false)) {
                double x = Main.locationYML.getDouble("Spawn.armorstand." + id + ".x");
                double y = Main.locationYML.getDouble("Spawn.armorstand." + id + ".y");
                double z = Main.locationYML.getDouble("Spawn.armorstand." + id + ".z");
                String w = Main.locationYML.getString("Spawn.armorstand." + id + ".world");
                World world = Bukkit.getWorld(w);
                crateLocations.add(new Location(world, x, y, z));
            }
        } catch(NullPointerException exception){
            Bukkit.getConsoleSender().sendMessage("[armorstands] No Locations found! Create some crates!");
        }
    }


    public void reloadSignLocations(){
        signLocations.clear();
        try{
            for (String id : Main.signYML.getConfigurationSection("Sign").getKeys(false)) {
                double x = Main.signYML.getDouble("Sign.sign." + id + ".x");
                double y = Main.signYML.getDouble("Sign.sign." + id + ".y");
                double z = Main.signYML.getDouble("Sign.sign." + id + ".z");
                String w = Main.signYML.getString("Sign.sign." + id + ".world");

                World world = Bukkit.getWorld("World");
                signLocations.add(new Location(world, x, y, z));
            }
        } catch(NullPointerException exception){
            Bukkit.getConsoleSender().sendMessage("[armorstands] No Locations found! Create some crates!");
        }
    }


    public static void addCrate(Location loc){
        int n = 0;
        n = Main.locationYML.getInt("Spawns");
        n++;
        Main.locationYML.set("Spawns", n);
        Main.locationYML.set("Spawn.armorstand." + n + ".world", loc.getWorld().getName());
        Main.locationYML.set("Spawn.armorstand." + n + ".x", loc.getX());
        Main.locationYML.set("Spawn.armorstand." + n + ".y", loc.getY());
        Main.locationYML.set("Spawn.armorstand." + n + ".z", loc.getZ());
        Main.location.saveConfig();
        Main.rc.reloadLocations();
    }

    public static void addSignLocation(Location loc, Integer n){
        Main.signYML.set("Signs", n);
        Main.signYML.set("Sign.sign." + n + ".world", loc.getWorld().getName());
        Main.signYML.set("Sign.sign." + n + ".x", loc.getX());
        Main.signYML.set("Sign.sign." + n + ".y", loc.getY());
        Main.signYML.set("Sign.sign." + n + ".z", loc.getZ());
        Main.sign.saveConfig();
        Main.rc.reloadSignLocations();
    }

    public ArrayList<Location> getCrateLocations() {
        return crateLocations;
    }

    public ArrayList<Location> getSignLocations() {
        return signLocations;
    }



}
