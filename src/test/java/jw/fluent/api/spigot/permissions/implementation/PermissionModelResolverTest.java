package jw.fluent.api.spigot.permissions.implementation;

import jw.fluent.api.spigot.permissions.api.PermissionModel;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PermissionModelResolverTest {

    @Test
    public void merge() {

        var resolver = new PermissionModelResolver();


        var root = new PermissionModel();
        root.setName("plugin");

        var commands = new PermissionModel();
        commands.setName("commands");

        var cmd = new PermissionModel();
        cmd.setName("play");


        root.addChild(commands);
        commands.addChild(cmd);

        var fakeRoot = new PermissionModel();
        fakeRoot.setName("plugin");

        var extenalCommands = new PermissionModel();
        extenalCommands.setName("commands");



        var externalCmd = new PermissionModel();
        externalCmd.setName("join");
        extenalCommands.addChild(externalCmd);

        var leave = new PermissionModel();
        leave.setName("leave");

        fakeRoot.addChild(leave);
        fakeRoot.addChild(extenalCommands);



       var result = resolver.merge(root, List.of(extenalCommands, leave));

        Assert.assertEquals(result.size(),1);
        Assert.assertEquals(result.get(0).getName(), "plugin");
        Assert.assertEquals(result.get(0).getChildren().size(), 2);
        Assert.assertEquals(result.get(0).getChildren().get(0).getChildren().size(), 2);
        Assert.assertEquals(result.get(0).getChildren().get(1).getName(), "leave");
    }
}