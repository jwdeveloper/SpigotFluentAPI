package jw.spigot_fluent_api.legacy_gui.chest_gui.implementations.bind_strategy;


import jw.spigot_fluent_api.legacy_gui.chest_gui.ChestGUI;
import jw.spigot_fluent_api.legacy_gui.chest_gui.implementations.bind_strategy.interfaces.OnChangeEvent;
import jw.spigot_fluent_api.legacy_gui.chest_gui.implementations.bind_strategy.interfaces.OnClickEvent;
import jw.spigot_fluent_api.utilites.binding.Observable;


public class BindingStrategyBuilder<T>
{
    BindingStrategy<T> bindingStrategy;

    public BindingStrategyBuilder()
    {
        bindingStrategy = new BindingStrategy<T>();
    }
    public BindingStrategyBuilder(Observable<T> bindingField)
    {
        this();
        this.setBindingFile(bindingField);
    }
    public BindingStrategyBuilder<T> setBindingFile(Observable<T> bindingField)
    {
        bindingStrategy.setBindingField(bindingField);
        return this;
    }
    public BindingStrategyBuilder<T> setOnClick(OnClickEvent<T> onClick)
    {
        bindingStrategy.onClickEvent = onClick;
        return this;
    }
    public BindingStrategyBuilder<T> setOnValueChange(OnChangeEvent<T> onChangeEvent)
    {
        bindingStrategy.onChangeEvent = onChangeEvent;
        return this;
    }
    public BindingStrategyBuilder<T> setButton(Observable<T> bindingField)
    {
        bindingStrategy.setBindingField(bindingField);
        return this;
    }
    public BindingStrategyBuilder<T> setGUI(ChestGUI chestGUI)
    {
        bindingStrategy.setChestGUI(chestGUI);
        return this;
    }

    public BindingStrategy<T> build()
    {
       return bindingStrategy;
    }
}
