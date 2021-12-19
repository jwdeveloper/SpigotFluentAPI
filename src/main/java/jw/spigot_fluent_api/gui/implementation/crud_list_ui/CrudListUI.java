package jw.spigot_fluent_api.gui.implementation.crud_list_ui;

import jw.spigot_fluent_api.gui.button.button_observer.ButtonObserverUI;
import jw.spigot_fluent_api.gui.events.ButtonUIEvent;
import jw.spigot_fluent_api.gui.implementation.list_ui.ListUI;
import jw.spigot_fluent_api.utilites.messages.MessageBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class CrudListUI<T> extends ListUI<T>
{
    private final CrudListController<T> listViewModel;
    private ButtonObserverUI buttonCancel;
    private ButtonObserverUI buttonDelete;
    private ButtonObserverUI buttonEdit;
    private ButtonObserverUI buttonInsert;

    public CrudListUI(String name, int height)
    {
        super(name, height);
        listViewModel = new CrudListController<>(this);
        loadCrudButtons();
    }

    protected void loadCrudButtons()
    {
        buttonDelete = ButtonObserverUI
                .builder()
                .setLocation(0, 7)
                .setTitle(new MessageBuilder().color(ChatColor.DARK_RED).inBrackets("Delete"))
                .setMaterial(Material.BARRIER)
                .addObserver(listViewModel.deleteObserver())
                .buildAndAdd(this);

        buttonEdit = ButtonObserverUI
                .builder()
                .setLocation(0, 6)
                .setTitle(new MessageBuilder().color(ChatColor.YELLOW).inBrackets("Edit"))
                .setMaterial(Material.WRITABLE_BOOK)
                .addObserver(listViewModel.editObserver())
                .buildAndAdd(this);

        buttonInsert = ButtonObserverUI
                .builder()
                .setLocation(0, 5)
                .setTitle(new MessageBuilder().color(ChatColor.DARK_GREEN).inBrackets("Insert"))
                .setMaterial(Material.CRAFTING_TABLE)
                .addObserver(listViewModel.insertObserver())
                .buildAndAdd(this);

        buttonCancel = ButtonObserverUI
                .builder()
                .setLocation(getHeight() - 1, 4)
                .addObserver(listViewModel.cancelObserver())
                .buildAndAdd(this);

        onListOpen(player ->
        {
            listViewModel.setState(CrudListState.None);
        });

        onListClose(player ->
        {
            listViewModel.setState(CrudListState.None);
        });
        onContentClick(listViewModel::selectHandler);
    }

    public void onDelete(ButtonUIEvent event)
    {
        listViewModel.onDeleteEvent = event;
    }

    public void onEdit(ButtonUIEvent event)
    {
        listViewModel.onEditEvent = event;
    }

    public void onInsert(ButtonUIEvent event)
    {
        listViewModel.onInsertEvent = event;
    }

    public void onGet(ButtonUIEvent event)
    {
        listViewModel.onGetEvent = event;
    }
}
