package jw.fluent.api.desing_patterns.dependecy_injection.generator;

import jw.fluent.api.desing_patterns.dependecy_injection.api.containers.Container;

import java.util.HashMap;
import java.util.Map;

import jw.fluent.api.desing_patterns.dependecy_injection.api.models.RegistrationInfo;

import java.util.function.Supplier;
import java.util.function.Function;

import jw.fluent.api.desing_patterns.dependecy_injection.api.enums.RegistrationType;

//@JW generated code source don't modify it

public class GeneratedContainer implements Container {
    private final Map<Class<?>, Supplier<Object>> injections;

    private unit.assets.SomeRepo someRepoInstance;

    private unit.assets.PlayerStats playerStatsInstance;

    private jw.fluent.api.desing_patterns.dependecy_injection.generator.assets.IExample iExampleInstance;

    private Function<Container, Object> playerMediatorInstanceProvider;

    private unit.assets.PlayerMediator playerMediatorInstance;


    public GeneratedContainer() {
        injections = new HashMap<>();

        injections.put(unit.assets.SomeRepo.class, this::getUnit_assets_SomeRepo);
        injections.put(unit.assets.PlayerStats.class, this::getUnit_assets_PlayerStats);
        injections.put(unit.assets.SuperInventory.class, this::getUnit_assets_SuperInventory);
        injections.put(jw.fluent.api.desing_patterns.dependecy_injection.generator.assets.IExample.class, this::getJw_fluent_api_desing_patterns_dependecy_injection_generator_assets_IExample);
        injections.put(unit.assets.PlayerMediator.class, this::getUnit_assets_PlayerMediator);
    }

    @Override
    public Object find(Class<?> type) {
        if (!injections.containsKey(type)) {
            System.out.println("Injection not found");
            return null;
        }

        return injections.get(type).get();
    }

    //SINGLETON for class SomeRepo
    private unit.assets.SomeRepo getUnit_assets_SomeRepo() {
        if (someRepoInstance != null) {
            return someRepoInstance;
        }

        someRepoInstance = new unit.assets.SomeRepo();
        return someRepoInstance;
    }

    //SINGLETON for class PlayerStats
    private unit.assets.PlayerStats getUnit_assets_PlayerStats() {
        if (playerStatsInstance != null) {
            return playerStatsInstance;
        }

        playerStatsInstance = new unit.assets.PlayerStats();
        return playerStatsInstance;
    }

    //TRANSIENT for class SuperInventory
    private unit.assets.SuperInventory getUnit_assets_SuperInventory() {
        return new unit.assets.SuperInventory(getUnit_assets_PlayerStats(), getUnit_assets_SomeRepo());
    }

    //SINGLETON for class IExample
    private jw.fluent.api.desing_patterns.dependecy_injection.generator.assets.IExample getJw_fluent_api_desing_patterns_dependecy_injection_generator_assets_IExample() {
        if (iExampleInstance != null) {
            return iExampleInstance;
        }

        iExampleInstance = new jw.fluent.api.desing_patterns.dependecy_injection.generator.assets.Example();
        return iExampleInstance;
    }

    //SINGLETON for class PlayerMediator
    private unit.assets.PlayerMediator getUnit_assets_PlayerMediator() {
        if (playerMediatorInstanceProvider == null) {
            System.out.println("Provider need to be registered");
            return null;
        }

        if (playerMediatorInstance != null) {
            return playerMediatorInstance;
        }

        playerMediatorInstance = (unit.assets.PlayerMediator) playerMediatorInstanceProvider.apply(this);
        return playerMediatorInstance;
    }

    @Override
    public boolean register(RegistrationInfo info) {

        if (info.registrationType() == RegistrationType.InterfaceAndProvider &&
                info._interface().equals(unit.assets.PlayerMediator.class)) {
            playerMediatorInstanceProvider = info.provider();
            return true;
        }

        return false;
    }
}
