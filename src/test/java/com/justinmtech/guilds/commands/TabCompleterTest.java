package com.justinmtech.guilds.commands;

import com.justinmtech.guilds.bukkit.commands.TabCompleter;
import org.junit.Test;

public class TabCompleterTest {

    @Test
    public void getRecommendationsThatStartWith() {
        TabCompleter.getRecommendationsThatStartWith("", TabCompleter.BASE_COMMANDS).forEach(System.out::println);
        TabCompleter.getRecommendationsThatStartWith("le", TabCompleter.HAS_GUILD_COMMANDS).forEach(System.out::println);
        TabCompleter.getRecommendationsThatStartWith("", TabCompleter.GUILD_LEADER_COMMANDS).forEach(System.out::println);

    }
}