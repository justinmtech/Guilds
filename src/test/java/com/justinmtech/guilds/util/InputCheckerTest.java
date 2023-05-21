package com.justinmtech.guilds.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InputCheckerTest {

    @Test
    public void noSpecialCharacters() {
        String[] characters = new String[]{"guilds", "invite", "jamm"};
        Assertions.assertTrue(InputChecker.noSpecialCharacters(characters));
        String[] charactersSpecial = new String[]{"guilds", "create", "newGuild$"};
        Assertions.assertFalse(InputChecker.noSpecialCharacters(charactersSpecial));
    }
}