package ui;

import jw.spigot_fluent_api.gui.implementation.chest_ui.ChestUI;
import jw.spigot_fluent_api.gui.implementation.crud_list_ui.CrudListUI;
import jw.spigot_fluent_api.gui.implementation.list_ui.ListUI;
import jw.spigot_fluent_api.utilites.bukkit_mocks.PlayerMock;
import jw.spigot_fluent_api.utilites.bukkit_mocks.ServerMock;
import jw.spigot_fluent_api.utilites.unit_tests.benchmark.Benchmarker;
import org.bukkit.Bukkit;
import org.junit.Test;

public class CreateUIBeanchmark
{
    @Test
    public void testContentManager() {
        initSpigot();
        Benchmarker.run(10000,o ->
        {
            CrudListUI<String> crudListUI = new CrudListUI<>("test",2);
            crudListUI.open(new PlayerMock());
        });

        Benchmarker.run(10000,o ->
        {
            ListUI<String> crudListUI = new ListUI<>("test",2);
            crudListUI.open(new PlayerMock());
        });

        Benchmarker.run(10000,o ->
        {
            ChestUI crudListUI = new ChestUI("test",2);
            crudListUI.open(new PlayerMock());
        });

    }

    public void initSpigot()
    {
        Bukkit.setServer(new ServerMock());
        CrudListUI<String> crudListUI = new CrudListUI<>("test",2);
    }
}
