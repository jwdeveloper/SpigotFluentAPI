package jw.spigot_fluent_api.legacy_gui.chest_gui.implementations.list_gui;


import jw.spigot_fluent_api.legacy_gui.button.Button;
import jw.spigot_fluent_api.legacy_gui.button.ButtonActionsEnum;
import jw.spigot_fluent_api.legacy_gui.InventoryGUI;
import jw.spigot_fluent_api.legacy_gui.chest_gui.implementations.list_gui.action_button.*;
import jw.spigot_fluent_api.legacy_gui.chest_gui.ChestGUI;
import jw.spigot_fluent_api.legacy_gui.chest_gui.implementations.button.ButtonMapper;
import jw.spigot_fluent_api.legacy_gui.chest_gui.implementations.utilites.ListGUIPagination;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;


public class ListGUI<T> extends ChestGUI<T> implements IListGUI<T> {

    protected ListGUIPagination pagination;
    protected ActionButtonManager actionButtonManager;
    private int maxItemsOnPage;
    private Material backgroundMaterial;
    protected ActionButtonManager actionManager;

    public EditButton editButton;
    public CopyButton copyButton;
    public InsertButton insertButton;
    public RightArrowButton rightArrowButton;
    public LeftArrowButton leftArrowButton;
    public ExitButton exitButton;
    public CancelButton cancelButton;

    public ListGUI(InventoryGUI parent, String name, int size, Class<T> tClass) {
        super(parent, name, size, tClass);
        initListGUI();
    }

    public ListGUI(InventoryGUI parent, String name, int height) {
        super(parent,name,height);
        initListGUI();
    }

    @Override
    public void onInitialize() {
    }

    public void initListGUI()
    {
        maxItemsOnPage = 7 * (height - 2);
        backgroundMaterial = Material.BLACK_STAINED_GLASS_PANE;
        pagination = new ListGUIPagination<T>(maxItemsOnPage);
        actionButtonManager = new ActionButtonManager(this);
        drawBorder(this.backgroundMaterial);

        editButton = new EditButton(this);
        actionButtonManager.addActionButton(editButton);
        this.addButton(editButton.getButton());

        cancelButton = new CancelButton(this);
        actionButtonManager.addActionButton(cancelButton);
        this.addButton(cancelButton.getButton());

        exitButton = new ExitButton(this);
        actionButtonManager.addActionButton(exitButton);
        this.addButton(exitButton.getButton());
    }

    @Override
    public void onClick(Player player, Button button) {

        this.actionManager.onClick(player,button);
    }

    @Override
    public void onClose(Player player) {
        this.triggerAction(ButtonActionsEnum.CANCEL);
    }

    @Override
    public void setBackgroundMaterial(Material material) {
        this.backgroundMaterial = material;
        this.drawBorder(this.backgroundMaterial);
    }
    public Material getBackgroundMaterial()
    {
        return this.backgroundMaterial;
    }

    @Override
    public void addButtons(List<T> data, ButtonMapper<T> buttonMapper) {
        this.pagination.setContent(data);
        this.pagination.setButtonMapper(buttonMapper);
        displayPaginationResult();
    }
    @Override
    public void clearContentButtons() {
        for (int j = 1; j < this.height - 1; j++) {
            for (int i = 1; i <= 7; i++) {
                this.addButton(null, j, i);
            }
        }
    }
    @Override
    public void nextPage() {
        if (pagination.canNextPage()) {
            pagination.nextPage();
            displayPaginationResult();
            displayLog("Next page " + pagination.getCurrentPage(), ChatColor.GREEN);
        }
    }

    public void triggerAction(ButtonActionsEnum actionsEnum)
    {
        this.actionManager.setCurrentAction(actionsEnum);
    }

    @Override
    public void backPage() {
        if (pagination.canBackPage()) {
            pagination.backPage();
            displayPaginationResult();
            displayLog("Back page " + pagination.getCurrentPage(), ChatColor.GREEN);
        }
    }

    public void setPagination(ListGUIPagination pagination) {
        this.pagination = pagination;
    }
    private void setPageName() {
        if (isTitleSet)
            return;
        if (this.pagination.getPagesCount() > 1)
            this.setName(this.name + " " + ChatColor.DARK_GRAY + "[" + pagination.getCurrentPage() + "/" + pagination.getPagesCount() + "]");
        else
            this.setName(this.name + " " + ChatColor.DARK_GRAY);
    }

    public void displayPaginationResult() {
        Button[] buttons = this.pagination.getButtons();
        for (Button button : buttons) {
            this.addButton(button);
        }
    }

    private void actionPlaceHolder(Player player, Button button) {
        this.displayLog("Acction not init " + button.getAction(), ChatColor.YELLOW);
    }
}