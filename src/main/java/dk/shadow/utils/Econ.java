package dk.shadow.utils;

import dk.nydt.utils.FormatTime;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

import static dk.shadow.Main.econ;

public class Econ {

    public boolean addMoney(String player, double amount) {

        return econ.depositPlayer(player, amount).transactionSuccess();
    }

    private boolean addMoneyToPlayer(String playerName, double amount) {
        return econ.depositPlayer(playerName, amount).transactionSuccess();
    }

    private boolean createPlayerAccount(String playerName) {
        return econ.createPlayerAccount(playerName);
    }

    public static double getbalance(String playerName) {
        return econ.getBalance(playerName);
    }


}