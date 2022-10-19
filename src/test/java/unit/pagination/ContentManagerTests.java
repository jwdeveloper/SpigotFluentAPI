package unit.pagination;

import jw.fluent_api.minecraft.gui.implementation.list_ui.content_manger.ContentManager;
import org.junit.Test;

import java.util.List;

public class ContentManagerTests {

    @Test
    public void testContentManager() {


        var h = 6;
        var w = 9;
        ContentManager<String> contentManager = new ContentManager<>(h, w);


        contentManager.setContent(List.of("Raz", "Dwa", "Trzy"));
        contentManager.setButtonMapper((a, b) ->
        {
            b.setTitle(a);
        });

        var buttons = contentManager.getButtons();

    }

}
