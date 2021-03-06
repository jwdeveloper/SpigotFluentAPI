package jw.spigot_fluent_api.fluent_gui.implementation.list_ui;

import jw.spigot_fluent_api.fluent_gui.button.ButtonUI;
import jw.spigot_fluent_api.fluent_gui.button.button_observer.ButtonObserverUI;
import jw.spigot_fluent_api.fluent_gui.events.ButtonUIEvent;
import jw.spigot_fluent_api.fluent_gui.implementation.chest_ui.ChestUI;
import jw.spigot_fluent_api.fluent_gui.implementation.list_ui.content_manger.ButtonUIMapper;
import jw.spigot_fluent_api.fluent_gui.implementation.list_ui.content_manger.FilterContentEvent;
import jw.spigot_fluent_api.fluent_message.MessageBuilder;
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
        listContentManager = new ListUIManager<>(this);
        loadSpecialButtons();
    }

    protected void loadSpecialButtons() {

        setBorderMaterial(Material.GRAY_STAINED_GLASS_PANE);
        buttonSearch = ButtonObserverUI
                .builder()
                .setLocation(0, 0)
                .setTitle(new MessageBuilder().color(ChatColor.GRAY).inBrackets("Search"))
                .setMaterial(Material.SPYGLASS)
                .setOnClick((player, button) ->
                {
                    player.sendMessage("Not implemented yet...");
                })
                .buildAndAdd(this);

        buttonPageDown = ButtonObserverUI
                .builder()
                .setLocation(getHeight() - 1, 3)
                .setTitle(new MessageBuilder().color(ChatColor.GRAY).inBrackets("Page down"))
                .setMaterial(Material.ARROW)
                .setOnClick((player, button) ->
                {
                    listContentManager.lastPage();
                })
                .buildAndAdd(this);

        buttonPageUp = ButtonObserverUI
                .builder()
                .setLocation(getHeight() - 1, 5)
                .setTitle(new MessageBuilder().color(ChatColor.GRAY).inBrackets("Page up"))
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
        for (var event : onListOpen) {
            event.accept(player);
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

    public void onListOpen(Consumer<Player> event) {
        onListOpen.add(event);
    }

    public void onContentClick(ButtonUIEvent event) {
        onClickContent.add(event);
    }

    public void onListClose(Consumer<Player> event) {
        onListClose.add(event);
    }
}
