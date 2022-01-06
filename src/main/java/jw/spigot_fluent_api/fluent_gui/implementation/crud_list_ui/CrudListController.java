package jw.spigot_fluent_api.fluent_gui.implementation.crud_list_ui;

import jw.spigot_fluent_api.fluent_gui.button.ButtonUI;
import jw.spigot_fluent_api.fluent_gui.button.button_observer.ButtonObserver;
import jw.spigot_fluent_api.fluent_gui.button.button_observer.ButtonObserverBuilder;
import jw.spigot_fluent_api.fluent_gui.button.button_observer.ButtonObserverEvent;
import jw.spigot_fluent_api.fluent_gui.events.ButtonUIEvent;
import jw.spigot_fluent_api.fluent_gui.implementation.list_ui.ListUI;
import jw.spigot_fluent_api.utilites.binding.Observable;
import jw.spigot_fluent_api.utilites.messages.MessageBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class CrudListController<T> {
    private final Material DEFAULT_BACKGROUND = Material.GRAY_STAINED_GLASS_PANE;
    private final ListUI<T> listUI;

    private final Observable<CrudListState> currentState;
    private CrudListState _currentState;

    public ButtonUIEvent onDeleteEvent = (a, b) -> {
    };
    public ButtonUIEvent onEditEvent = (a, b) -> {
    };
    public ButtonUIEvent onInsertEvent = (a, b) -> {
    };
    public ButtonUIEvent onGetEvent = (a, b) -> {
    };

    public CrudListController(ListUI<T> listUI) {
        _currentState = CrudListState.None;
        currentState = new Observable<CrudListState>(this, "_currentState");
        this.listUI = listUI;
    }

    public void setState(CrudListState lastState) {
        currentState.set(lastState);
        listUI.refreshButtons();
    }

    public CrudListState getState() {
        return currentState.get();
    }


    public ButtonObserver<?> deleteObserver() {
        return baseObserver(CrudListState.Delete, event ->
        {
            listUI.setBorderMaterial(Material.RED_STAINED_GLASS_PANE);
        }).build();
    }

    public ButtonObserver<?> editObserver() {
        return baseObserver(CrudListState.Edit, event ->
        {
            listUI.setBorderMaterial(Material.YELLOW_STAINED_GLASS_PANE);
        }).build();
    }

    public ButtonObserver<?> insertObserver() {
        return ButtonObserver.<CrudListState>builder()
                .withObserver(currentState)
                .onClick(event ->
                {
                    //to do insert
                }).build();
    }

    public ButtonObserver<?> cancelObserver() {
        return baseObserver(CrudListState.None, null)
                .onValueChange(event ->
                {
                    if (event.getValue() == CrudListState.None)
                    {
                        listUI.setBorderMaterial(DEFAULT_BACKGROUND);
                        event.getButton().setMaterial(DEFAULT_BACKGROUND);
                        event.getButton().setTitle(" ");
                    } else {
                        var color = switch (event.getValue())
                                {
                                    case Delete -> ChatColor.RED;
                                    case Edit -> ChatColor.YELLOW;
                                    default -> ChatColor.GRAY;
                                };
                        event.getButton().setMaterial(Material.PLAYER_HEAD);
                        event.getButton().setTitle(new MessageBuilder()
                                .color(color)
                                .inBrackets("Cancel")
                                .toString());
                    }
                }).build();
    }

    private ButtonObserverBuilder<CrudListState> baseObserver(CrudListState listState, Consumer<ButtonObserverEvent<CrudListState>> observerEvent) {
        return ButtonObserver.<CrudListState>builder()
                .withObserver(currentState)
                .onClick(event ->
                {
                    if (event.getValue() != listState) {
                        event.getObserver().setValue(listState);
                    }
                })
                .onValueChange(event ->
                {
                    if (event.getValue() == listState) {
                        observerEvent.accept(event);
                    }
                });
    }


    public void selectHandler(Player player, ButtonUI buttonUI) {
        var state = getState();
        switch (state) {
            case Delete -> onDeleteEvent.execute(player, buttonUI);
            case Edit -> onEditEvent.execute(player, buttonUI);
            case Create -> onInsertEvent.execute(player, buttonUI);
            case None -> onGetEvent.execute(player, buttonUI);
        }
        setState(CrudListState.None);
    }

}
