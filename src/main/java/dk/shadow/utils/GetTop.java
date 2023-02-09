package dk.shadow.utils;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class GetTop {
    public static Map<Double, String> getTopBalances(int count) {
        Economy econ = Bukkit.getServicesManager().getRegistration(Economy.class).getProvider();
        Map<Double, String> balanceMap = new LinkedHashMap<>();

        for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
            double balance = econ.getBalance(p);
            balanceMap.put(balance, p.getName());
        }

        return balanceMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                .limit(count)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
    }

    public static String formatNum(double number) {
        String minus = "";
        if (number < 0) {
            minus = "-";
            number = Math.abs(number);
        }
        int zeros = (int) (Math.log10(number));
        int n = zeros / 3;
        if (n < 1) {
            return String.format("%s%.0f", minus, number);
        } else {
            String[] abbreviations = new String[]{"", "K", "M", "B", "T"};
            return String.format("%s%.2f%s", minus, number / Math.pow(1000, n), abbreviations[n]);
        }
    }
}
