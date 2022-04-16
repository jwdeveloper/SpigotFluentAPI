package jw.spigot_fluent_api.fluent_logger.implementation;

import jw.spigot_fluent_api.fluent_message.message.MessageBuilder;
import org.bukkit.ChatColor;

import java.util.logging.Logger;

public class SimpleLogger {
    private static final String ERROR_BAR;

    static {
        ERROR_BAR = new MessageBuilder().newLine().color(ChatColor.BOLD).color(ChatColor.DARK_RED).bar("-", 100).newLine().build();
    }

    private Logger logger;


    public void warning(String message, String... params) {
        log(message, params);
    }

    public void success(String message, String... params) {
        log(message, params);
    }

    public void info(String message, String... params) {
        log(message, params);
    }

    public void log(String message, String... params)
    {
        var msg = new MessageBuilder();
        if(params.length == 0)
        {
            msg.text(message).sendToConsole();
            return;
        }
        msg.text(message);
        for (var param:params)
        {
         msg.space().text(param);
        }
        msg.sendToConsole();
    }

    public void error(String message, Exception exception) {
        var errorMessage = new MessageBuilder();
        var description = getErrorDescription(message, exception);
        var stackTrace = getStackTrace(exception);

        errorMessage
                .merge(description)
                .text(ERROR_BAR)
                .merge(stackTrace)
                .text(ERROR_BAR)
                .sendToConsole();
    }

    private MessageBuilder getErrorDescription(String message, Exception exception) {
        var stackTrace = new MessageBuilder();

        stackTrace.newLine()
                .color(ChatColor.DARK_RED).inBrackets("Critical Error").space()
                .color(ChatColor.RED).text(message).reset();
        if (exception == null) {
            return stackTrace;
        }
        var cause = exception.getCause() != null ? exception.getCause().getMessage() : exception.getMessage();
        stackTrace.color(ChatColor.DARK_RED).inBrackets("Reason").color(ChatColor.YELLOW).space().text(cause)
                .color(ChatColor.RESET).newLine()
                .color(ChatColor.DARK_RED).inBrackets("Exception type")
                .color(ChatColor.YELLOW).space().text(exception.getClass().getSimpleName())
                .color(ChatColor.RESET);
        return stackTrace;
    }

    private MessageBuilder getStackTrace(Exception exception) {
        var stackTrace = new MessageBuilder();

        if (exception == null) {
            return stackTrace;
        }

        var offset = 6;
        stackTrace.reset();
        for (var trace : exception.getStackTrace()) {
            offset = 6;
            offset = offset - (trace.getLineNumber() + "").length();
            stackTrace
                    .newLine()
                    .color(ChatColor.WHITE)
                    .text("at line", ChatColor.WHITE)
                    .space(2)
                    .text(trace.getLineNumber(), ChatColor.AQUA)
                    .space(offset)
                    .text("in", ChatColor.WHITE)
                    .space()
                    .text(trace.getClassName(), ChatColor.GRAY)
                    .text("." + trace.getMethodName() + "()", ChatColor.AQUA)
                    .space()
                    .reset();
        }
        return stackTrace;
    }
}
