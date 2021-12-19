package jw.spigot_fluent_api.commands;


import jw.spigot_fluent_api.commands.events.FluentCommandEvent;
import jw.spigot_fluent_api.commands.events.FluentCommandPlayerEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class FluentCommand extends BukkitCommand {

    private FluentCommand parent;
    private final ArrayList<String> permissions = new ArrayList<>();
    private final ArrayList<FluentCommand> subCommands = new ArrayList<>();
    private final HashMap<Integer, Supplier<ArrayList<String>>> tabCompletes = new HashMap<>();

    public FluentCommand(String name, boolean registerCommand) {
        super(name);
        if (registerCommand)
            registerCommands();
        onInitialize();
    }

    public FluentCommand(String name) {
        super(name);
        registerCommands();
        onInitialize();
        this.displaySubCommandsNames();
    }

    protected abstract void onInitialize();

    protected void onPlayerInvoke(Player playerSender, String[] args) {
    }

    protected void onConsoleInvoke(ConsoleCommandSender serverSender, String[] args) {
    }

    protected void onInvoke(CommandSender serverSender, String[] args) {
    }

    protected boolean onPermissionCheck(Player player, ArrayList<String> permissions) {
        if (permissions.size() == 0) {
            return true;
        }
        for (var permission : permissions) {
            if (!player.hasPermission(permission)) {
                player.sendMessage(ChatColor.RED + "You need permission: " + permission);
                return false;
            }
        }
        return true;
    }


    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        if (args.length == 0)
            return new ArrayList<>();

        var target = this;
        var arguments = args;
        var child = target.isSubCommandInvoked(arguments);
        if (child == null)
            return tabCompletes.get(1).get();

        target = child;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equalsIgnoreCase(target.getName())) {
                arguments = Arrays.copyOfRange(args, i + 1, args.length);
                return target.tabComplete(sender,alias,arguments);
            }
        }
        return new ArrayList<>();
    }

    @Override
    public boolean execute(CommandSender commandSender, String commandLabel, String[] args) {
        FluentCommand target = this;
        String[] arguments = args;
        if (args.length != 0) {
            //null nie jest najlepszym rozwiazaniem, ale nie chce mi się robić opcjonala
            //magia połaczona z rekurencją
            FluentCommand child_invoked = target.isSubCommandInvoked(args);
            if (child_invoked != null) {
                target = child_invoked;
                for (int j = 0; j < args.length; j++) {
                    if (args[j].equalsIgnoreCase(target.getName())) {
                        arguments = Arrays.copyOfRange(args, j + 1, args.length);
                        break;
                    }
                }
            }
        }
        if (commandSender instanceof Player playerSender) {

            if (!onPermissionCheck(playerSender, target.permissions)) {
                return false;
            }
            target.onPlayerInvoke(playerSender, arguments);
        } else {
            target.onConsoleInvoke((ConsoleCommandSender) commandSender, arguments);
        }
        target.onInvoke(commandSender, arguments);
        return true;
    }

    public List<String> getSubCommandsNames() {
        return subCommands.stream().map(Command::getName).toList();
    }

    //rekurencja bejbe
    public FluentCommand isSubCommandInvoked(String[] args) {
        FluentCommand result = null;
        String[] part = null;
        for (FluentCommand c : subCommands) {
            //szukanie komendy wsrod dzieci
            if (args.length > 1) {
                part = Arrays.copyOfRange(args, 1, args.length);
                result = c.isSubCommandInvoked(part);
                if (result != null) {
                    break;
                }
            }
            // jesli nie znaleziono dziecka, to rodzic jest wywolywany
            if (c.getName().equalsIgnoreCase(args[0])) {
                result = c;
                break;
            }
        }
        return result;
    }

    public FluentCommand addPermission(String permission) {
        if (!permissions.contains(permission))
            this.permissions.add(permission);
        return this;
    }

    public FluentCommand addPermission(String... permissions) {
        for (var permission : permissions) {
            addPermission(permission);
        }
        return this;
    }

    public FluentCommand addDefaultPermission() {
        //x.a.b.c
        StringBuilder result = new StringBuilder(this.getName());
        FluentCommand parent = this.parent;
        while (parent != null) {
            result.insert(0, parent.getName() + ".");
            parent = parent.parent;
        }
        return addPermission(result.toString());
    }

    public void addTabCompletes(int argument, String... actions) {
        tabCompletes.putIfAbsent(argument, () -> {
            return new ArrayList(List.of(actions));
        });
    }

    public void addTabCompletes(int argument, Supplier<ArrayList<String>> actions) {
        tabCompletes.putIfAbsent(argument, actions);
    }

    public void addTabCompletes(int argument, ArrayList<String> actions) {
        tabCompletes.putIfAbsent(argument, () -> {
            return actions;
        });
    }

    public void addTabCompletes(String... complets) {
        tabCompletes.putIfAbsent(0, () ->
        {
            return new ArrayList<String>(Arrays.asList(complets));
        });
    }

    public void displaySubCommandsNames() {
        tabCompletes.putIfAbsent(1, () ->
        {
            var names = new ArrayList<String>();
            this.subCommands.forEach(c -> {
                names.add(c.getName());
            });
            return names;
        });
    }

    public FluentCommand setParent(FluentCommand parent)
    {
        this.parent = parent;
        return this;
    }
    public void setTabCompleter(int argument, Supplier<ArrayList<String>> acction) {
        tabCompletes.putIfAbsent(argument, acction);
    }

    public FluentCommand addSubCommand(FluentCommand child) {
        child.setParent(this);
        subCommands.add(child);
        return this;
    }

    public FluentCommand addSubCommand(String name, FluentCommandPlayerEvent commandEvent) {
        this.addSubCommand(new FluentSubCommand(name, commandEvent));
        return this;
    }

    public FluentCommand addSubCommand(String name, FluentCommandEvent commandEvent) {
        this.addSubCommand(new FluentCommand(name, false) {

            @Override
            public void onInvoke(CommandSender serverSender, String[] args) {
                commandEvent.execute(serverSender, args);
            }

            @Override
            public void onInitialize() {

            }
        });
        return this;
    }

    public void removeSubCommand(FluentCommand subCommand)
    {
        if(!subCommands.contains(subCommand))
            return;
        subCommand.parent = null;
        subCommands.remove(subCommand);
    }

    protected String connectArgs(String[] stringArray)
    {
        StringJoiner joiner = new StringJoiner("");
        for (int i = 0; i < stringArray.length; i++) {
            if (i < stringArray.length - 1)
                joiner.add(stringArray[i] + " ");
            else
                joiner.add(stringArray[i]);
        }
        return joiner.toString();
    }

    private void registerCommands() {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            commandMap.register(this.getName(), this);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}