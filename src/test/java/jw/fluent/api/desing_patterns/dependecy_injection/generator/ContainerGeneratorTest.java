package jw.fluent.api.desing_patterns.dependecy_injection.generator;

import jw.fluent.api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent.api.desing_patterns.dependecy_injection.api.models.RegistrationInfo;
import jw.fluent.api.desing_patterns.dependecy_injection.generator.assets.Example;
import jw.fluent.api.desing_patterns.dependecy_injection.generator.assets.IExample;
import jw.fluent.api.desing_patterns.dependecy_injection.implementation.containers.builder.ContainerBuilderImpl;
import jw.fluent.api.files.implementation.FileUtility;
import jw.fluent.api.utilites.code_generator.builders.ClassCodeBuilder;
import jw.fluent.api.utilites.java.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import unit.assets.PlayerMediator;
import unit.assets.PlayerStats;
import unit.assets.SomeRepo;
import unit.assets.SuperInventory;

import java.io.IOException;
import java.util.List;


public class ContainerGeneratorTest {


    private List<RegistrationInfo> createRegistrationInfo() {
        var builder = new ContainerBuilderImpl<>();
        builder.registerSigleton(SomeRepo.class);
        builder.registerSigleton(PlayerStats.class);
        builder.registerTransient(SuperInventory.class);
        builder.registerSigleton(IExample.class, Example.class);
        builder.register(PlayerMediator.class, LifeTime.SINGLETON,(e)->
        {
            return new Example();
        });
        return builder.getConfiguration().getRegistrations();
    }

    @Test
    public void should_generate_container() throws IOException {

        // arrange
        var registrations = createRegistrationInfo();
        var _package = "jw.fluent.api.desing_patterns.dependecy_injection.generator";
        var clazzName = "GeneratedContainer";
        var generator = new ContainerGenerator(registrations);

        // act
        var clazzContent = generator.generate(_package, clazzName);
        FileUtility.saveClassFile(clazzContent, true, _package, clazzName);
        // assert
        Assert.assertNotEquals(StringUtils.EMPTY, clazzContent);
    }

    @Test
    public void should_container_be_working() throws IOException {

        // arrange
        // act
        var container = new GeneratedContainer();

        var registrations = createRegistrationInfo();
        for(var registr : registrations)
        {
            container.register(registr);
        }

        var someRepo = (SomeRepo)container.find(SomeRepo.class);
        var playerStats = (PlayerStats)container.find(PlayerStats.class);
        var playerMediator = (PlayerMediator)container.find(PlayerMediator.class);
        var SuperInventoryTransient1 = (SuperInventory)container.find(SuperInventory.class);
        var SuperInventoryTransient2 = (SuperInventory)container.find(SuperInventory.class);

        // assert

        Assert.assertNotNull(someRepo);
        Assert.assertNotNull(playerStats);
        Assert.assertNotNull(playerMediator);
        Assert.assertNotNull(SuperInventoryTransient1);
        Assert.assertNotNull(SuperInventoryTransient2);
        Assert.assertNotEquals(SuperInventoryTransient1, SuperInventoryTransient2);
    }

}