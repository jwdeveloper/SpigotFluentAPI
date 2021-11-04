package jw.spigot_fluent_api.commands;


import jw.spigot_fluent_api.commands.events.FluentCommandEvent;
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
    private final ArrayList<FluentCommand> children = new ArrayList<>();
    private final HashMap<Integer, Supplier<ArrayList<String>>> tabCompletes = new HashMap<>();
    private final ArrayList<String> emptyTabCompletes = new ArrayList<>();
    private Consumer<CommandSender> OnNoArguments;
    protected boolean commandResult = true;
    
    public abstract void onInvoke(Player playerSender, String[] args);

    public abstract void onInvoke(ConsoleCommandSender serverSender, String[] args);

    public abstract void onInitialize();

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
        return target.getTabCompleter(arguments.length);
    }

    public void setCommandResult(boolean result) {
        this.commandResult = result;
    }


    public  boolean execute(String... args)
    {
        return execute(null,"",args);
    }

    @Override
    public boolean execute(CommandSender commandSender, String commandLabel, String[] args) {
        FluentCommand target = this;
        String[] arguments = args;
        commandResult = true;
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
        if (target.OnNoArguments != null && arguments.length == 0) {
            target.OnNoArguments.accept(commandSender);
            return commandResult;
        }
        if (commandSender instanceof Player) {
            //sprawdzanie permisji
            if (target.permissions.size() != 0 )
            {
                for (var permission:permissions)
                {
                    if(!(commandSender).hasPermission(permission))
                    {
                        commandSender.sendMessage(ChatColor.RED+"You need permission: "+permission);
                        return commandResult;
                    }
                }
            }
            target.onInvoke((Player) commandSender, arguments);
        } else {
            target.onInvoke((ConsoleCommandSender) commandSender, arguments);
        }
        return commandResult;
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
        for (FluentCommand c : children) {
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
    public FluentCommand addPermission(String name)
    {
        if(permissions.contains(name))
            return this;
        this.permissions.add(name);
        return  this;
    }
    public FluentCommand addPermission(String... name)
    {
        for(var permission: name)
        {
            if(permissions.contains(permission))
                return this;
            this.permissions.add(permission);
        }
        return  this;
    }

    public FluentCommand addDefaultPermission()
    {
        //x.a.b.c
        String result = this.getName();
        FluentCommand parent = this.parent;
        while(parent != null)
        {
            result = parent.getName()+"."+result;
            parent = parent.parent;
        }
        return  addPermission(result);
    }

    public ArrayList<String> getTabCompleter(int argument) {
        return tabCompletes.getOrDefault(argument, () -> {
            return emptyTabCompletes;
        }).get();
    }

    public void setTabCompleter(int argument, String... acction) {
        ArrayList<String> complieters = new ArrayList<>();

        for (int i = 0; i < acction.length; i++) {
            complieters.add(acction[i]);
        }

        tabCompletes.putIfAbsent(argument, () -> {
            return complieters;
        });
    }


    public void addTabComplets(String ... complets)
    {
            tabCompletes.putIfAbsent(0, () ->
            {
               return new ArrayList<String>(Arrays.asList(complets));
            });
    }

    public void displaySubCommandsNames() {
        tabCompletes.putIfAbsent(1, () ->
        {
            ArrayList<String> names = new ArrayList<>();

            this.children.forEach(c -> {
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

    public void setNoArgsError(Consumer<CommandSender> acction) {
        OnNoArguments = acction;
    }

    public void setTabCompleter(int argument, Supplier<ArrayList<String>> acction) {
        tabCompletes.putIfAbsent(argument, acction);
    }

    public FluentCommand addSubCommand(FluentCommand child) {
        child.setParent(this);
        children.add(child);
        return this;
    }
    
    public FluentCommand addSubCommand(String name, FluentCommandEvent commandEvent)
    {
        this.addSubCommand(new FluentSubCommand(name,commandEvent));
        return this;
    }
    
    public void removeChild(FluentCommand child) {
        child.parent = null;
        children.remove(child);
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