package jw.fluent.api.logger.implementation;

import jw.fluent.api.spigot.messages.message.MessageBuilder;
import jw.fluent.api.logger.api.SimpleLogger;
import jw.fluent.api.utilites.messages.LogUtility;
import jw.fluent.plugin.implementation.FluentApi;
import org.bukkit.ChatColor;

import java.util.ResourceBundle;
import java.util.logging.Logger;

public class SimpleLoggerImpl implements SimpleLogger {
    private static final String ERROR_BAR;

    static {
        ERROR_BAR = new MessageBuilder().newLine().color(ChatColor.BOLD).color(ChatColor.DARK_RED).bar("-", 100).newLine().build();
    }

    private Logger logger;

    public SimpleLoggerImpl(Logger logger)
    {
        this.logger = logger;
    }

    @Override
    public String getName() {
        return "SimpleLogger";
    }

    @Override
    public boolean isLoggable(Level level)
    {
        return true;
    }

    @Override
    public void log(Level level, ResourceBundle bundle, String msg, Throwable thrown) {

        switch (level)
        {
            case INFO -> info(msg);
            case WARNING -> warning(msg);
            case ERROR ->error(msg,thrown);
            case ALL ->success(msg);
        }
    }

    @Override
    public void log(Level level, ResourceBundle bundle, String format, Object... params) {
        switch (level)
        {
            case INFO -> info(format);
            case WARNING -> warning(format);
            case ALL ->success(format);
        }
    }

    public enum LogType
    {
        SUCCESS,INFO,WARNING
    }


    public void warning(String message, Object... params) {
        log(message, LogType.WARNING, params);
    }

    public void success(String message, Object... params) {
        log(message, LogType.SUCCESS, params);
    }

    public void info(String message, Object... params) {
        log(message, LogType.INFO, params);
    }

    public void log(String message, LogType logType, Object... params)
    {
        var msg = new MessageBuilder();

        if(FluentApi.plugin() != null)
        {
            msg.inBrackets(FluentApi.plugin().getName()).space();
        }
        else
        {
            msg.inBrackets("plugin").space();
        }

        var format =switch (logType)
        {
            case INFO -> LogUtility.info();
            case SUCCESS -> LogUtility.success();
            case WARNING -> LogUtility.warning();
        };
        msg.text(format);
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

    public void error(String message, Throwable exception) {



        var errorMessage = new MessageBuilder();
        if(exception == null)
        {
           errorMessage.error().text(message).sendToConsole();
           return;
        }
        var description = getErrorDescription(message, exception);
        var stackTrace = getStackTrace(exception);
        errorMessage
                .merge(description)
                .text(ERROR_BAR)
                .merge(stackTrace)
                .text(ERROR_BAR)
                .sendToConsole();
    }

    @Override
    public void error(String message) {
        error(message,null);
    }

    private MessageBuilder getErrorDescription(String message, Throwable exception) {
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

    private MessageBuilder getStackTrace(Throwable exception) {
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
