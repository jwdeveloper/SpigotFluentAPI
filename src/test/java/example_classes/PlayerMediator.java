package example_classes;

import jw.spigot_fluent_api.desing_patterns.mediator.Mediator;

public class PlayerMediator implements Mediator<PlayerStats,SuperInventory>
{
    @Override
    public SuperInventory handle(PlayerStats object)
    {
        var result = new SuperInventory(null);
        result.setName(object.getPlayerName());
        return result;
    }
}
