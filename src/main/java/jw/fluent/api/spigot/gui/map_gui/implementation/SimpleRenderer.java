package jw.fluent.api.spigot.gui.map_gui.implementation;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class SimpleRenderer extends MapRenderer {
    @Override
    public void render(MapView map, MapCanvas canvas,  Player player)
    {

        canvas.setPixel(1, 1, MapPalette.RED);
    }
}
