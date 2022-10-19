package unit.assets;

import jw.spigot_fluent_api.desing_patterns.mediator.interfaces.MediatorHandler;

public class PlayerMediator implements MediatorHandler<PlayerStats,SuperInventory>
{
    @Override
    public SuperInventory handle(PlayerStats object)
    {
        var result = new SuperInventory(null);
        result.setName(object.getPlayerName());
        return result;
    }
}
