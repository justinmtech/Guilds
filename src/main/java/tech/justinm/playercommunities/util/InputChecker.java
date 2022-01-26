package tech.justinm.playercommunities.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputChecker {
    public static boolean noSpecialCharacters(String input) {
        Pattern pattern = Pattern.compile("^a-ZA-Z0-9");
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }
}
