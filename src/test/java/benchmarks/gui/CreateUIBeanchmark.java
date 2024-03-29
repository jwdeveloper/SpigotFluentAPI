package benchmarks.gui;

import org.junit.Ignore;
import unit.desing_patterns.mediator.SpigotTestBase;
import jw.fluent.api.spigot.gui.inventory_gui.implementation.chest_ui.ChestUI;
import jw.fluent.api.spigot.gui.inventory_gui.implementation.crud_list_ui.CrudListUI;
import jw.fluent.api.spigot.gui.inventory_gui.implementation.list_ui.ListUI;
import jw.fluent.api.utilites.benchmark.Benchmarker;
import org.junit.Test;

public class CreateUIBeanchmark extends SpigotTestBase
{

    @Test
    @Ignore
    public void testContentManager() {
        Benchmarker.run(10000,o ->
        {
            CrudListUI<String> crudListUI = new CrudListUI<>("test",2);
           // crudListUI.open(new PlayerMock());
        });

        Benchmarker.run(10000,o ->
        {
            ListUI<String> crudListUI = new ListUI<>("test",2);
          //  crudListUI.open(new PlayerMock());
        });

        Benchmarker.run(10000,o ->
        {
            ChestUI crudListUI = new ChestUI("test",2);
          //  crudListUI.open(new PlayerMock());
        });

    }

}
