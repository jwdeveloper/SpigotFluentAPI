package jw.spigot_fluent_api.gui_v2.list_ui;
import jw.spigot_fluent_api.gui_v2.button.ButtonUI;
import jw.spigot_fluent_api.gui_v2.button.button_observer.ButtonObserverUI;
import jw.spigot_fluent_api.gui_v2.chest_ui.ChestUI;
import jw.spigot_fluent_api.gui_v2.events.ButtonUIEvent;
import jw.spigot_fluent_api.gui_v2.list_ui.content_manger.ButtonUIMapper;
import jw.spigot_fluent_api.gui_v2.list_ui.content_manger.ContentManager;
import jw.spigot_fluent_api.utilites.messages.MessageBuilder;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

@Getter
public class ListUI<T> extends ChestUI {
    private final ContentManagerViewModel<T> contentManagerViewModel;
    private final ListViewModel<T> listViewModel;

    protected ButtonObserverUI buttonDelete;
    protected ButtonObserverUI buttonEdit;
    protected ButtonObserverUI buttonInsert;
    protected ButtonObserverUI buttonSearch;
    protected ButtonObserverUI buttonCancel;
    protected ButtonObserverUI buttonExit;
    protected ButtonObserverUI buttonPageUp;
    protected ButtonObserverUI buttonPageDown;

    public ListUI(String name, int height) {
        super(name, height);
        contentManagerViewModel = new ContentManagerViewModel<>(this);
        listViewModel = new ListViewModel<>(this);
        loadActionButtons();
    }

    protected void loadActionButtons() {
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

        buttonEdit = ButtonObserverUI
                .builder()
                .setLocation(0, 5)
                .setTitle(new MessageBuilder().color(ChatColor.DARK_GREEN).inBrackets("Insert"))
                .setMaterial(Material.CRAFTING_TABLE)
                .addObserver(listViewModel.insertObserver())
                .buildAndAdd(this);


        buttonSearch = ButtonObserverUI
                .builder()
                .setLocation(0, 0)
                .setTitle(new MessageBuilder().color(ChatColor.BLUE).inBrackets("Search"))
                .setMaterial(Material.SPYGLASS)
                .addObserver(listViewModel.searchObserver())
                .buildAndAdd(this);

        buttonCancel = ButtonObserverUI
                .builder()
                .setLocation(getHeight() - 1, 4)
                .addObserver(listViewModel.cancelObserver())
                .buildAndAdd(this);


        buttonPageDown = ButtonObserverUI
                .builder()
                .setLocation(getHeight() - 1, 5)
                .setTitle(new MessageBuilder().color(ChatColor.WHITE).inBrackets("Page down"))
                .setMaterial(Material.ARROW)
                .buildAndAdd(this);

        buttonPageUp = ButtonObserverUI
                .builder()
                .setLocation(getHeight() - 1, 3)
                .setTitle(new MessageBuilder().color(ChatColor.WHITE).inBrackets("Page up"))
                .setMaterial(Material.ARROW)
                .buildAndAdd(this);

        buttonExit = ButtonObserverUI
                .builder()
                .setLocation(getHeight() - 1, INVENTORY_WIDTH-1)
                .setTitle(new MessageBuilder().color(ChatColor.GRAY).inBrackets("Exit"))
                .setMaterial(Material.ARROW)
                .setOnClick((player, button) ->
                {
                    openParent();
                })
                .buildAndAdd(this);
    }

    @Override
    protected void onOpen(Player player)
    {
        listViewModel.setState(ListState.None);
        this.setTitle(new MessageBuilder().inBrackets(name)
                .space()
                .text(contentManagerViewModel.pageDescription())
                .toString());
    }

    @Override
    protected void onClose(Player player) {
        listViewModel.setState(ListState.None);
    }

    @Override
    protected void onClick(Player player, ButtonUI button) {
        if (!contentManagerViewModel.isContentButton(button))
            return;

        listViewModel.selectHandler(player, button);
    }

    public void addContentButtons(List<T> data, ButtonUIMapper<T> buttonMapper) {
        contentManagerViewModel.setButtonFormatter(data, buttonMapper);
        refreshContent();
    }

    public void refreshContent() {
        addButtons(contentManagerViewModel.getButtons());
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
