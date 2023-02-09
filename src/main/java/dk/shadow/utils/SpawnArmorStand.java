package dk.shadow.utils;

import dk.nydt.commands.OntimeTop;
import dk.nydt.utils.FormatTime;
import dk.nydt.utils.TimeUtils;
import dk.shadow.Main;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class SpawnArmorStand {
    private Location loc;
    private Location newloc;

    public void spawnArmorStand(Player player) {

        for (Entity ent : player.getWorld().getEntities()){
            if(ent instanceof ArmorStand) {
                if (ent.getCustomName().contains("1") && (ent.getCustomName().contains("2") && (ent.getCustomName().contains("3") && (ent.getCustomName().contains("4") && (ent.getCustomName().contains("5")))))) {
                    Main.getArmorStandList().remove((ArmorStand) ent);
                    ent.remove();
                }
            }
        }


        OntimeTop ontimeTop = new OntimeTop();
        String[] top = ontimeTop.getTop5();
        FormatTime formatTime = new FormatTime();




        int i = 0;

        for (String id : Main.locationYML.getConfigurationSection("Spawn.armorstand").getKeys(false)) {




            double x = Main.locationYML.getDouble("Spawn.armorstand." + id + ".x");
            double y = Main.locationYML.getDouble("Spawn.armorstand." + id + ".y");
            double z = Main.locationYML.getDouble("Spawn.armorstand." + id + ".z");


            World w = Bukkit.getServer().getWorld(Main.config.getConfig().getString("World", "World"));
            loc = new Location(w, x, y, z);

            double xx = Main.signYML.getDouble("Sign.sign." + id + ".x");
            double yy = Main.signYML.getDouble("Sign.sign." + id + ".y");
            double zz = Main.signYML.getDouble("Sign.sign." + id + ".z");

            newloc = new Location(w, xx, yy, zz);

            int score = dk.nydt.Main.ontimeYML.getInt("Accounts." + top[i]);
            OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString(top[i]));

            ArmorStand armorStand = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
            Main.getArmorStandList().add(armorStand);
            armorStand.setGravity(false);
            armorStand.setSmall(true);
            armorStand.setVisible(true);
            armorStand.setCustomNameVisible(true);
            armorStand.setCustomName(Chat.colored( "&8&l[&a#" + (Math.addExact(i, 1)) + "&8&l]" +  "&f " + p.getName().toUpperCase()));

            ItemStack head = SkullCreator.itemFromName(p.getName());
            armorStand.setHelmet(head);
            armorStand.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
            armorStand.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
            armorStand.setBoots(new ItemStack(Material.LEATHER_BOOTS));
            armorStand.setArms(true);




            Block block = newloc.getBlock();

            if (block.getState() instanceof Sign) {
                Sign sign = (Sign) block.getState();

                sign.setLine(0, Chat.colored("&d&lONTIME"));
                sign.setLine(1, Chat.colored("&7&o(" + p.getName() + "&7)"));
                sign.setLine(2, Chat.colored(" "));
                sign.setLine(3, Chat.colored("&7" + formatTime.calculateTime(score)));
                sign.update();
            }

            i++;
            if (i == 5) {
                break;
            }
        }




    }

}
