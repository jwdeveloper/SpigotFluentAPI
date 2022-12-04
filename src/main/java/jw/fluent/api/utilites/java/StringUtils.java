package jw.fluent.api.utilites.java;

public class StringUtils
{
    public static String EMPTY_STRING = "";

    public static boolean nullOrEmpty(String string)
    {
        if(string == null)
        {
            return true;
        }
        if(string.equals(EMPTY_STRING))
        {
            return true;
        }
        return false;
    }
}
