package dk.shadow.utils;


import dk.shadow.Main;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import dk.shadow.utils.Econ;

import java.util.*;

import static org.bukkit.Bukkit.getServer;


public class SpawnArmorStand {
    private Location loc;
    private Location newloc;
    private OfflinePlayer p;

    public void spawnArmorStand(Player player) {

        for (Entity ent : player.getWorld().getEntities()){
            if(ent instanceof ArmorStand) {
                Main.getArmorStandList().remove((ArmorStand) ent);
                ent.remove();

            }
        }


        int i = Main.config.getConfig().getInt("armorstandSpawnfirstNumber", 1);
        Map<Double, String> topPlayers = GetTop.getTopBalances(Main.config.getConfig().getInt("armorstandSpawn", 6));
        for (Map.Entry<Double, String> entry : topPlayers.entrySet()) {




            System.out.println(entry.getKey() + " " + entry.getValue());
            double balance = entry.getKey();
            String name = entry.getValue();
            System.out.println("name " + name + " " + topPlayers);
            p = Bukkit.getOfflinePlayer(name);


            double x = Main.locationYML.getDouble("Spawn.armorstand." + i + ".x");
            double y = Main.locationYML.getDouble("Spawn.armorstand." + i + ".y");
            double z = Main.locationYML.getDouble("Spawn.armorstand." + i + ".z");


            World w = Bukkit.getServer().getWorld(Main.config.getConfig().getString("World", "World"));
            loc = new Location(w, x, y, z);

            double xx = Main.signYML.getDouble("Sign.sign." + i + ".x");
            double yy = Main.signYML.getDouble("Sign.sign." + i + ".y");
            double zz = Main.signYML.getDouble("Sign.sign." + i + ".z");

            newloc = new Location(w, xx, yy, zz);

            //int score = dk.nydt.Main.ontimeYML.getInt("Accounts." + top[i]);

            ArmorStand armorStand = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
            Main.getArmorStandList().add(armorStand);
            armorStand.setGravity(false);
            armorStand.setSmall(true);
            armorStand.setVisible(true);
            armorStand.setCustomNameVisible(true);
            armorStand.setCustomName(Chat.colored( "&8&l[&a#" +  i + "&8&l]" +  "&f " + p.getName().toUpperCase()));

            ItemStack head = SkullCreator.itemFromName(p.getName());
            armorStand.setHelmet(head);
            armorStand.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
            armorStand.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
            armorStand.setBoots(new ItemStack(Material.LEATHER_BOOTS));
            armorStand.setArms(true);




            Block block = newloc.getBlock();

            if (block.getState() instanceof Sign) {
                Sign sign = (Sign) block.getState();
                List<String> signs = Main.config.getConfig().getStringList("Signinfo");
                for (int n = 0; n < signs.size(); n++) {

                    String message = signs.get(n);
                    message = message.replace("%balance%", GetTop.formatNum(balance));
                    message = message.replace("%player%", p.getName());

                    sign.setLine(n, Chat.colored(message));
                    sign.update();



                }


            }

            i++;
            if (i == Main.config.getConfig().getInt("armorstandSpawn", 6)) {
                break;
            }
        }




    }

    public OfflinePlayer getTopPlayer() {
        return p;
    }

}
