package jw.spigot_fluent_api.gui_v2.test;

import jw.spigot_fluent_api.gui_v2.button.button_observer.ButtonObserver;
import jw.spigot_fluent_api.utilites.binding.Observable;
import jw.spigot_fluent_api.utilites.messages.MessageBuilder;
import org.bukkit.Material;

public class DataViewModel {
    public Integer _value = 0;
    public Observable<Integer> value;

    public DataViewModel() {
        value = new Observable<>(this, "_value");
    }

    public ButtonObserver ButtonIncrease() {
        return ButtonObserver.<Integer>builder()
                .observable(value)
                .onValueChange(event ->
                {
                    event.getButton()
                            .setDescription(new MessageBuilder()
                                    .field("Current value", event.getValue()));
                })
                .onClick(event ->
                {
                    event.getBindingStrategy().setValue(event.getValue() + 1);
                })
                .build();
    }

    public ButtonObserver ButtonDisplay() {
        return ButtonObserver.<Integer>builder()
                .observable(value)
                .onValueChange(event ->
                {
                    var value = event.getValue()%3;
                    switch (value)
                    {
                        case 0:
                            event.getButton().setMaterial(Material.DIAMOND);
                            break;
                        case 1:
                            event.getButton().setMaterial(Material.GOLD_INGOT);
                            break;
                        case 2:
                            event.getButton().setMaterial(Material.IRON_AXE);
                            break;
                    }
                }).build();

    }


}
