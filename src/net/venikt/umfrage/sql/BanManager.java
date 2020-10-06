package net.venikt.umfrage.sql;

import net.venikt.umfrage.Umfrage;
import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class BanManager {

    public void votekick(String uuid, int seconds, String name){
        long end = 0;
        if (seconds == -1) {
            end = -1;
        } else {
            long current = System.currentTimeMillis();
            long millis = seconds * 1000;
            end = current + millis;
        }
        Map<Integer, String> paramater = new HashMap<>();
        paramater.put(1, uuid);
        paramater.put(2, String.valueOf(end));
        Umfrage.getMySQL().executeQuery("INSERT INTO votekick (UUID, End) VALUES (?,?)", paramater);
        if (Bukkit.getPlayer(name) != null){
            Bukkit.getPlayer(name).kickPlayer("§7_________________________________\n" +
                    "\n" +
                    "        §cDu wurdest §c§lge VOTEKICKT\n" +
                    "\n" +
                    "§7§7_________________________________");
        }
    }

    @SuppressWarnings(value = "deprecation")

    public static void unban(String uuid) {
        Map<Integer, String> paramater = new HashMap<>();
        paramater.put(1, uuid);
        Umfrage.getMySQL().executeQuery("DELETE FROM `votekick` WHERE UUID= ?", paramater);
    }

    public static boolean isBanned(String uuid) {
        Map<Integer, String> paramater = new HashMap<>();
        paramater.put(1, uuid);
        ResultSet rs = Umfrage.getMySQL().executeQuery("SELECT * FROM votekick WHERE UUID = ?", paramater);
        return true;
    }


    public static long getEnd(String uuid) {
        Map<Integer, String> paramater = new HashMap<>();
        paramater.put(1, uuid);
        ResultSet rs = Umfrage.getMySQL().executeQuery("SELECT * FROM votekick WHERE UUID= ?", paramater);
        try {
            if (rs.next()) {
                return rs.getLong("End");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }



    public static String getReamainingTime(String uuid) {
        long current = System.currentTimeMillis();
        long end = BanManager.getEnd(uuid);
        long millis = end - current;

        if (end == -1) {
            return "§4§lPERMANENT";
        }
        int seconds = 0;
        int minutes = 0;

        while (millis > 1000) {
            millis -= 1000;
            ++seconds;
        }
        while (seconds > 60) {
            seconds -= 60;
            ++minutes;
        }
        return "§e" + minutes + " Minute(n) " + seconds + " Sekunde(n) ";
    }
}
