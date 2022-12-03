package jw.fluent_api.updater.implementation;

import jw.fluent_api.spigot.messages.FluentMessage;
import jw.fluent_api.spigot.messages.message.MessageBuilder;
import jw.fluent_api.updater.api.UpdaterOptions;
import jw.fluent_api.utilites.files.FileUtility;
import jw.fluent_api.utilites.java.StringUtils;
import jw.fluent_plugin.implementation.FluentApi;
import jw.fluent_plugin.implementation.modules.logger.FluentLogger;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;


public class SimpleUpdater {
    private String github;
    private String command;
    private JavaPlugin plugin;
    private CommandSender sender;
    private String baseCommandName;

    public SimpleUpdater(UpdaterOptions dto, JavaPlugin plugin, String baseCommmadName) {
        this.github = dto.getGithub();
        this.command = "update";
        this.plugin = plugin;
        this.baseCommandName = baseCommmadName;
    }


    public void checkUpdate(Consumer<Boolean> consumer) {
        if (github.equals(StringUtils.EMPTY_STRING)) {
            FluentApi.logger().warning("Updater", "Download url could not be empty");
            return;
        }
        var currentVersion = plugin.getDescription().getVersion();
        var releaseUrl = github + "/releases/latest";
        FluentApi.spigot().tasks().taskAsync(unused ->
        {
            try {
                var html = getHTML(releaseUrl);
                var latestVersion = getLatestVersion(html);
                if (latestVersion.equals(currentVersion)) {
                    consumer.accept(false);
                    return;
                }
                consumer.accept(true);

            } catch (Exception e) {
                FluentApi.logger().error("Checking for update error", e);
            }
        });
    }

    public void checkUpdate(ConsoleCommandSender commandSender) {
        sender = commandSender;
        checkUpdate(aBoolean ->
        {
            if (aBoolean == true) {
                message().text("New version available, use " + ChatColor.AQUA + "/" + baseCommandName + " update" + ChatColor.RESET + " to download").send(sender);
                message().text("Check out changes ").text(github + "/releases/latest", ChatColor.AQUA).send(sender);
            }
        });
    }

    public void downloadUpdate(CommandSender commandSender) {

        this.sender = commandSender;
        message().text("Checking version...").send(sender);
        checkUpdate(aBoolean ->
        {
            if (aBoolean == false) {
                message().text("Latest version is already downloaded").send(sender);
                return;
            }
            var pluginName = plugin.getName();
            downloadCurrentVersion(pluginName);
        });
    }

    private MessageBuilder message() {
        var msg = FluentMessage.message().inBrackets(FluentApi.plugin().getName());
        return msg.space().color(ChatColor.AQUA).inBrackets("Update info").color(ChatColor.GRAY).space();
    }

    private String getUpdatesFolder() {
        return FileUtility.pluginPath(FluentApi.plugin()) + File.separator + "update" + File.separator;
    }

    private String getLatestVersion(String html) {

        var comIndex = github.indexOf("com");
        var repoName = github.substring(comIndex + 3);
        var startIndex = html.indexOf("ref_page:" + repoName);
        var endIndex = html.indexOf(";", startIndex);

        var all = html.substring(startIndex, endIndex);
        var index = all.lastIndexOf("/");
        var latestVersion = all.substring(index + 1);

        return latestVersion;
    }

    private String getHTML(String urlToRead) throws Exception {
        var stringBuilder = new StringBuilder();
        var url = new URL(urlToRead);
        var conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream())))
        {
            var line = StringUtils.EMPTY_STRING;
            while (true)
            {
                line = reader.readLine();
                if(line == null)
                {
                    break;
                }
                stringBuilder.append(line);
            }
        }
        catch (Exception e)
        {
            FluentLogger.LOGGER.error("Unable to get html",e);
        }
        return stringBuilder.toString();
    }

    private void downloadCurrentVersion(String pluginName) {
        var output = getUpdatesFolder() + pluginName + ".jar";
        var download = github + "/releases/latest/download/" + pluginName + ".jar";
        try {

            message().text("Downloading latest version...")
                    .send(sender);
            var in = new BufferedInputStream(new URL(download).openStream());
            var yourFile = new File(output);
            yourFile.getParentFile().mkdirs();
            yourFile.createNewFile(); // if path already exists will do nothing
            var fileOutputStream = new FileOutputStream(output, false);
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            message().text("New version downloaded! use ")
                    .text("/reload", ChatColor.AQUA).color(ChatColor.GRAY)
                    .text(" to apply changes")
                    .send(sender);
        } catch (Exception e) {
            FluentApi.logger().error("Update download error", e);
        }
    }
}
