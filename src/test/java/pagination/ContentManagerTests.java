package pagination;

import jw.spigot_fluent_api.gui.implementation.list_ui.content_manger.ContentManager;
import jw.spigot_fluent_api.utilites.bukkit_mocks.ServerMock;
import org.bukkit.Bukkit;
import org.junit.Test;

import java.util.List;

public class ContentManagerTests {

    @Test
    public void testContentManager() {
        var h = 6;
        var w = 9;
        Bukkit.setServer(new ServerMock());
        ContentManager<String> contentManager = new ContentManager<>(h, w);


        contentManager.setContent(List.of("Raz", "Dwa", "Trzy"));
        contentManager.setButtonMapper((a, b) ->
        {
            b.setTitle(a);
        });

        var buttons = contentManager.getButtons();

    }

}
