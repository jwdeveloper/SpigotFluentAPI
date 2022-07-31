package jw.spigot_fluent_api.utilites.messages;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Emoticons
{
    public static final String smiley = "☺";
    public static final String sad = "☹";
    public static final String heartHalf = "♥";
    public static final String arrowLeft = "←";

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
    public static final String yingYang = "☯";
    public static final String moon = "☾";
    public static final String crown = "♔";

    public static final String scissor = "✁";
    public static final String plane = "✈";
    public static final String mail = "✉";
    public static final String pencil = "✎";
    public static final String check = "✓";
    public static final String flower = "✿";
    public static final String music = "♩";
    public static final String arrowRight = "→";

    public static String square = "■";
    public static String heart = "❤";
    public static String rotatedHeart = "❥";
    public static String man = "웃";
    public static String woman = "유";
    public static String hipis = "☮";
    public static String piece = "✌";
    public static String phone2 = "☏";
    public static String radioActive = "☢";
    public static String dead = "☠";
    public static String yes = "✔";
    public static String boxYes = "☑";
    public static String crownKing = "♚";
    public static String triangle = "▲";
    public static String note2 = "♪";
    public static String bitCoin = "฿";
    public static String dCoin = "Ɖ";

    public static String smallHeart = "♥";
    public static String tinyHeart = "❣";
    public static String menSymbol = "♂";
    public static String womanSymbol = "♀";
    public static String manAndWoman = "⚤";
    public static String anarhist = "Ⓐ";
    public static String write = "✍";
    public static String email = "✉";
    public static String toxic = "☣";
    public static String bug = "☤";
    public static String no = "✘";
    public static String noBox = "☒";
    public static String crownQueen = "♛";
    public static String triangleDown = "▼";
    public static String note = "♫";
    public static String emoji15 = "⌘";
    public static String time = "⌛";

    public static String emptyHeart = "♡";
    public static String emoji1 = "ღ";
    public static String emoji2 = "ツ";
    public static String emoji3 = "☼";
    public static String cloud2 = "☁";
    public static String snow = "❅";
    public static String infitnity = "♾️";
    public static String pen = "✎";
    public static String copyRight = "©";
    public static String right = "®";
    public static String tm = "™";
    public static String sum = "Σ";
    public static String starCircle = "✪";
    public static String starRussin = "✯";
    public static String comunism = "☭";
    public static String arrow = "➳";
    public static String dot = "•";
    public static String christian = "✞";

    public static String celcius = "℃";
    public static String farenheit = "℉";
    public static String dot2 = "°";
    public static String flower2 = "✿";
    public static String light = "⚡";
    public static String snowGolem = "☃";
    public static String umberlla = "☂";
    public static String sizers = "✄";
    public static String emoji8 = "¢";
    public static String euro = "€";
    public static String funt = "£";
    public static String infitiy = "∞";
    public static String star2 = "✫";
    public static String star3 = "★";
    public static String half = "½";
    public static String yingYang2 = "☯";
    public static String jews = "✡";
    public static String islam = "☪";

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
