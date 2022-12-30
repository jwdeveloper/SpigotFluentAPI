package jw.fluent.api.spigot.gui.fluent_ui.observers.list.checkbox;

import jw.fluent.api.desing_patterns.observer.implementation.Observer;
import jw.fluent.api.desing_patterns.observer.implementation.ObserverBag;
import jw.fluent.api.utilites.java.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckBox
{
    private String name = StringUtils.EMPTY;
    private Observer<Boolean> observer = new ObserverBag<Boolean>(false).getObserver();
    private String permission = StringUtils.EMPTY;

    public CheckBox()
    {

    }
}
