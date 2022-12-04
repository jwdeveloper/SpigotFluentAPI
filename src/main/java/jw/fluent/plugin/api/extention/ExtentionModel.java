package jw.fluent.plugin.api.extention;

import jw.fluent.plugin.api.FluentApiExtention;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExtentionModel
{
    private FluentApiExtention extention;

    private ExtentionPiority piority;

}
