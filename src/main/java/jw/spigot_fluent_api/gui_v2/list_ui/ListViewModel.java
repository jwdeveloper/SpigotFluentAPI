package jw.spigot_fluent_api.gui_v2.list_ui;

import jw.spigot_fluent_api.gui_v2.button.ButtonUI;
import jw.spigot_fluent_api.gui_v2.button.button_observer.ButtonObserver;
import jw.spigot_fluent_api.gui_v2.button.button_observer.ButtonObserverBuilder;
import jw.spigot_fluent_api.gui_v2.button.button_observer.ButtonObserverEvent;
import jw.spigot_fluent_api.gui_v2.events.ButtonUIEvent;
import jw.spigot_fluent_api.utilites.binding.Observable;
import jw.spigot_fluent_api.utilites.messages.MessageBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class ListViewModel<T> {
    private final Material DEFAULT_BACKGROUND = Material.GRAY_STAINED_GLASS_PANE;
    private final ListUI<T> listUI;
    public ListState _currentState;
    private Observable<ListState> currentState;
    public ButtonUIEvent onDeleteEvent = (a, b) -> {
    };
    public ButtonUIEvent onEditEvent = (a, b) -> {
    };
    public ButtonUIEvent onInsertEvent = (a, b) -> {
    };
    public ButtonUIEvent onGetEvent = (a, b) -> {
    };

    public ListViewModel(ListUI<T> listUI) {
        _currentState = ListState.None;
        currentState = new Observable(this, "_currentState");
        this.listUI = listUI;
    }

    public void setState(ListState lastState) {
        currentState.set(lastState);
        listUI.refreshButtons();
    }

    public ListState getState() {
        return currentState.get();
    }


    public ButtonObserver deleteObserver() {
        return baseObserver(ListState.Delete, event ->
        {
            listUI.setBorder(Material.RED_STAINED_GLASS_PANE);
        }).build();
    }

    public ButtonObserver editObserver() {
        return baseObserver(ListState.Edit, event ->
        {
            listUI.setBorder(Material.YELLOW_STAINED_GLASS_PANE);
        }).build();
    }

    public ButtonObserver insertObserver() {
        return ButtonObserver.<ListState>builder()
                .observable(currentState)
                .onClick(event ->
                {
                    //to do insert
                }).build();
    }

    public ButtonObserver searchObserver() {
        return ButtonObserver.<ListState>builder()
                .observable(currentState)
                .onClick(event ->
                {
                    //to do insert
                }).build();
    }

    public ButtonObserver cancelObserver() {
        return baseObserver(ListState.None, null)
                .onValueChange(event ->
                {
                    if (event.getValue() == ListState.None)
                    {
                        listUI.setBorder(DEFAULT_BACKGROUND);
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

    private ButtonObserverBuilder<ListState> baseObserver(ListState listState, Consumer<ButtonObserverEvent<ListState>> observerEvent) {
        return ButtonObserver.<ListState>builder()
                .observable(currentState)
                .onClick(event ->
                {
                    if (event.getValue() != listState) {
                        event.getBindingStrategy().setValue(listState);
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
            case Delete:
                onDeleteEvent.Execute(player, buttonUI);
                break;
            case Edit:
                onEditEvent.Execute(player, buttonUI);
                break;
            case Create:
                onInsertEvent.Execute(player, buttonUI);
                break;
            case None:
                onGetEvent.Execute(player, buttonUI);
                break;
        }
        setState(ListState.None);
    }

}
