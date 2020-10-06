package net.venikt.umfrage.utils;

import net.venikt.umfrage.Data;
import net.venikt.umfrage.Umfrage;
import net.venikt.umfrage.commands.StartKickCommand;
import net.venikt.umfrage.sql.BanManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Countdown {

    public static Integer countdown = 31;

    public static void startCountdown(Player target){
        new BukkitRunnable() {

            @Override
            public void run() {
                countdown--;
                if((countdown == 30) || (countdown == 15) || (countdown == 10) || (countdown == 5)){
                    for(Player all : Bukkit.getOnlinePlayers()){
                        all.sendMessage(Data.getPrefix() + "Der Votekick endet in §e" + countdown  + "§7 Sekunden");
                    }
                } else if(countdown == 1){
                    for(Player all : Bukkit.getOnlinePlayers()){
                        all.sendMessage(Data.getPrefix() + "Der Votekick endet in §eeiner §7Sekunde");
                    }
                }

                if(countdown == 0) {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        all.sendMessage(Data.getPrefix() + "Ja Votes: §e" + StartKickCommand.jaarray.size());
                        all.sendMessage(Data.getPrefix() + "Nein Votes: §e" + StartKickCommand.neinarray.size());
                        if (StartKickCommand.jaarray.size() > StartKickCommand.neinarray.size()) {
                            all.sendMessage(Data.getPrefix() + "§cDer Spieler wurde bestraft");
                            Umfrage.getBanManager().votekick(target.getUniqueId().toString(), 300, target.getName());
                        } else {
                            all.sendMessage(Data.getPrefix() + "§aDer Spieler wurde nicht bestraft");
                        }
                        StartKickCommand.neinarray.clear();
                        StartKickCommand.jaarray.clear();
                        cancel();
                        StartKickCommand.votekick = false;
                        countdown = 31;
                    }
                }
            }
        }.runTaskTimer(Umfrage.getInstance(), 20, 20);
    }


}
