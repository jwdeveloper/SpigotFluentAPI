package jw.fluent.api.spigot.permissions.api;

import jw.fluent.api.spigot.permissions.api.enums.Visibility;
import jw.fluent.api.utilites.java.StringUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PermissionModel {

    private String name = StringUtils.EMPTY;

    private String description = StringUtils.EMPTY;

    private Visibility visibility = Visibility.None;

    private String parentGroup = StringUtils.EMPTY;

    private String title = StringUtils.EMPTY;

    private List<String> groups = new ArrayList<>();

    private boolean isParent = false;
}
