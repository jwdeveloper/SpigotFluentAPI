package jw.spigot_fluent_api.gui.chest_gui.implementations;




import jw.spigot_fluent_api.gui.button.Button;
import jw.spigot_fluent_api.gui.button.ButtonActionsEnum;
import jw.spigot_fluent_api.gui.button.ButtonFactory;
import jw.spigot_fluent_api.gui.InventoryGUI;
import jw.spigot_fluent_api.gui.events.InventoryEvent;
import jw.spigot_fluent_api.gui.chest_gui.ChestGUI;
import jw.spigot_fluent_api.gui.chest_gui.implementations.button.ButtonMapper;
import jw.spigot_fluent_api.gui.chest_gui.implementations.utilites.ListGUIPagination;
import jw.spigot_fluent_api.utilites.binding.BindingObject;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionType;

import java.util.List;
import java.util.function.Consumer;

public class ListGUI<T> extends ChestGUI<T> {

    public Consumer<String> onInsert = (a) -> {
    };
    public InventoryEvent onClickContent = this::actionPlaceHolder;

    public InventoryEvent onSelect = this::actionPlaceHolder;

    public InventoryEvent onDelete = this::actionPlaceHolder;

    public InventoryEvent onCopy = this::actionPlaceHolder;

    public InventoryEvent onEdit = this::actionPlaceHolder;

    protected ListGUIPagination pagination;
    protected BindingObject<ButtonActionsEnum> currentAcction = new BindingObject<>();

    private Material backgroundMaterial = Material.BLACK_STAINED_GLASS_PANE;
    private Button insertButton;
    private Button searchButton;
    private Button deleteButton;
    private Button copyButton;
    private Button cancelButton;
    private int maxItemsOnPage;

    public ListGUI(InventoryGUI parent, String name, int size) {
        super(parent, name, size);
        setUpGUI();
    }

    public ListGUI(InventoryGUI parent, String name, int size, Class<T> tClass) {
        super(parent, name, size, tClass);
        setUpGUI();
    }

    @Override
    public void onInitialize() {

    }

    private void actionPlaceHolder(Player player, Button button) {
        this.displayLog("Acction not init " + button.getAction(), ChatColor.YELLOW);
    }

    @Override
    public void onClick(Player player, Button button) {
        if (button.getAction() == ButtonActionsEnum.CLICK) {
            this.displayLog("Click, current action " + this.currentAcction.get().name(), ChatColor.GREEN);
            switch (this.currentAcction.get()) {
                case EDIT -> {
                    this.playSound(Sound.UI_TOAST_IN);
                    this.onEdit.Execute(player, button);
                    this.displayPaginationResult();
                    this.cancelAction();
                    this.refresh();
                }
                case DELETE -> {
                    this.playSound(Sound.UI_TOAST_IN);
                    this.onDelete.Execute(player, button);
                    this.displayPaginationResult();
                    this.cancelAction();
                    this.refresh();
                }
                case COPY -> {
                    this.playSound(Sound.BLOCK_SLIME_BLOCK_PLACE);
                    this.onCopy.Execute(player, button);
                    this.displayPaginationResult();
                    this.cancelAction();
                    this.refresh();
                }
                case CANCEL -> {
                    this.playSound(Sound.UI_TOAST_OUT);
                    cancelAction();
                    this.refresh();
                }
                case GET -> {
                    this.onSelect.Execute(player, button);
                    this.cancelAction();
                }
                default -> this.onClickContent.Execute(player, button);
            }
        }
    }

    public Button getInsertButton() {
        if (this.insertButton != null)
            return this.insertButton;

        insertButton = ButtonFactory.insertButton();
        insertButton.setSound(Sound.UI_CARTOGRAPHY_TABLE_TAKE_RESULT);
        insertButton.setPosition(0, 6);
        insertButton.setOnClick((a, b) ->
        {
            cancelAction();
            this.openTextInput(ChatColor.BOLD + "Enter name", input ->
            {
                onInsert.accept(input);
            });
        });
        return insertButton;
    }

    public Button getSearchButton() {
        if (this.searchButton != null)
            return this.searchButton;
        searchButton = ButtonFactory.searchButton();
        searchButton.setPosition(0, 0);
        searchButton.setOnClick((a, b) ->
        {
            cancelAction();
            this.openTextInput("Enter value on chat", input ->
            {
                // this.filterContent(input);
                this.getCancelButton().setActive(true);
            });
        });
        return searchButton;
    }

    public void setBackgroundMaterial(Material material)
    {
        this.backgroundMaterial = material;
        this.drawBorder(this.backgroundMaterial);
    }

    public Button getDeleteButton() {
        if (this.deleteButton != null)
            return this.deleteButton;

        deleteButton = ButtonFactory.deleteButton();
        deleteButton.setPosition(0, 8);
        deleteButton.setOnClick((a, b) ->
        {
            this.setCurrentAction(ButtonActionsEnum.DELETE);
            this.getCancelButton().setActive(false);
            this.drawBorder(Material.RED_STAINED_GLASS_PANE);

            if (!isTitleSet)
                this.setName(this.name + ChatColor.DARK_RED + " [Delete]");
            else
                this.refresh();
        });
        return deleteButton;
    }

    public Button getCopyButton() {
        if (this.copyButton != null)
            return this.copyButton;

        copyButton = ButtonFactory.copyButton();
        copyButton.setPosition(0, 5);
        copyButton.setOnClick((a, b) ->
        {
            this.setCurrentAction(ButtonActionsEnum.COPY);
            this.drawBorder(Material.LIME_STAINED_GLASS_PANE);
            if (!isTitleSet)
                this.setName(this.name + ChatColor.DARK_GREEN + " [Copy]");
            else
                this.refresh();
        });
        return copyButton;
    }

    public Button getCancelButton() {
        if (this.cancelButton != null)
            return this.cancelButton;

        cancelButton = ButtonFactory.cancelButton();
        cancelButton.setPosition(this.height - 1, 4);
        return cancelButton;
    }

    //Adding content

    public void clearButtons() {
        for (int j = 1; j < this.height - 1; j++) {
            for (int i = 1; i <= 7; i++) {
                this.addButton(null, j, i);
            }
        }
    }

    public void setPagination(ListGUIPagination pagination)
    {
        this.pagination = pagination;
    }


    public void addButtons(List<T> data, ButtonMapper<T> buttonMapper) {
        this.pagination.setContent(data);
        this.pagination.setButtonMapper(buttonMapper);
        displayPaginationResult();
    }

    public void addButtons(List<Button> buttons) {
        clearButtons();
        int index = 0;
        for (int j = 1; j < this.height - 1; j++) {
            for (int i = 1; i <= 7; i++) {
                this.addButton(buttons.get(index), j, i);
                index++;
            }
        }
    }

    //Pages

    public void nextPage() {
        if (pagination.canNextPage()) {
            pagination.nextPage();
            displayPaginationResult();
            displayLog("Next page " + pagination.getCurrentPage(), ChatColor.GREEN);
        }
    }

    public void backPage() {
        if (pagination.canBackPage()) {
            pagination.backPage();
            displayPaginationResult();
            displayLog("Back page " + pagination.getCurrentPage(), ChatColor.GREEN);
        }

    }

    private void setPageName() {
        if (isTitleSet)
            return;
        if (this.pagination.getPages() > 1)
            this.setName(this.name + " " + ChatColor.DARK_GRAY + "[" + pagination.getCurrentPage() + "/" + pagination.getPages() + "]");
        else
            this.setName(this.name + " " + ChatColor.DARK_GRAY);
    }

    //Events

    private void displayPaginationResult() {
        Button[] buttons = this.pagination.getButtons();
        for (Button button : buttons) {
            this.addButton(button);
        }
    }

    private void setUpGUI() {
        maxItemsOnPage = 7 * (height - 2);
        pagination = new ListGUIPagination<T>(maxItemsOnPage);
        currentAcction.set(ButtonActionsEnum.EMPTY);
        currentAcction.onChange(actionsEnum ->
        {
            if (actionsEnum == ButtonActionsEnum.EMPTY) {
                cancelButton.setMaterial(this.backgroundMaterial);
                cancelButton.setName(" ");
            } else {

            }
        });
        drawBorder(this.backgroundMaterial);

        Button exitButton = ButtonFactory.exitButton();
        exitButton.setPosition(this.height - 1, 8);
        exitButton.setOnClick((a, b) ->
        {
            cancelAction();
            this.openParent();
        });

        Button leftArrow = ButtonFactory.BackButton(PotionType.SLOWNESS);
        leftArrow.setPosition(this.height - 1, 3);
        leftArrow.setOnClick((a, b) ->
        {
            a.playSound(a.getLocation(), Sound.BLOCK_SNOW_BREAK, 1, 1);
            this.backPage();
            this.refresh();
        });
        Button rightArrow = ButtonFactory.nextButton(PotionType.SLOWNESS);
        rightArrow.setPosition(this.height - 1, 5);
        rightArrow.setOnClick((a, b) ->
        {
            a.playSound(a.getLocation(), Sound.BLOCK_SNOW_BREAK, 1, 1);
            this.nextPage();
            this.refresh();
        });


        this.addButton(exitButton);
        this.addButton(leftArrow);
        this.addButton(rightArrow);
        this.addButton(getCancelButton());
        this.addButton(getCopyButton());
        this.addButton(getDeleteButton());
        this.addButton(getInsertButton());
        this.addButton(getSearchButton());
    }

    private void cancelAction() {

        switch (this.currentAcction.get()) {
            case DELETE, COPY, EDIT -> {
                this.displayLog("Cancel action ", ChatColor.RED);
                this.setCurrentAction(ButtonActionsEnum.EMPTY);
                this.drawBorder(this.backgroundMaterial);
                setPageName();
            }
        }
    }

    private void setCurrentAction(ButtonActionsEnum action) {
        this.displayLog("Set current action: " + action.name(), ChatColor.GREEN);
        this.currentAcction.set(action);
    }


}