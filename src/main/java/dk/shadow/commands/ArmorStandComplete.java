package dk.shadow.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.List;

public class ArmorStandComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        if (command.getName().equalsIgnoreCase("armor")) {
            List<String> commands = Arrays.asList("add", "spawn", "addsign");
            return commands;
        }
        return null;
    }
}
