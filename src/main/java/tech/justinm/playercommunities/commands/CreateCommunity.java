package tech.justinm.playercommunities.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.justinm.playercommunities.PlayerCommunities;
import tech.justinm.playercommunities.SubCommand;
import tech.justinm.playercommunities.util.Message;

public class CreateCommunity extends SubCommand {

    public CreateCommunity(PlayerCommunities plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    public void execute() {
        Player player = (Player) getSender();
        String name = getArgs()[1];
        if (getPlugin().getData().getCommunity(name) == null) {
        getPlugin().getData().createCommunity(player.getUniqueId(), name);
        Message.send(getPlugin(), player, "create-community");
        } else {
            Message.send(getPlugin(), getSender(), "community-already-exists");
        }
    }
}
