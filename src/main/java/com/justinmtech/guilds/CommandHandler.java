package com.justinmtech.guilds;

import com.justinmtech.guilds.commands.*;
import com.justinmtech.guilds.util.InputChecker;
import com.justinmtech.guilds.util.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler implements CommandExecutor {
    private final Guilds plugin;

    public CommandHandler(Guilds plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("guilds")) {
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
                new CreateGuild(plugin, sender, args);
            } else {
                Message.sendPlaceholder(plugin, sender, "syntax.create", label);
            }
        } else if (args[0].equalsIgnoreCase("disband")) {
            if (args.length == 1) {
                new DisbandGuild(plugin, sender, args);
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
            new ListGuilds(plugin, sender, args);
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
        } else if (args[0].equalsIgnoreCase("upgrade")) {
            if (args.length == 1) {
                new Upgrade(plugin, sender, args, label);
            } else {
                Message.sendPlaceholder(plugin, sender, "syntax.upgrade", label);
            }
        } else if (args[0].equalsIgnoreCase("confirm")) {
            if (args.length == 1) {
                new Confirmation(plugin, sender, args);
            } else {
                Message.sendPlaceholder(plugin, sender, "syntax.confirm", label);
            }
        } else if (args[0].equalsIgnoreCase("leave")) {
            if (args.length == 1) {
                new LeaveGuild(plugin, sender, args);
            } else {
                Message.sendPlaceholder(plugin, sender, "syntax.leave", label);
            }
        } else if (args[0].equalsIgnoreCase("accept")) {
                if (args.length == 2) {
                    new AcceptInvite(plugin, sender, args);
                } else {
                    Message.sendPlaceholder(plugin, sender, "syntax.accept-invite", label);
                }
        } else {
            Message.sendHelp(plugin, sender, "help", label);
        }
    }
}
