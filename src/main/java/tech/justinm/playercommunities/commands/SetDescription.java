package tech.justinm.playercommunities.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.justinm.playercommunities.PlayerCommunities;
import tech.justinm.playercommunities.SubCommand;

public class SetDescription extends SubCommand {

    public SetDescription(PlayerCommunities plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    private void execute() {
        Player player = (Player) getSender();
        try {
            StringBuilder desc = new StringBuilder();
            for (String arg : getArgs()) {
                desc.append(arg).append(" ");
            }
            getPlugin().getData().getCommunity(player.getUniqueId()).setDescription(desc.toString().trim());
            player.sendMessage("Description set to: " + desc);
        } catch (NullPointerException e) {
            e.printStackTrace();
            player.sendMessage("An error occurred!");
        }
    }
}
