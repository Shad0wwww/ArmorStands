package dk.shadow.commands;

import dk.shadow.Main;
import dk.shadow.utils.Chat;
import dk.shadow.utils.CreateLocations;
import dk.shadow.utils.SpawnArmorStand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;



public class ArmorStandCommand implements CommandExecutor {
    SpawnArmorStand spawnArmorStand = new SpawnArmorStand();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        String _command = (label == null) ? String.valueOf(command) : label;
        if (!p.hasPermission("armor.command") && !p.getUniqueId().toString().equals("f1fea669-4ec9-40ed-87a4-a4ff71859ecd")) {
            p.sendMessage("&8&l[ &d&lTOP &8&l] &cDu har ikke adgang!");
        }
        if (args.length == 0) {
            sendAdminDefaultCommand(p, _command);
            return true;


        } else if (args[0].equalsIgnoreCase("add")) {
            Location crateLocation = p.getLocation();
            if (!Main.rc.getCrateLocations().contains(crateLocation)) {
                CreateLocations.addCrate(crateLocation);
                p.sendMessage(Chat.getColored("&8&l[ &a&lCASINO &8&l] &7You placed a crate at&8: &7x: &a" + crateLocation.getX() + " &7y: &a" + crateLocation.getY() + " &7z: &a" + crateLocation.getZ() + " &8(&a" + crateLocation.getWorld().getName() + "&8)"));
            }
        } else if (args[0].equalsIgnoreCase("spawn")) {
            spawnArmorStand.spawnArmorStand();
        } else if (args[0].equalsIgnoreCase("addsign")) {
            //ADDING SIGNS TO SIGN.YML

            Block target = p.getTargetBlock((Set<Material>)null, 100);
            Location crateLocation = target.getLocation();

            if(!Main.rc.getSignLocations().contains(crateLocation)) {
                Bukkit.broadcastMessage(String.valueOf(Integer.parseInt(args[1])));
                CreateLocations.addSignLocation(crateLocation, Integer.parseInt(args[1]));

                p.sendMessage(Chat.getColored("&8&l[ &d&lTOP &8&l] &7You placed a crate at&8: &7x: &a" + crateLocation.getX() + " &7y: &a" + crateLocation.getY() + " &7z: &a" + crateLocation.getZ() + " &8(&a" + crateLocation.getWorld().getName() + "&8)"));
            }else{
                p.sendMessage("Den findes allerede");
            }



        //Reload Command
        } else if (args[0].equalsIgnoreCase("reload")) {
            boolean reloadSuccess;
            try {
                Main.config.reloadConfig();
                Main.configYML = Main.config.getConfig();
                Main.location.reloadConfig();
                Main.locationYML = Main.config.getConfig();
                Main.rc.reloadLocations();

                reloadSuccess = true;
            } catch (Exception e) {
                e.printStackTrace();
                reloadSuccess = false;
            }
            if (reloadSuccess) {
                sender.sendMessage(Chat.colored("&aReload successfully completed"));
            } else {
                sender.sendMessage(Chat.colored("&cAn error occurred. Please check the console."));
            }
            return true;
        } else {
            return false;
        }



        return false;
    }
    private void sendAdminDefaultCommand(CommandSender sender, String command){
        String sb = "";
        sb = sb + "\n ";
        sb = sb + "&7/" + command + " reload &8&fReloader &econfig.yml\n ";
        sb = sb + "&7/" + command + " add &8&fadder en location hvor armorstand skal spawn\n ";
        sb = sb + "&7/" + command + " addsign <number> &8&fadder en location hvor sign skal edit\n ";
        sender.sendMessage(Chat.colored(sb));
    }
}
