package com.eomuhzin.guard.spigot;

import com.destroystokyo.paper.profile.ProfileProperty;
import com.eomuhzin.guard.commons.Configuration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Listeners implements Listener {

    @EventHandler
    public void onPrePlayerJoin(AsyncPlayerPreLoginEvent event) {
        List<String> tokens = Configuration.configuration.getListString("tokens");

        // Filtra propriedades válidas: começam com "eguard-" e o valor não é igual à parte após o "-"
        List<ProfileProperty> filteredProperties = event.getPlayerProfile().getProperties().stream()
                .filter(prop -> {
                    String[] parts = prop.getName().split("-", 2);
                    return parts[0].equals("eguard");
                })
                .collect(Collectors.toList());

        if (Configuration.configuration.isDebug()) {
            System.out.println("[eGuard][DEBUG] Player: " + event.getName() + " (UUID: " + event.getUniqueId() + ")");
        }

        boolean allTokensPresent = tokens.stream().allMatch(token -> {
            int i = 0;
            boolean found = filteredProperties.stream()
                    .anyMatch(prop -> token.equals(prop.getSignature()));

            if (Configuration.configuration.isDebug()) {
                System.out.println("[eGuard][DEBUG] Token '" + i + "' presente? " + found);
            }
            i++;
            return found;
        });

        if (!allTokensPresent) {
            System.out.println("[eGuard][DEBUG] Nem todos os tokens foram encontrados. Kickando jogador.");
            // mensagem com replace § por &
            String message = String.join("\n", Configuration.configuration.getListString("alertas.mensagens-desconexao-token-invalido"))
                    .replace("§", "&")
                    .replace("{player}", event.getName())
                    .replace("{uuid}", event.getUniqueId().toString());
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER,
                    message);
        } else {
            if(Configuration.isDebug()) {
                System.out.println("[eGuard][DEBUG] Todos os tokens necessários foram encontrados. Permissão concedida.");
            }
        }
    }


}
