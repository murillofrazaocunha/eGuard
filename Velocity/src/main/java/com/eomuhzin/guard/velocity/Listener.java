package com.eomuhzin.guard.velocity;

import com.eomuhzin.guard.commons.Configuration;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerPreConnectEvent;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.util.GameProfile;

import java.util.List;

public class Listener {


    @Subscribe
    public void onServerPreConnect(ServerPreConnectEvent event) {
        Player player = event.getPlayer();
        GameProfile profile =  player.getGameProfile();
        var tokens = Configuration.configuration.getListString("tokens-do-proxy");
        List<GameProfile.Property> properties = new java.util.ArrayList<>(profile.getProperties());
        for (int i = 0; i < tokens.size(); i++) {
            String list = tokens.get(i);
            properties.add(new GameProfile.Property("eguard-" + i, String.valueOf(i), list));
        }
        player.setGameProfileProperties(properties);
    }

}
