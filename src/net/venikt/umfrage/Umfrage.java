package net.venikt.umfrage;

import net.venikt.umfrage.commands.StartKickCommand;
import net.venikt.umfrage.events.PlayerJoinLoginListener;
import net.venikt.umfrage.sql.BanManager;
import net.venikt.umfrage.sql.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Umfrage extends JavaPlugin {

    private static Umfrage instance;
    public static MySQL mysql;

    public static BanManager banManager;

    @Override
    public void onEnable() {
        instance = this;
        getCommand("votekick").setExecutor(new StartKickCommand());
        getCommand("ja").setExecutor(new StartKickCommand());
        getCommand("nein").setExecutor(new StartKickCommand());
        Bukkit.getPluginManager().registerEvents(new PlayerJoinLoginListener(), getInstance());

        banManager = new BanManager();

        loadConfig();

        connectMySQL();

    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public void connectMySQL() {
        String Host = this.getConfig().getString("MySQL.Host");
        int Port = this.getConfig().getInt("MySQL.Port");
        String Database = this.getConfig().getString("MySQL.Database");
        String User = this.getConfig().getString("MySQL.Username");
        String Password = this.getConfig().getString("MySQL.Password");
        mysql.setData(Host, Port, Database, User, Password);
        mysql = MySQL.getInstance();
        mysql.executeQuery("CREATE TABLE IF NOT EXISTS votekick (UUID VARCHAR(100), End VARCHAR(100))");
    }

    @Override
    public void onDisable() {

    }

    public static Umfrage getInstance() {
        return instance;
    }

    public static MySQL getMySQL() {
        return mysql;
    }

    public static BanManager getBanManager() {
        return banManager;
    }


}
