package tech.justinm.playercommunities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.justinm.playercommunities.PlayerCommunities;
import tech.justinm.playercommunities.core.Community;

public class CreateCommunity implements CommandExecutor {
    private PlayerCommunities plugin;

    public CreateCommunity(PlayerCommunities plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (label.equalsIgnoreCase("pccreate")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                String name = args[0];
                plugin.getData().getAllCommunities().add(new Community(player.getUniqueId(), name));
                player.sendMessage("You created a community!");
                return true;
            }
        }
        return false;
    }
}
