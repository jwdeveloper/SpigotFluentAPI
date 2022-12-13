package jw.fluent.api.utilites.java;

public class StringUtils
{
    public static String EMPTY = "";

    public static boolean isNullOrEmpty(String string)
    {
        if(string == null)
        {
            return true;
        }
        if(string.equals(EMPTY))
        {
            return true;
        }
        return false;
    }

    public static boolean isNotNullOrEmpty(String string)
    {
      return !isNullOrEmpty(string);
    }

    public static  String capitalize(String str)
    {
        if (isNullOrEmpty(str))
        {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}
