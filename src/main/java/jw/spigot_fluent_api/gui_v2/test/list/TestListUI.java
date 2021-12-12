package jw.spigot_fluent_api.gui_v2.test.list;

import jw.spigot_fluent_api.gui_v2.list_ui.ListUI;

import java.util.List;

public class TestListUI extends ListUI<String> {
    public TestListUI() {
        super("lista", 6);
        setEnableLogs(true);
    }

    @Override
    protected void onInitialize() {


        this.addContentButtons(List.of("Raz", "Dwa", "Trzy", "Cztery"), (data, button) ->
        {
            button.setTitle(data);
        });

        this.onDelete((player, button) ->
        {

        });

        this.onInsert((player, button) ->
        {

        });


    }
}
