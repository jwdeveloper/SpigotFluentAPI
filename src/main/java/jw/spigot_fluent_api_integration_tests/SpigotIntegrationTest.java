package jw.spigot_fluent_api_integration_tests;

import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_message.message.MessageBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public abstract class SpigotIntegrationTest {
    private List<Method> tests;

    private int piority = 0;

    public SpigotIntegrationTest() {
        tests = new ArrayList<>();
    }

    public abstract void beforeTests();

    public void afterTests() {

    }

    public final void loadTests() {
        FluentPlugin.logSuccess("Test: " + this.getClass().getSimpleName() + " loaded");
        try {
            var methods = this.getClass().getDeclaredMethods();
            for (var m : methods) {
                if (m.isAnnotationPresent(SpigotTest.class)) {
                    tests.add(m);
                }
            }
        } catch (Exception e) {

            FluentLogger.error("Test: " + this.getClass().getSimpleName() + " not loaded", e);
        }
    }


    public final void runTests() {
        try {
            beforeTests();
        } catch (Exception e) {
            FluentLogger.error("can lunch beforeRun method", e);
        }
        for (var test : tests) {
            try {
                var start = System.nanoTime();
                test.invoke(this);
                var finish = System.nanoTime();
                var result = finish - start;
                var inMS = result / Math.pow(10, 6);
                sendLog(test, messageSuccess(inMS).toString());
            } catch (Exception e) {
                sendLog(test, messageError(e).toString());
            }
        }
        try {
            afterTests();
        } catch (Exception e) {
            FluentLogger.error("can afterTests  method", e);
        }
    }

    private void sendLog(Method method, String message) {
        new MessageBuilder()
                .color(ChatColor.GREEN)
                .inBrackets("Spigot integration test")
                .space()
                .color(ChatColor.WHITE)
                .text(getTestName(method))
                .space()
                .color(ChatColor.WHITE)
                .text(message)
                .send();
    }


    private MessageBuilder messageSuccess(double time) {
        return new MessageBuilder()
                .color(ChatColor.GREEN)
                .inBrackets("passed")
                .color(ChatColor.WHITE)
                .space()
                .text("in " + time + "ms")
                .color(ChatColor.RESET);
    }

    private MessageBuilder messageError(Exception e) {

        var file = e.getCause().getStackTrace()[1];
        var line = file.getLineNumber();
        var method = file.getMethodName();
        return new MessageBuilder()
                .color(ChatColor.DARK_RED)
                .inBrackets("not passed")
                .color(ChatColor.RESET)
                .newLine()
                .color(ChatColor.DARK_RED)
                .inBrackets("Reason")
                .color(ChatColor.YELLOW)
                .space()
                .text(e.getCause().getMessage())
                .space()
                .text("at " + method + " on line " + line)
                .color(ChatColor.RESET);
    }

    public Player getExamplePlayer() throws Exception {
        var players = Bukkit.getOnlinePlayers();
        if (players.size() == 0) {
            throw new Exception("Should be minimum one player on the server to run test");
        }
        return players.stream().findAny().get();
    }

    public void setPiority(int piority) {
        this.piority = piority;
    }

    public int getPiority() {
        return this.piority;
    }

    private String getTestName(Method method) {
        return this.getClass().getSimpleName() + "." + method.getName();
    }
}
