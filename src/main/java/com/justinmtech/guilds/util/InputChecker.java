package com.justinmtech.guilds.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputChecker {
    public static boolean noSpecialCharacters(String[] args) {
        for (String arg : args) {
            Pattern pattern = Pattern.compile("^a-ZA-Z0-9");
            Matcher matcher = pattern.matcher(arg);
            if (matcher.find()) return false;
        }
        return true;
    }
}
