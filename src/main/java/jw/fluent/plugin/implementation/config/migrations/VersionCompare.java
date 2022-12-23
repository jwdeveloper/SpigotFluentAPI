package jw.fluent.plugin.implementation.config.migrations;

import java.util.regex.Pattern;

public class VersionCompare
{
    public static boolean isHigher(String newVersion, String OldVersion) {
        String s1 = normalisedVersion(newVersion);
        String s2 = normalisedVersion(OldVersion);
        int cmp = s1.compareTo(s2);

        if(cmp < 0)
        {
            return false;
        }else if(cmp >0)
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    public static boolean isLower(String newVersion, String OldVersion) {
        String s1 = normalisedVersion(newVersion);
        String s2 = normalisedVersion(OldVersion);
        int cmp = s1.compareTo(s2);

        if(cmp < 0)
        {
            return true;
        }else if(cmp >0)
        {
            return false;
        }
        else
        {
            return false;
        }

    }

    private static String normalisedVersion(String version) {
        return normalisedVersion(version, ".", 4);
    }

    private static String normalisedVersion(String version, String sep, int maxWidth) {
        String[] split = Pattern.compile(sep, Pattern.LITERAL).split(version);
        StringBuilder sb = new StringBuilder();
        for (String s : split) {
            sb.append(String.format("%" + maxWidth + 's', s));
        }
        return sb.toString();
    }
}
