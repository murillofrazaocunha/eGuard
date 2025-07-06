package com.eomuhzin.guard.spigot;

import com.eomuhzin.guard.commons.Configuration;
import com.eomuhzin.guard.spigot.commands.Commands;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class Spigot extends JavaPlugin {
    @Override
    public void onEnable() {
        try {
            Configuration.saveDefaultConfig("Spigot");
            getServer().getPluginManager().registerEvents(new Listeners(), this);
            Commands.setupCommands();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
