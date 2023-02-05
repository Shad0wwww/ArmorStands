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

import java.util.*;
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
        Map<String, Long> playerHours = new HashMap<>();

        Bukkit.broadcastMessage(String.valueOf(playerNames.toArray().length));
        Collections.sort(playerNames);



        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            playerNames.add(p.getName());
            long timeElapsed = System.currentTimeMillis() - p.getFirstPlayed();
            long hoursElapsed = TimeUnit.MILLISECONDS.toHours(timeElapsed);
            playerHours.put(p.getName(), hoursElapsed);
        }

        List<Map.Entry<String, Long>> sortedPlayerHours = new ArrayList<>(playerHours.entrySet());
        sortedPlayerHours.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));


        int i = 1;



        for (Map.Entry<String, Long> entry : sortedPlayerHours) {
            String playerName = entry.getKey();
            long hoursElapsed = entry.getValue();

            double x = Main.locationYML.getDouble("Spawn.armorstand." + i + ".x");
            double y = Main.locationYML.getDouble("Spawn.armorstand." + i + ".y");
            double z = Main.locationYML.getDouble("Spawn.armorstand." + i + ".z");
            World w = player.getWorld();
            loc = new Location(w, x, y, z);

            ArmorStand armorStand = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
            Main.getArmorStandList().add(armorStand);
            armorStand.setGravity(false);
            armorStand.setSmall(true);
            armorStand.setVisible(true);
            armorStand.setCustomNameVisible(true);
            armorStand.setCustomName(playerName);
            ItemStack head = SkullCreator.itemFromName(playerName);
            armorStand.setHelmet(head);
            armorStand.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
            armorStand.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
            armorStand.setBoots(new ItemStack(Material.LEATHER_BOOTS));
            Location newloc = loc;
            newloc.add(0, -1, 0.5);
            Block block = newloc.getBlock();
            Player newplayer = Bukkit.getPlayer(playerName);
            if (block.getState() instanceof Sign) {
                Sign sign = (Sign) block.getState();
                sign.setLine(0, Chat.colored("&c" + playerName));
                sign.setLine(1, Chat.colored("&a" + hoursElapsed));
                sign.setLine(2, "");
                sign.update();
            }
            i++;
            if (i == sortedPlayerHours.size()) {
                break;
            }
        }




    }

}
