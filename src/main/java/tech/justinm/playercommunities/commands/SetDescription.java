package tech.justinm.playercommunities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.justinm.playercommunities.PlayerCommunities;

public class SetDescription implements CommandExecutor {
    private PlayerCommunities plugin;

    public SetDescription(PlayerCommunities plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("pcsetdesc")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                try {

                    StringBuilder desc = new StringBuilder();
                    for (int i = 0; i < args.length; i++) {
                        desc.append(args[i]).append(" ");
                    }

                    //Check perms other than is member
                    plugin.getCommunities().stream().filter(c -> c.containsMember(player)).findAny().orElseThrow(NullPointerException::new).setDescription(desc.toString().trim());
                    player.sendMessage("Description set to: " + desc.toString());
                    return true;
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    player.sendMessage("An error occurred!");
                    return true;
                }
            }
        }
        return false;
    }
}
