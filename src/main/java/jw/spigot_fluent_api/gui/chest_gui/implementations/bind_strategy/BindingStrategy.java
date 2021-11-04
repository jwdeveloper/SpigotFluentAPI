package jw.spigot_fluent_api.gui.chest_gui.implementations.bind_strategy;


import jw.spigot_fluent_api.gui.button.Button;
import jw.spigot_fluent_api.gui.events.ButtonEvent;
import jw.spigot_fluent_api.gui.chest_gui.ChestGUI;
import jw.spigot_fluent_api.gui.chest_gui.implementations.bind_strategy.interfaces.OnChangeEvent;
import jw.spigot_fluent_api.gui.chest_gui.implementations.bind_strategy.interfaces.OnClickEvent;
import jw.spigot_fluent_api.utilites.binding.BindingField;
import org.bukkit.entity.Player;

public class BindingStrategy<T> implements ButtonEvent {

    protected ChestGUI chestGUI;
    protected Button button;
    protected BindingField<T> bindingField;
    public OnChangeEvent<T> onChangeEvent = this::onValueChanged;
    public OnClickEvent<T> onClickEvent = this::onClick;

    public BindingStrategy(Button button,ChestGUI chestGUI,BindingField<T> bindingField)
    {
      setBindingField(bindingField);
      setButton(button);
      setChestGUI(chestGUI);
    }
    public BindingStrategy(BindingField<T> bindingField)
    {
        setBindingField(bindingField);
    }
    public BindingStrategy()
    {

    }
    @Override
    public void Execute(Player player, Button button)
    {
        if(bindingField.isBinded())
             return;
        onClickEvent.OnClick(player, button,this, getValue());
    }
    public void onChange(T newValue)
    {
        if(bindingField.isBinded() || !chestGUI.isOpen())
            return;

        onChangeEvent.OnValueChanged(chestGUI, button, newValue);
        chestGUI.refreshButton(button);
    }

    protected void onClick(Player player, Button button,BindingStrategy<T> bindingStrategy, T currentValue)
    {
    }
    protected void onValueChanged(ChestGUI inventoryGUI, Button button, T newValue)
    {
    }

    public void setOnChangeEvent(OnChangeEvent onChangeEvent)
    {
        this.onChangeEvent = onChangeEvent;
    }
    public void setOnClickEvent(OnClickEvent onClickEvent)
    {
        this.onClickEvent = onClickEvent;
    }
    public void setButton(Button button)
    {
        this.button = button;
    }
    public void setChestGUI(ChestGUI chestGUI)
    {
        this.chestGUI = chestGUI;
        chestGUI.addBindStrategy(this);
    }
    public void setBindingField(BindingField<T> bindingField)
    {
        this.bindingField = bindingField;
        this.bindingField.onChange(this::onChange);
    }
    public  T getValue()
    {
        return  bindingField.get();
    }
    public void setValue(T newValue)
    {
        bindingField.set(newValue);
    }
    public Button getButton()
    {
        return button;
    }
    public String getFieldName(){ return  bindingField.getFieldName();}
    public void setObject(T object)
    {
        this.bindingField.setObject(object);
    }

}
