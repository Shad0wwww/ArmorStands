package dk.shadow.task;

import dk.shadow.Main;
import dk.shadow.utils.Chat;
import dk.shadow.utils.SpawnArmorStand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class UpdateArmorStand extends BukkitRunnable {
    SpawnArmorStand spawnArmorStand = new SpawnArmorStand();
    private boolean stop = false;
    @Override
    public void run() {
        if (stop) {
            cancel();
            return;
        }

        Bukkit.broadcastMessage(Chat.colored(Main.config.getConfig().getString("updatere-besked")));
        spawnArmorStand.spawnArmorStand();
    }
}
