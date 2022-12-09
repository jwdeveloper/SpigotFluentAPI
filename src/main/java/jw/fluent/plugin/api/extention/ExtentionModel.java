package jw.fluent.plugin.api.extention;

import jw.fluent.plugin.api.FluentApiExtension;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExtentionModel
{
    private FluentApiExtension extention;

    private ExtentionPiority piority;

}
