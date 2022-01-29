package tech.justinm.playercommunities;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.justinm.playercommunities.commands.*;
import tech.justinm.playercommunities.util.InputChecker;
import tech.justinm.playercommunities.util.Message;

public class CommandHandler implements CommandExecutor {
    private final PlayerCommunities plugin;

    public CommandHandler(PlayerCommunities plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("playercommunities")) {
            if (sender instanceof Player) {
                if (args.length > 0) {
                    if (InputChecker.noSpecialCharacters(args)) {
                        parseSubCommand(sender, args, label);
                    } else {
                        if (args[0].equalsIgnoreCase("setdesc")) {
                            parseSubCommand(sender, args, label);
                        } else {
                        Message.send(plugin, sender, "no-special-characters");
                        }
                    }
                } else {
                Message.sendHelp(plugin, sender, "help", label);
                }
            }
        }
        return true;
    }

    private void parseSubCommand(CommandSender sender, String[] args, String label) {
        if (args[0].equalsIgnoreCase("create")) {
            if (args.length == 2) {
                new CreateCommunity(plugin, sender, args);
            } else {
                Message.sendPlaceholder(plugin, sender, "syntax.create", label);
            }
        } else if (args[0].equalsIgnoreCase("disband")) {
            if (args.length == 1) {
                new DisbandCommunity(plugin, sender, args);
            } else {
                Message.sendPlaceholder(plugin, sender, "syntax.disband", label);
            }
        } else if (args[0].equalsIgnoreCase("warp")) {
            if (args.length == 2) {
                new GoToWarp(plugin, sender, args);
            } else {
                Message.sendPlaceholder(plugin, sender, "syntax.warp", label);
            }
        } else if (args[0].equalsIgnoreCase("setname")) {
            if (args.length == 2) {
                new SetName(plugin, sender, args);
            } else {
                Message.sendPlaceholder(plugin, sender, "syntax.setname", label);
            }
        } else if (args[0].equalsIgnoreCase("invite")) {
            if (args.length == 2) {
                new InvitePlayer(plugin, sender, args);
            } else {
                Message.sendPlaceholder(plugin, sender, "syntax.invite", label);
            }
        } else if (args[0].equalsIgnoreCase("deny")) {
            if (args.length == 2) {
                new DenyInvite(plugin, sender, args);
            } else {
                Message.sendPlaceholder(plugin, sender, "syntax.deny-invite", label);
            }
        } else if (args[0].equalsIgnoreCase("list")) {
            new ListCommunities(plugin, sender, args);
        } else if (args[0].equalsIgnoreCase("setdesc")) {
            if (args.length >= 2) {
                new SetDescription(plugin, sender, args);
            } else {
                Message.sendPlaceholder(plugin, sender, "syntax.setdesc", label);
            }
        } else if (args[0].equalsIgnoreCase("setwarp")) {
            if (args.length == 2) {
                new SetWarp(plugin, sender, args);
            } else {
                Message.sendPlaceholder(plugin, sender, "syntax.setwarp", label);
            }
        } else if (args[0].equalsIgnoreCase("leave")) {
            if (args.length == 1) {
                new LeaveCommunity(plugin, sender, args);
            } else {
                Message.sendPlaceholder(plugin, sender, "syntax.leave", label);
            }
        } else if (args[0].equalsIgnoreCase("accept")) {
                if (args.length == 2) {
                    new AcceptInvite(plugin, sender, args);
                } else {
                    Message.sendPlaceholder(plugin, sender, "syntax.accept-invite", label);
                }
        } else if (args.length == 1) {
            new GetCommunityInfo(plugin, sender, args);
        }
    }
}
