package com.justinmtech.guilds.bukkit.commands;

import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.util.InputChecker;
import com.justinmtech.guilds.bukkit.util.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class CommandHandler implements CommandExecutor {
    private final Guilds plugin;

    public CommandHandler(Guilds plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("guilds") && !command.getName().equalsIgnoreCase("gc")) return false;
        if (sender instanceof ConsoleCommandSender) Message.sendHelp(plugin, sender, "help", label);
        if (args.length == 0 && command.getName().equalsIgnoreCase("guilds")) {
            guildInfo(sender, args, label);
            return true;
        } else if (command.getName().equalsIgnoreCase("gc")) {
            sendChat(sender, args, label, command.getName());
            return true;
        }
        if (command.getName().equalsIgnoreCase("guilds")) parseSubCommand(sender, args, label, command.getName());
        return true;
    }

    private void parseSubCommand(CommandSender sender, String[] args, String label, String command) {
        if (args[0].equalsIgnoreCase("help")) Message.sendHelp(plugin, sender, "help", label);
        else if (args[0].equalsIgnoreCase("create")) {
            if (InputChecker.noSpecialCharacters(args)) {
                guildCreate(sender, args, label);
            } else {
                Message.send(plugin, sender, "no-special-characters");
            }
        }
        else if (args[0].equalsIgnoreCase("disband")) guildDisband(sender, args, label);
        else if (args[0].equalsIgnoreCase("promote") || args[0].equalsIgnoreCase("demote")) {
            guildPromote(sender, args, label);
        }
        else if (args[0].equalsIgnoreCase("warp")) guildWarp(sender, args, label);
        else if (args[0].equalsIgnoreCase("kick")) playerKick(sender, args, label);
        else if (args[0].equalsIgnoreCase("invite")) guildInvite(sender, args, label);
        else if (args[0].equalsIgnoreCase("deny")) guildDeny(sender, args, label);
        else if (args[0].equalsIgnoreCase("list")) new ListGuilds(plugin, sender, args);
        else if (args[0].equalsIgnoreCase("setdesc")) guildSetDesc(sender, args, label);
        else if (args[0].equalsIgnoreCase("setwarp")) {
            if (InputChecker.noSpecialCharacters(args)) {
                guildSetWarp(sender, args, label);
            } else {
                Message.send(plugin, sender, "no-special-characters");
            }
        }
        else if (args[0].equalsIgnoreCase("upgrade")) guildUpgrade(sender, args, label);
        else if (args[0].equalsIgnoreCase("confirm")) guildConfirmUpgrade(sender, args, label);
        else if (args[0].equalsIgnoreCase("leave")) guildLeave(sender, args, label);
        else if (args[0].equalsIgnoreCase("accept")) guildAcceptInvite(sender, args, label);
        else if (args[0].equalsIgnoreCase("settag")) {
            if (InputChecker.noSpecialCharacters(args)) {
                setTag(sender, args, label);
            } else {
                Message.send(plugin, sender, "no-special-characters");
            }
        }
        else if (args[0].equalsIgnoreCase("chat") || args[0].equalsIgnoreCase("c")) {
            sendChat(sender, args, label, command);
        }
        else if (args.length == 1) guildInfo(sender, args, label);
    }

    private void sendChat(CommandSender sender, String[] args, String label, String command) {
        if (command.equalsIgnoreCase("gc") && args.length >= 1) new Chat(plugin, sender, args);
        else if (!command.equalsIgnoreCase("gc") && args.length >= 2) new Chat(plugin, sender, args);
        else Message.sendPlaceholder(plugin, sender, "syntax.chat", label);
    }

    private void playerKick(CommandSender sender, String[] args, String label) {
        if (args.length == 2) new KickPlayer(plugin, sender, args);
        else Message.sendPlaceholder(plugin, sender, "syntax.kick", label);
    }

    private void setTag(CommandSender sender, String[] args, String label) {
        if (args.length == 2) new SetTag(plugin, sender, args);
        else Message.sendPlaceholder(plugin, sender, "syntax.settag", label);
    }

    private void guildPromote(CommandSender sender, String[] args, String label) {
        if (args.length == 2) new PromoteDemotePlayer(plugin, sender, args);
        else Message.sendPlaceholder(plugin, sender, "syntax.promote", label);
    }

    private void guildLeave(CommandSender sender, String[] args, String label) {
        if (args.length == 1) new LeaveGuild(plugin, sender, args);
        else Message.sendPlaceholder(plugin, sender, "syntax.leave", label);

    }

    private void guildAcceptInvite(CommandSender sender, String[] args, String label) {
        if (args.length == 2) new AcceptInvite(plugin, sender, args);
        else Message.sendPlaceholder(plugin, sender, "syntax.confirm", label);
    }

    private void guildConfirmUpgrade(CommandSender sender, String[] args, String label) {
        if (args.length == 1) new Confirmation(plugin, sender, args);
        else Message.sendPlaceholder(plugin, sender, "syntax.confirm", label);
    }

    private void guildUpgrade(CommandSender sender, String[] args, String label) {
        if (args.length == 1) new Upgrade(plugin, sender, args, label);
        else Message.sendPlaceholder(plugin, sender, "syntax.upgrade", label);
    }

    private void guildSetWarp(CommandSender sender, String[] args, String label) {
        if (args.length == 2) new SetWarp(plugin, sender, args);
        else Message.sendPlaceholder(plugin, sender, "syntax.setwarp", label);
    }

    private void guildSetDesc(CommandSender sender, String[] args, String label) {
        if (args.length >= 2) new SetDescription(plugin, sender, args);
        else Message.sendPlaceholder(plugin, sender, "syntax.setdesc", label);
    }

    private void guildInfo(CommandSender sender, String[] args, String label) {
        if (args.length == 0 || args.length == 1) new GetGuildInfo(plugin, sender, args);
        else Message.sendPlaceholder(plugin, sender, "messages.guild-not-found", label != null ? label : "guild");
    }

    private void guildDeny(CommandSender sender, String[] args, String label) {
        if (args.length == 2) new DenyInvite(plugin, sender, args);
        else Message.sendPlaceholder(plugin, sender, "syntax.deny-invite", label);
    }

    private void guildCreate(CommandSender sender, String[] args, String label) {
        if (args.length == 2) new CreateGuild(plugin, sender, args);
        else Message.sendPlaceholder(plugin, sender, "syntax.create", label);
    }

    private void guildDisband(CommandSender sender, String[] args, String label) {
        if (args.length == 1) new DisbandGuild(plugin, sender, args);
        else Message.sendPlaceholder(plugin, sender, "syntax.disband", label);
    }

    private void guildWarp(CommandSender sender, String[] args, String label) {
        if (args.length == 2) new GoToWarp(plugin, sender, args);
        else Message.sendPlaceholder(plugin, sender, "syntax.warp", label);
    }

    private void guildInvite(CommandSender sender, String[] args, String label) {
        if (args.length == 2) new InvitePlayer(plugin, sender, args);
        else Message.sendPlaceholder(plugin, sender, "syntax.invite", label);
    }
}
