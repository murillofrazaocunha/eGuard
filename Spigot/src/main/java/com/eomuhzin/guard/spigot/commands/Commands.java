package com.eomuhzin.guard.spigot.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;


import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public abstract class Commands extends Command {

    public Commands(String name, String... aliases) {
        super(name);
        this.setAliases(Arrays.asList(aliases));

        try {
            SimpleCommandMap simpleCommandMap = (SimpleCommandMap) Bukkit.getServer()
                    .getClass()
                    .getDeclaredMethod("getCommandMap")
                    .invoke(Bukkit.getServer());
            simpleCommandMap.register(this.getName(), "eguard", this);
        } catch (ReflectiveOperationException ex) {
            ex.printStackTrace();
        }
    }

    public abstract void perform(CommandSender sender, String label, String[] args);

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        this.perform(sender, commandLabel, args);
        return true;
    }

    public static void setupCommands() {
        new Reload();
    }
}