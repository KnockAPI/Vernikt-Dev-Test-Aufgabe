package net.venikt.umfrage.commands;

import net.venikt.umfrage.Data;
import net.venikt.umfrage.utils.Countdown;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class StartKickCommand implements CommandExecutor {

    public static boolean votekick;

    public static ArrayList<String> jaarray = new ArrayList<>();
    public static ArrayList<String> neinarray = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player){
            Player p = (Player)sender;
            if(p.hasPermission("system.votekick")){
                if(args.length == 2){
                    Player target = Bukkit.getPlayerExact(args[0]);
                    String grund = args[1];
                    if(target != null){
                        for(Player all : Bukkit.getOnlinePlayers()){
                            if(!votekick) {
                                votekick = true;
                                all.sendMessage(Data.getPrefix() + "§7Es wurde ein Votekick gestartet");
                                all.sendMessage(Data.getPrefix() + "§7--------------------------------");
                                all.sendMessage("§8 ");
                                all.sendMessage("§6Ersteller§8: §b" + p.getDisplayName());
                                all.sendMessage("§cTäter§8: §b" + target.getDisplayName());
                                all.sendMessage("§aGrund§8: §b" + grund);
                                all.sendMessage("§9 ");
                                all.sendMessage(Data.getPrefix() + "§7--------------------------------");
                                Countdown.startCountdown(target);
                            } else{
                                p.sendMessage(Data.getPrefix() + "§cEs läuft bereits ein VoteKick!");
                            }
                        }
                    } else{
                        p.sendMessage(Data.getPrefix() + "§cDer Spieler ist aktuell nicht auf dem §bServer");
                    }
                } else if(args.length == 0) {
                    if(cmd.getName().equalsIgnoreCase("ja")){
                        if(votekick){
                            if(!neinarray.contains(p.getUniqueId().toString()) && !jaarray.contains(p.getUniqueId().toString())){
                                jaarray.add(p.getUniqueId().toString());
                                p.sendMessage(Data.getPrefix() + "Du hast für §aJa §7abgestimmt");
                            } else{
                                p.sendMessage(Data.getPrefix() + "Du hast bereits abgestimmt");
                            }
                        } else{
                            p.sendMessage(Data.getPrefix() + "§cEs läuft aktuell kein VoteKick");
                        }
                    } else if(cmd.getName().equalsIgnoreCase("nein")) {
                        if(votekick){
                            if(!neinarray.contains(p.getUniqueId().toString()) && !jaarray.contains(p.getUniqueId().toString())){
                                neinarray.add(p.getUniqueId().toString());
                                p.sendMessage(Data.getPrefix() + "Du hast für §cNein §7abgestimmt");
                            } else{
                                p.sendMessage(Data.getPrefix() + "Du hast bereits abgestimmt");
                            }
                        } else{
                            p.sendMessage(Data.getPrefix() + "§cEs läuft aktuell kein VoteKick");
                        }
                    }
                } else {
                    p.sendMessage(Data.getPrefix() + "§cBitte Benutze§8: §b/votekick <Spieler> <Grund>");
                }
            }
        }
        return false;
    }
}
