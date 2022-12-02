package jw.fluent_api.spigot.map_gui.implementation;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.map.MapView;

public class MapFactory
{


    public MapView createMap(Player player)
    {
        MapView mapView = Bukkit.getServer().createMap(player.getWorld());
        mapView.addRenderer(new SimpleRenderer());

        return mapView;
    }
}
