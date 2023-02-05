package dk.shadow.commands;

import dk.shadow.Main;
import dk.shadow.utils.Chat;
import dk.shadow.utils.CreateLocations;
import dk.shadow.utils.SpawnArmorStand;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArmorStandCommand implements CommandExecutor {
    SpawnArmorStand spawnArmorStand = new SpawnArmorStand();
    //TODO add sign to yml and et gives text to them
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        String _command = (label == null) ? String.valueOf(command) : label;
        if (!p.hasPermission("armor.command")) {
            p.sendMessage("noob");
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
            spawnArmorStand.spawnArmorStand(p);
        } else if (args[0].equalsIgnoreCase("addsign")) {
            //ADDING SIGNS TO A SIGN.YML
            if (args.length == 1) {
                sender.sendMessage("lol");
            }

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
        sender.sendMessage(Chat.colored(sb));
    }
}
