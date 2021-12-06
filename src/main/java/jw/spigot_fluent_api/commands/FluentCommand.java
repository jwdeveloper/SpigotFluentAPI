package jw.spigot_fluent_api.commands;


import jw.spigot_fluent_api.commands.events.FluentCommandEvent;
import jw.spigot_fluent_api.commands.events.FluentCommandPlayerEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
    private Consumer<CommandSender> onNoArguments;

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
    protected void onPlayerInvoke(Player playerSender, String[] args) {}
    protected void onConsoleInvoke(ConsoleCommandSender serverSender, String[] args) {}
    protected void onInvoke(CommandSender serverSender, String[] args) {}

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
        FluentCommand target = this;
        String[] arguments = args;
        if (args.length != 0) {
            FluentCommand child_invoked = target.isChildInvoked(args);
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
        return new ArrayList<>();
    }

    @Override
    public boolean execute(CommandSender commandSender, String commandLabel, String[] args) {
        FluentCommand target = this;
        String[] arguments = args;
        if (args.length != 0) {
            //null nie jest najlepszym rozwiazaniem, ale nie chce mi się robić opcjonala
            //magia połaczona z rekurencją
            FluentCommand child_invoked = target.isChildInvoked(args);
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
        if (target.onNoArguments != null && arguments.length == 0) {
            target.onNoArguments.accept(commandSender);
            return true;
        }
        if (commandSender instanceof Player) {

            if (!onPermissionCheck((Player) commandSender, target.permissions)) {
                return false;
            }
            target.onPlayerInvoke((Player) commandSender, arguments);
        } else
        {
            target.onConsoleInvoke((ConsoleCommandSender) commandSender, arguments);
        }
        target.onInvoke(commandSender, arguments);
        return true;
    }

    public List<String> getSubCommandsNames() {
        return subCommands.stream().map(c -> c.getName()).toList();
    }

    public boolean execute(String... args) {
        return execute(null, "", args);
    }

    public String getMessage(String[] args) {
        StringBuilder toReturn = new StringBuilder();
        for (String s : args) {
            toReturn.append(s);
            toReturn.append(' ');
        }

        return toReturn.toString();
    }

    //rekurencja bejbe
    public FluentCommand isChildInvoked(String[] args) {
        FluentCommand result = null;
        for (FluentCommand c : subCommands) {
            //szukanie komendy wsrod dzieci
            if (args.length > 1) {
                String[] part = Arrays.copyOfRange(args, 1, args.length);
                result = c.isChildInvoked(part);
                if (result != null) {
                    break;
                }
            }
            // jesli nie znaleziono udzieci to moze rodzic jest wlasicielm komendy
            if (c.getName().equalsIgnoreCase(args[0])) {
                result = c;
                break;
            }
        }
        return result;
    }

    public FluentCommand addPermission(String name) {
        if (permissions.contains(name))
            return this;
        this.permissions.add(name);
        return this;
    }

    public FluentCommand addPermission(String... name) {
        for (var permission : name) {
            if (permissions.contains(permission))
                return this;
            this.permissions.add(permission);
        }
        return this;
    }

    public FluentCommand addDefaultPermission() {
        //x.a.b.c
        String result = this.getName();
        FluentCommand parent = this.parent;
        while (parent != null) {
            result = parent.getName() + "." + result;
            parent = parent.parent;
        }
        return addPermission(result);
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

    public FluentCommand setParent(FluentCommand parent) {
        this.parent = parent;
        return this;
    }

    public void setNoArgsError(Consumer<CommandSender> action) {
        onNoArguments = action;
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

    public FluentCommand addSubCommand(String name, FluentCommandEvent commandEvent)
    {
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

    public void removeChild(FluentCommand child) {
        child.parent = null;
        subCommands.remove(child);
    }

    protected String connectArgs(String[] stringArray) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}