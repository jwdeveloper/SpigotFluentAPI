package jw.fluent.plugin.api.extention;

public enum ExtentionPiority
{
    LOW(1), MEDIUM(2), HIGH(3);

    private final int level;

    private ExtentionPiority(int level)
    {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
