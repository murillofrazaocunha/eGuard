package com.eomuhzin.guard.velocity;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

import java.io.IOException;

import static com.eomuhzin.guard.commons.Configuration.saveDefaultConfig;

@Plugin(id = "myfirstplugin", name = "My First Plugin", version = "0.1.0-SNAPSHOT",
        url = "https://example.org", description = "I did it!", authors = {"Me"})
public class Velocity {

    public final ProxyServer server;
    public final Logger logger;


    @Inject
    public Velocity(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;

        logger.info("Hello there! I made my first plugin with Velocity.");
    }


    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        try {
            saveDefaultConfig("Velocity");
            server.getEventManager().register(this, new Listener());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}