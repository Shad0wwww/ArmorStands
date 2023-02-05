package dk.shadow.utils;

import dk.shadow.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;

public class CreateLocations {
    private ArrayList<Location> crateLocations = new ArrayList<>();

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

    public ArrayList<Location> getCrateLocations() {
        return crateLocations;
    }

}
