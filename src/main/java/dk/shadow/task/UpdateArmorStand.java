package dk.shadow.task;

import dk.shadow.Main;
import dk.shadow.utils.Chat;
import dk.shadow.utils.SpawnArmorStand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class UpdateArmorStand extends BukkitRunnable {
    SpawnArmorStand armorStand = new SpawnArmorStand();
    @Override
    public void run() {
        Bukkit.broadcastMessage(Chat.colored(Main.config.getConfig().getString("updatere-besked")));
        Player player = Bukkit.getPlayer(Bukkit.getOnlinePlayers().iterator().next().getName());
        armorStand.spawnArmorStand(player);
    }
}
