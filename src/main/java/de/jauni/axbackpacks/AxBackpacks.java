package de.jauni.axbackpacks;

import de.jauni.axbackpacks.manager.DatabaseManager;
import de.jauni.axbackpacks.manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class AxBackpacks extends JavaPlugin {
    DatabaseManager databaseManager;

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    PlayerManager playerManager;

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        try {
            databaseManager = new DatabaseManager(this);
            playerManager = new PlayerManager(this);
            if (databaseManager.initDatabaseTable1() == false) {
                getLogger().severe("Error creating backpacks table.");
                Bukkit.getServer().shutdown();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        getCommand("backpack").setExecutor(new de.jauni.axbackpacks.command.BackpackCommand(this));
        getServer().getPluginManager().registerEvents(new de.jauni.axbackpacks.listener.InventoryCloseListener(this), this);
        getServer().getPluginManager().registerEvents(new de.jauni.axbackpacks.listener.PlayerJoinListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
