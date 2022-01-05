package jw.spigot_fluent_api.legacy_gui.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomValidator implements Validator
{
    private String regex;
    public CustomValidator(String regex)
    {
        this.regex = regex;
    }
    @Override
    public boolean Validate(String input)
    {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        return m.matches();
    }
}
