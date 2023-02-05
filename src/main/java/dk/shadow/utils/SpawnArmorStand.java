package dk.shadow.utils;

import dk.shadow.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SpawnArmorStand {
    private Location loc;

    public void spawnArmorStand(Player player) {

        for (Entity ent : player.getWorld().getEntities()){
            if(ent instanceof ArmorStand) {
                Main.getArmorStandList().remove((ArmorStand) ent);
                ent.remove();
            }
        }

        List<String> playerNames = new ArrayList<>();

        Bukkit.broadcastMessage(String.valueOf(playerNames.toArray().length));
        Collections.sort(playerNames);

        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            playerNames.add(p.getName());
        }
        int i = 0;

        for (String id : Main.locationYML.getConfigurationSection("Spawn.armorstand").getKeys(false)) {
            double x = Main.locationYML.getDouble("Spawn.armorstand." + id + ".x");
            double y = Main.locationYML.getDouble("Spawn.armorstand." + id + ".y");
            double z = Main.locationYML.getDouble("Spawn.armorstand." + id + ".z");
            World w = player.getWorld();
            loc = new Location(w, x, y, z);
            ArmorStand armorStand = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);


            Main.getArmorStandList().add(armorStand);

            armorStand.setGravity(false);
            armorStand.setSmall(true);
            armorStand.setVisible(true);
            //ADDING SETTTING THE CUSTOM NAME TO TRUE
            armorStand.setCustomNameVisible(true);
            //ADDING NAME
            armorStand.setCustomName(playerNames.get(i));
            //ADDING ITEMS
            ItemStack head = SkullCreator.itemFromName(playerNames.get(i));
            armorStand.setHelmet(head);
            armorStand.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
            armorStand.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
            armorStand.setBoots(new ItemStack(Material.LEATHER_BOOTS));
            //EDITING SIGN

            Location newloc = loc;
            newloc.add(0, -1, 0.5);
            Block block = newloc.getBlock();
            Player newplayer = Bukkit.getPlayer(playerNames.get(i));

            long timeElapsed = System.currentTimeMillis() - newplayer.getFirstPlayed();
            long hoursElapsed = TimeUnit.MILLISECONDS.toHours(timeElapsed);

            if (block.getState() instanceof Sign) {
                Sign sign = (Sign) block.getState();
                sign.setLine(0, Chat.colored("&c" + playerNames.get(i)));
                sign.setLine(1, Chat.colored("&a" + hoursElapsed));
                sign.setLine(2, "");
                sign.update();
            }


            i++;

            if (i == playerNames.size()) {
                break;
            }


        }
    }

}
