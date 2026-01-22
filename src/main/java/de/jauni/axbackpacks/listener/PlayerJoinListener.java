package de.jauni.axbackpacks.listener;

import de.jauni.axbackpacks.AxBackpacks;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerJoinListener implements Listener {
    AxBackpacks reference;

    public PlayerJoinListener(AxBackpacks reference) {
        this.reference = reference;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        try (Connection conn = reference.getDatabaseManager().getConnection()) {
            try (PreparedStatement check = conn.prepareStatement("SELECT uuid FROM backpacks WHERE uuid = ?")) {
                check.setString(1, p.getUniqueId().toString());
                ResultSet rs = check.executeQuery();
                if (!rs.next()) {
                    try (PreparedStatement insert = conn.prepareStatement("INSERT INTO backpacks (uuid, inventory) VALUES (?, ?)")) {
                        insert.setString(1, p.getUniqueId().toString());
                        insert.setString(2, "");
                        insert.executeUpdate();
                    }
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
