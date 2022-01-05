package jw.spigot_fluent_api.legacy_gui.chest_gui.implementations.list_gui;

import jw.spigot_fluent_api.legacy_gui.button.Button;
import jw.spigot_fluent_api.legacy_gui.button.ButtonActionsEnum;
import jw.spigot_fluent_api.legacy_gui.chest_gui.implementations.list_gui.action_button.ActionButton;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.utilites.binding.BindingObject;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.function.Consumer;

public class ActionButtonManager {
    private BindingObject<ButtonActionsEnum> currentAction = new BindingObject<>();
    private ButtonActionsEnum lastAction = ButtonActionsEnum.EMPTY;
    private HashMap<ButtonActionsEnum, ActionButton> actionButtons = new HashMap<>();

    private ListGUI listGUI;

    public ActionButtonManager(ListGUI listGUI) {
        this.listGUI = listGUI;
        actionButtons = new HashMap<>();

        currentAction.set(ButtonActionsEnum.EMPTY);
        currentAction.onChange(onActionSelect());
    }

    public ActionButton addActionButton(ActionButton actionButton)
    {
        if(actionButtons.containsKey(actionButton.getAction()))
        {
            FluentPlugin.logError(listGUI.toString()+" already contains "+actionButton.getAction()+" action");
            return actionButton;
        }
        actionButtons.put(actionButton.getAction(),actionButton);
        return  actionButton;
    }

    public void setCurrentAction(ButtonActionsEnum action)
    {
        lastAction = this.currentAction.get();
        this.currentAction.set(action);

    }

    private Consumer<ButtonActionsEnum> onActionSelect() {
        return (action) ->
        {
            if(currentAction.get() == action)
                return;
            if(!actionButtons.containsKey(action))
                return;

            listGUI.displayLog("Set current action: " + action.name(), ChatColor.GREEN);
            var actionButton = actionButtons.get(currentAction.get());
            actionButton.getButton().getOnClick().Execute(null,actionButton.getButton());
        };
    }

    public void onClick(Player player,Button button)
    {
        if (button.getAction() != ButtonActionsEnum.CLICK ||
            currentAction.get() == ButtonActionsEnum.EMPTY)
            return;
        var actionButton = actionButtons.get(currentAction.get());
        actionButton.onActionTriggered(player,button);
    }



}
