package jw.fluent.api.spigot.gui.inventory_gui.implementation.list_ui;

import jw.fluent.api.spigot.gui.fluent_ui.FluentChestUI;
import jw.fluent.api.spigot.gui.inventory_gui.implementation.chest_ui.ChestUI;
import jw.fluent.api.spigot.gui.inventory_gui.EventsListenerInventoryUI;
import jw.fluent.api.spigot.gui.inventory_gui.button.ButtonUI;
import jw.fluent.api.spigot.gui.inventory_gui.button.observer_button.ButtonObserverUI;
import jw.fluent.api.spigot.gui.inventory_gui.events.ButtonUIEvent;
import jw.fluent.api.spigot.gui.inventory_gui.implementation.list_ui.search_manager.SearchManager;
import jw.fluent.api.spigot.gui.inventory_gui.implementation.list_ui.search_manager.events.SearchFilterEvent;
import jw.fluent.api.spigot.gui.inventory_gui.implementation.list_ui.content_manger.ButtonUIMapper;
import jw.fluent.api.spigot.gui.inventory_gui.implementation.list_ui.content_manger.FilterContentEvent;
import jw.fluent.plugin.implementation.modules.messages.FluentMessage;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.modules.translator.FluentTranslator;
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
    private final FluentTranslator translator;
    private final FluentChestUI fluentUI;
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
        translator = FluentApi.translator();
        fluentUI = FluentApi.container().findInjection(FluentChestUI.class);
        loadSpecialButtons();
    }

    protected void loadSpecialButtons() {

        setBorderMaterial(Material.GRAY_STAINED_GLASS_PANE);

        buttonSearch = fluentUI
                .buttonBuilder()
                .setObserver(searchManager.getButtonObserver())
                .setLocation(0, 0)
                .setDescription(options ->
                {
                    options.setTitle(translator.get("gui.base.search.title"));
                    options.setOnLeftClick(translator.get("gui.base.search.desc.left-click"));
                    options.setOnRightClick(translator.get("gui.base.search.desc.right-click"));
                    options.setOnShiftClick(translator.get("gui.base.search.desc.shift-click"));
                })
                .setMaterial(Material.SPYGLASS)
                .setOnLeftClick((player, button) ->
                {
                    if (!searchManager.hasProfiles()) {
                        return;
                    }
                    close();
                    FluentMessage.message().inBrackets("Enter search key", ChatColor.AQUA).send(player);
                    EventsListenerInventoryUI.registerTextInput(player, searchedKey ->
                    {
                        addContentFilter(input ->
                        {
                            return searchManager.search(searchedKey, input, player);
                        });
                        applyFilters();
                        open(player);
                    });
                })
                .setOnShiftClick((player, button) ->
                {
                    resetFilter();
                })
                .build(this);

        buttonPageDown = fluentUI
                .buttonBuilder()
                .setLocation(getHeight() - 1, 3)
                .setDescription(options ->
                {
                    options.setTitle(translator.get("gui.base.page-down.title"));
                })
                .setMaterial(Material.ARROW)
                .setOnLeftClick((player, button) ->
                {
                    listContentManager.lastPage();
                })
                .build(this);

        buttonPageUp = fluentUI
                .buttonBuilder()
                .setLocation(getHeight() - 1, 5)
                .setDescription(options ->
                {
                    options.setTitle(translator.get("gui.base.page-up.title"));
                })
                .setMaterial(Material.ARROW)
                .setOnLeftClick((player, button) ->
                {
                    listContentManager.nextPage();
                })
                .build(this);

        buttonExit = fluentUI.buttonFactory()
                .back(this)
                .setOnLeftClick((player, button) ->
                {
                    openParent();
                }).build(this);
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

    public final void addSearchStrategy(String name, SearchFilterEvent<T> event) {
        searchManager.addSearchProfile(name, event);
    }

    public void setListTitlePrimary(String value) {
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

    protected final FluentTranslator getTranslator() {
        return translator;
    }

    protected final FluentChestUI getFluentUI() {
        return fluentUI;
    }
}
