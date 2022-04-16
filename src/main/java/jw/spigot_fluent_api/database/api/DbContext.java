package jw.spigot_fluent_api.database.api;

public abstract class DbContext
{
    private final String url;

    public DbContext(String url)
    {
        this.url = url;
    }

    public abstract void onConfiguration();

    public abstract void saveChangesAsync();
}
