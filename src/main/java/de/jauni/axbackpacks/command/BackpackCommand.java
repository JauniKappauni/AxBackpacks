package de.jauni.axbackpacks.command;

import de.jauni.axbackpacks.AxBackpacks;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class BackpackCommand implements CommandExecutor {
    AxBackpacks reference;

    public BackpackCommand(AxBackpacks reference) {
        this.reference = reference;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        Inventory inv = reference.getPlayerManager().loadPlayerBackpack(p);
        p.openInventory(inv);
        return true;
    }
}
