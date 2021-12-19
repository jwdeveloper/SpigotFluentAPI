package jw.spigot_fluent_api.gui_legacy.button;

import org.bukkit.Material;

public class ButtonAnimated
{
    private Material[] frames;
    private int refreshEveryTick;
    private boolean runOnOpen;


    public ButtonAnimated(Material... frames)
    {
        this.frames = frames;
    }

    public ButtonAnimated(int refreshEveryTick, Material... frames)
    {
        this(frames);
        this.refreshEveryTick =refreshEveryTick;
    }

    public ButtonAnimated(int refreshEveryTick, boolean runOnOpen, Material... frames)
    {
        this(refreshEveryTick,frames);
        this.runOnOpen =runOnOpen;
    }

    public boolean isRunOnOpen() {
        return runOnOpen;
    }

    public void setRunOnOpen(boolean runOnOpen) {
        this.runOnOpen = runOnOpen;
    }

    public int getRefreshEveryTick() {
        return refreshEveryTick;
    }

    public void setRefreshEveryTick(int refreshEveryTick) {
        this.refreshEveryTick = refreshEveryTick;
    }

    public Material[] getFrames() {
        return frames;
    }

    public void setFrames(Material[] frames) {
        this.frames = frames;
    }
}
