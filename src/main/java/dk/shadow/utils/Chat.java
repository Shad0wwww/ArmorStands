package dk.shadow.utils;

import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class Chat {
    public static String colored(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String plain(String s) {
        return s.replaceAll("§", "&");

    }

    public static String getColored(String s){return ChatColor.translateAlternateColorCodes('&', s);}

}