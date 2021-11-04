package jw.spigot_fluent_api.utilites.messages;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Emoticons
{
    public static final String smiley = "☺";
    public static final String sad = "☹";
    public static final String heart = "♥";
    public static final String arrowLeft = "←";
    public static final String arrowright = "→";
    public static final String cloud = "☁";
    public static final String sun = "☀";
    public static final String unbrella = "☂";
    public static final String snowman = "☃";
    public static final String comet = "☄";
    public static final String star = "★";
    public static final String phone = "☎";
    public static final String skull = "☠";
    public static final String radioactive = "☢";
    public static final String biohazard = "☣";
    public static final String peace = "☮";
    public static final String yingyang = "☯";
    public static final String moon = "☾";
    public static final String crown = "♔";
    public static final String music = "♩";
    public static final String scissor = "✁";
    public static final String plane = "✈";
    public static final String mail = "✉";
    public static final String pencil = "✎";
    public static final String check = "✓";
    public static final String flower = "✿";

    public static  List<String> getValues()
    {
     List<String> result = new ArrayList<>();
     Class<Emoticons> emoticons = Emoticons.class;
     Field[] properties = emoticons.getFields();

     try
     {
         for(Field field : properties)
         {
             result.add(field.get(null).toString());
         }
     }
     catch (Exception ignored)
     {

     }


     return result;
    }

}
