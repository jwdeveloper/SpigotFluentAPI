package jw.fluent.api.spigot.permissions.api;

import jw.fluent.api.spigot.permissions.api.enums.Visibility;
import jw.fluent.api.utilites.java.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PermissionSection
{
    private PermissionModel model;

    private List<PermissionModel> children;

    public boolean hasChildren()
    {
        return children.size() > 0;
    }

    public boolean hasVisibility()
    {
        return !model.getVisibility().equals(Visibility.None);
    }

    public boolean hasGroup()
    {
        return !model.getGroups().isEmpty();
    }

    public boolean hasTitle()
    {
        return !model.getTitle().equals(StringUtils.EMPTY_STRING);
    }

    public boolean hasDescription()
    {
        return !model.getDescription().equals(StringUtils.EMPTY_STRING);
    }
}
