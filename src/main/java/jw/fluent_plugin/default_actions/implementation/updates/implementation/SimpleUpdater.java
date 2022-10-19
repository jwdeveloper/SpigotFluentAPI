package jw.fluent_plugin.default_actions.implementation.updates.implementation;

import jw.fluent_api.minecraft.logger.FluentLogger;
import jw.fluent_api.minecraft.messages.FluentMessage;
import jw.fluent_api.minecraft.messages.message.MessageBuilder;
import jw.fluent_plugin.FluentPlugin;
import jw.fluent_plugin.default_actions.implementation.updates.api.data.UpdateDto;
import jw.fluent_api.minecraft.tasks.FluentTasks;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Consumer;


public class SimpleUpdater {
    private String github;
    private String command;
    private FluentPlugin plugin;
    private CommandSender sender;

    public SimpleUpdater(UpdateDto dto, FluentPlugin plugin) {
        this.github = dto.getGithub();
        this.command = dto.getUpdateCommandName();
        this.plugin = plugin;
    }


    public void checkUpdate(Consumer<Boolean> consumer) {
        var currentVersion = plugin.getDescription().getVersion();
        var releaseUrl = github + "/releases/latest";
        FluentTasks.taskAsync(unused ->
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
                FluentLogger.error("Checking for update error", e);
            }
        });
    }

    public void checkUpdate(ConsoleCommandSender commandSender) {
        sender = commandSender;
        checkUpdate(aBoolean ->
        {
            if (aBoolean == true) {
                message().text("New version available, use " + ChatColor.AQUA + "/" + command + " update"+ ChatColor.RESET + " to download").send(sender);
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
        var msg = FluentMessage.message().inBrackets(FluentPlugin.getPlugin().getName());
        return msg.space().color(ChatColor.AQUA).inBrackets("Update info").color(ChatColor.GRAY).space();
    }

    private String getUpdatesFolder() {
        return FluentPlugin.getPluginFile().getParentFile().getAbsolutePath() + File.separator + "update" + File.separator;
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
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        }
        return result.toString();
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
            yourFile.createNewFile(); // if file already exists will do nothing
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
            FluentLogger.error("Update download error", e);
        }
    }
}
