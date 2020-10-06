package net.venikt.umfrage.events;

import net.md_5.bungee.api.chat.TextComponent;
import net.venikt.umfrage.Umfrage;
import net.venikt.umfrage.sql.BanManager;
import net.venikt.umfrage.sql.MySQL;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.HashMap;
import java.util.Map;

public class PlayerJoinLoginListener implements Listener {

    @EventHandler
    public void onPlayerLoginEvent(PlayerLoginEvent e){
        String uuid = e.getPlayer().getUniqueId().toString();
        if(BanManager.isBanned(uuid)){
            long current = System.currentTimeMillis();
            long end = BanManager.getEnd(uuid);
            if(!(end < current) || end == -1) {
                e.disallow(PlayerLoginEvent.Result.KICK_BANNED, "§7_________________________________\n" +
                        "\n" +
                        "        §cDu wurdest §cgevotekickt\n" +
                        "\n" +
                        "        §7Verbleibende Zeit §8» §e" + BanManager.getReamainingTime(uuid) + "\n" +
                        "\n" +
                        "\n" +
                        "§7§7_________________________________");
            } else{
                Map<Integer, String> paramater = new HashMap<>();
                paramater.put(1, uuid);
                Umfrage.getMySQL().executeQuery("DELETE FROM votekick WHERE UUID= ?", paramater);
            }
        }
    }

}
