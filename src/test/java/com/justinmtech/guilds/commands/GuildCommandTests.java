package com.justinmtech.guilds.commands;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import com.justinmtech.guilds.Guilds;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GuildCommandTests {
    private ServerMock server;
    private Guilds plugin;

    @BeforeEach
    public void setup() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(Guilds.class);
    }

    @Test
    void guildsCommand() {
        server.addPlayer();
        Assertions.assertTrue(server.getPlayer(0).performCommand("guilds"));
        Assertions.assertTrue(server.getPlayer(0).performCommand("guilds list"));
        Assertions.assertTrue(server.getPlayer(0).performCommand("guilds warp"));
    }

    @Test
    void guildsOtherCommands() {
        Assertions.assertTrue(server.getPlayer(0).performCommand("guilds"));
    }


    @AfterAll
    public static void tearDown() {
        MockBukkit.unmock();
    }

}