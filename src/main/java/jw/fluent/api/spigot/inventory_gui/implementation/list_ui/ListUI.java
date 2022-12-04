package jw.fluent.api.spigot.inventory_gui.implementation.list_ui;

import jw.fluent.api.spigot.inventory_gui.implementation.chest_ui.ChestUI;
import jw.fluent.api.spigot.inventory_gui.EventsListenerInventoryUI;
import jw.fluent.api.spigot.inventory_gui.button.ButtonUI;
import jw.fluent.api.spigot.inventory_gui.button.button_observer.ButtonObserverUI;
import jw.fluent.api.spigot.inventory_gui.events.ButtonUIEvent;
import jw.fluent.api.spigot.inventory_gui.implementation.list_ui.search_manager.SearchManager;
import jw.fluent.api.spigot.inventory_gui.implementation.list_ui.search_manager.events.SearchFilterEvent;
import jw.fluent.api.spigot.inventory_gui.implementation.list_ui.content_manger.ButtonUIMapper;
import jw.fluent.api.spigot.inventory_gui.implementation.list_ui.content_manger.FilterContentEvent;
import jw.fluent.api.spigot.messages.FluentMessage;
import jw.fluent.api.spigot.messages.message.MessageBuilder;
import jw.fluent.api.utilites.messages.Emoticons;
import jw.fluent.plugin.implementation.FluentApi;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Getter
public class ListUI<T> extends ChestUI {
    private final ListUIManager<T> listContentManager;
    private final SearchManager<T> searchManager;
    private final List<Consumer<Player>> onListOpen;
    private final List<Consumer<Player>> onListClose;
    private final List<ButtonUIEvent> onClickContent;

    private ButtonObserverUI buttonSearch;
    private ButtonUI buttonExit;
    private ButtonObserverUI buttonPageUp;
    private ButtonObserverUI buttonPageDown;

    @Getter
    @Setter
    private String listTitle = "";


    public ListUI(String name, int height) {
        super(name, height);
        onListOpen = new ArrayList<>();
        onListClose = new ArrayList<>();
        onClickContent = new ArrayList<>();
        searchManager = new SearchManager<>();
        listContentManager = new ListUIManager<>(this);
        loadSpecialButtons();
    }

    protected void loadSpecialButtons() {

        setBorderMaterial(Material.GRAY_STAINED_GLASS_PANE);

        buttonSearch = ButtonObserverUI
                .builder()
                .addObserver(searchManager.getObserver())
                .setLocation(0, 0)
                .setDescription(FluentMessage
                        .message()
                        .bar(Emoticons.line,20,ChatColor.GRAY).newLine()
                        .field("Left click","Change option").newLine()
                        .field("Right click",  "Search").newLine()
                        .field("Shift click" ,"Reset").newLine()
                        .toArray()
                )
                .setTitlePrimary(FluentApi.translator().get("gui.base.search.title"))
                .setMaterial(Material.SPYGLASS)
                .setOnRightClick((player, button) ->
                {
                    close();
                    FluentMessage.message().inBrackets("Enter search key", ChatColor.AQUA).send(player);
                    EventsListenerInventoryUI.registerTextInput(player, searchedKey ->
                    {
                        addContentFilter(input ->
                        {
                            return searchManager.search(searchedKey,input,player);
                        });
                        applyFilters();
                        open(player);
                    });
                })
                .setOnShiftClick((player, button) ->
                {
                    resetFilter();
                })
                .buildAndAdd(this);

        buttonPageDown = ButtonObserverUI
                .builder()
                .setLocation(getHeight() - 1, 3)
                .setTitle(new MessageBuilder().color(ChatColor.GRAY).inBrackets(FluentApi.translator().get("gui.base.page-down.title")))
                .setMaterial(Material.ARROW)
                .setOnClick((player, button) ->
                {
                    listContentManager.lastPage();
                })
                .buildAndAdd(this);

        buttonPageUp = ButtonObserverUI
                .builder()
                .setLocation(getHeight() - 1, 5)
                .setTitle(new MessageBuilder().color(ChatColor.GRAY).inBrackets(FluentApi.translator().get("gui.base.page-up.title")))
                .setMaterial(Material.ARROW)
                .setOnClick((player, button) ->
                {
                    listContentManager.nextPage();
                })
                .buildAndAdd(this);

        buttonExit = ButtonObserverUI.factory()
                .goBackButton(this)
                .setOnClick((player, button) ->
                {
                    openParent();
                }).buildAndAdd(this);
    }

    @Override
    protected final void onOpen(Player player) {
        for (var listOpenEvent : onListOpen) {
            listOpenEvent.accept(player);
        }
        this.setTitle(listContentManager.pageDescription());
    }

    @Override
    protected final void onClose(Player player) {
        for (var event : onListClose) {
            event.accept(player);
        }
    }

    @Override
    protected final void onClick(Player player, ButtonUI button) {
        if (!listContentManager.isContentButton(button))
            return;

        for (var event : onClickContent) {
            event.execute(player, button);
        }
    }

    public void setContentButtons(List<T> data, ButtonUIMapper<T> buttonMapper) {
        listContentManager.setButtonFormatter(data, buttonMapper);
        refreshContent();
        displayLog("ContentButtons set, count:" + data.size(), ChatColor.GREEN);
    }

    public void applyFilters() {
        listContentManager.applyFilters();
        refreshContent();
    }

    public void addContentFilter(FilterContentEvent<T> filterContentEvent) {
        listContentManager.addFilter(filterContentEvent);
    }

    public void removeFilter(FilterContentEvent<T> filterContentEvent) {
        listContentManager.removeFilter(filterContentEvent);
    }

    public void resetFilter() {
        listContentManager.resetFilter();
        refreshContent();
    }


    public void refreshContent() {
        setTitle(listContentManager.pageDescription());
        addButtons(listContentManager.getButtons());
        refreshButtons();
    }

    public final void addSearchStrategy(String name,SearchFilterEvent<T> event)
    {
        searchManager.addSearchProfile(name, event);
    }

    public void setListTitlePrimary(String value)
    {
        setListTitle(FluentMessage.message().color(ChatColor.DARK_AQUA).bold().text(value).build());
    }

    public final void onListOpen(Consumer<Player> event) {
        onListOpen.add(event);
    }

    public final void onContentClick(ButtonUIEvent event) {
        onClickContent.add(event);
    }

    public final void onListClose(Consumer<Player> event) {
        onListClose.add(event);
    }
}
