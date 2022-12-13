package jw.fluent.api.spigot.gui.fluent_ui.styles;

import jw.fluent.api.utilites.java.StringUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ButtonStyleInfo {
    private String id;
    private String leftClick;
    private String rightClick;
    private String shiftClick;

    private String descriptionTitle;

    private List<String> observerPlaceholder = new ArrayList<>();
    private List<String> descriptionLines = new ArrayList<>();

    public boolean hasId() {
        return !StringUtils.isNullOrEmpty(id);
    }

    public boolean hasDescription()
    {
        return !descriptionLines.isEmpty();
    }

    public boolean hasObserverPlaceholdes()
    {
        return !observerPlaceholder.isEmpty();
    }

    public boolean hasClickInfo() {
        return StringUtils.isNotNullOrEmpty(leftClick) ||
                StringUtils.isNotNullOrEmpty(rightClick) ||
                StringUtils.isNotNullOrEmpty(shiftClick);
    }
}
