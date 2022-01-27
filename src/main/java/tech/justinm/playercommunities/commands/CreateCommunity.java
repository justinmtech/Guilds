package tech.justinm.playercommunities.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.justinm.playercommunities.PlayerCommunities;
import tech.justinm.playercommunities.SubCommand;

public class CreateCommunity extends SubCommand {

    public CreateCommunity(PlayerCommunities plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    public void execute() {
        Player player = (Player) getSender();
        String name = getArgs()[1];
        getPlugin().getData().createCommunity(player.getUniqueId(), name);
        player.sendMessage("You created a community!");
    }
}
