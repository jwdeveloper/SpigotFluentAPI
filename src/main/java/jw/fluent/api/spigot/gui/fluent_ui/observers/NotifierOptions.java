package jw.fluent.api.spigot.gui.fluent_ui.observers;

import lombok.Getter;

import java.util.UUID;

public class NotifierOptions
{
    @Getter
    private String id = UUID.randomUUID().toString();
}
