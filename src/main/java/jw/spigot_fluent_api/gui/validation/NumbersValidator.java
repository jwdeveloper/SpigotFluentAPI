package jw.spigot_fluent_api.gui.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumbersValidator implements Validator
 {
     @Override
     public boolean Validate(String input)
     {
         Pattern p = Pattern.compile("\\d+\"");
         Matcher m = p.matcher(input);
         return m.matches();
     }
 }
