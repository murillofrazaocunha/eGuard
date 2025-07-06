package com.eomuhzin.guard.spigot.commands;

import com.eomuhzin.guard.commons.Configuration;
import org.bukkit.command.CommandSender;

import java.io.IOException;

public class Reload extends Commands{
    public Reload() {
        super("eguardreload");
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        if(sender.hasPermission("guard.reload")) {
            try {
                Configuration.configuration.reloadConfig();
                sender.sendMessage("§aConfiguração recarregada com sucesso!");
            } catch (IOException e) {
                sender.sendMessage("§cUm erro ocorreu ao recarregar a configuração.");
                throw new RuntimeException(e);
            }
        } else {
            sender.sendMessage("§cVocê não tem permissão para executar este comando.");
        }
    }
}
