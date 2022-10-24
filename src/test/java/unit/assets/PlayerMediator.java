package unit.assets;

import jw.fluent_api.desing_patterns.mediator.api.MediatorHandler;

public class PlayerMediator implements MediatorHandler<PlayerStats,SuperInventory>
{
    @Override
    public SuperInventory handle(PlayerStats object)
    {
        var result = new SuperInventory(null, null);
        result.setName(object.getPlayerName());
        return result;
    }
}
