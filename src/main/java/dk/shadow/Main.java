package dk.shadow;

import dk.shadow.commands.ArmorStandCommand;
import dk.shadow.commands.ArmorStandComplete;
import dk.shadow.commands.EntityDamage;
import dk.shadow.commands.InteractEvent;
import dk.shadow.task.UpdateArmorStand;
import dk.shadow.utils.Config;
import dk.shadow.utils.CreateLocations;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {
    private static PluginManager pluginManager;
    public static Main instance;
    public static Config config, location, sign;
    public static FileConfiguration configYML, locationYML, signYML;
    public static CreateLocations rc;
    public static Economy econ = null;
    private static List<ArmorStand> armorStandList = new ArrayList<>();

    public static List<ArmorStand> getArmorStandList() {
        return armorStandList;
    }

    @Override
    public void onEnable() {
        pluginManager = getServer().getPluginManager();
        instance = this;
        getCommand("armor").setExecutor(new ArmorStandCommand());
        getCommand("armor").setTabCompleter(new ArmorStandComplete());
        pluginManager.registerEvents(new InteractEvent(), this);
        pluginManager.registerEvents(new EntityDamage(), this);
        //Config.yml
        if (!(new File(getDataFolder(), "config.yml")).exists())
            saveResource("config.yml", false);

        config = new Config(this, null, "config.yml");
        configYML = config.getConfig();

        if (!(new File(getDataFolder(), "location.yml")).exists())
            saveResource("location.yml", false);

        location = new Config(this, null, "location.yml");
        locationYML = location.getConfig();

        if (!(new File(getDataFolder(), "sign.yml")).exists())
            saveResource("sign.yml", false);

        sign = new Config(this, null, "sign.yml");
        signYML = sign.getConfig();


        if (!setupEconomy() ) {
            Bukkit.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            Bukkit.getLogger().severe(String.format(String.valueOf(getServer().getPluginManager().getPlugin("Vault"))));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        setupEconomy();

        //Register Events
        UpdateArmorStand updateArmorStand = new UpdateArmorStand();
        updateArmorStand.runTaskTimer(instance, 100L, config.getConfig().getInt("updatere-delay") * 20L);

        //Register Prizes
        rc = new CreateLocations();
        rc.reloadLocations();
        rc.reloadSignLocations();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }


    public static Main getInstance(){
        return instance;
    }
}
