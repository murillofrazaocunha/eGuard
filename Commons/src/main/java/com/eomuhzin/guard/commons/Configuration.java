package com.eomuhzin.guard.commons;

import org.yaml.snakeyaml.Yaml;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.*;

public class Configuration {
    private final String path;
    private Map<Object, Object> map;

    public static Configuration configuration;
    public final String from;

    public List<String> getProxyTokens() {
        return getListString("tokens-do-proxy");
    }
    public List<String> getSpigotTokens() {
        return getListString("tokens");
    }

    public static Boolean isDebug() {
        return configuration.getBoolean("debug");
    }



    public static void saveDefaultConfig(String from) throws IOException {
        String config = "config";
        if(Objects.equals(from, "Velocity")) {
            config = "tokens";
        }
        File path = new File("plugins/eGuard/");
        if (!path.exists()) {
            path.mkdirs();
        }

        File file = new File("plugins/eGuard/" + config + ".yml");
        if (!file.exists()) {
            try (InputStream inputStream = Configuration.class.getClassLoader().getResourceAsStream( config + ".yml")) {
                if (inputStream != null) {
                    Files.copy(inputStream, file.toPath());
                } else {
                    throw new IOException("Resource " + config + ".yml not found");
                }
            }
        }
        configuration = new Configuration("plugins/eGuard/" + config +".yml", from);
        if(from == "Velocity") {
            configuration.generateToken();
        }
    }


    public void save() {
        try (FileWriter writer = new FileWriter(this.path)) {
            Yaml yaml = new Yaml();
            yaml.dump(this.map, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void set(String path, Object value) {
        String[] split = path.split("\\.");
        Map<Object, Object> currentMap = this.map;

        for (int i = 0; i < split.length - 1; i++) {
            if (!currentMap.containsKey(split[i]) || !(currentMap.get(split[i]) instanceof Map)) {
                currentMap.put(split[i], new HashMap<>());
            }
            currentMap = (Map<Object, Object>) currentMap.get(split[i]);
        }

        currentMap.put(split[split.length - 1], value);
    }
    public void setToken() {
        List<String> tokens = new ArrayList<>();
        tokens.add(java.util.UUID.randomUUID().toString());
        tokens.add(java.util.UUID.randomUUID().toString());
        set("tokens-do-proxy", tokens);
        save();
        return;
    }
    public void generateToken() {
        List<String> tokenConfig = getListString("tokens-do-proxy");
        if( tokenConfig.isEmpty() || tokenConfig.size() < 2) {
            setToken();
            return;
        }
        for (String token : tokenConfig) {
            try {
                UUID.fromString(token);
            } catch (Exception e) {
                setToken();
                return;
            }
        }
    }


    public Configuration(String path, String from) throws IOException {
        this.path = path;
        this.from = from;
        try (InputStream inputStream = Files.newInputStream(new File(path).toPath())) {
            Yaml yaml = new Yaml();
            this.map = (Map<Object, Object>) yaml.load(inputStream);
        }
    }

    public void reloadConfig() throws IOException {
        try (InputStream inputStream = Files.newInputStream(new File(this.path).toPath())) {
            Yaml yaml = new Yaml();
            this.map = (Map<Object, Object>) yaml.load(inputStream);
        }
    }


    public List<String> getK(String section) {
        List<String> list = new ArrayList<>();
        Map<?, ?> c;
        try {
            c = (Map<?, ?>) get(section);
        } catch (ClassCastException e) {
            return null;
        }
        if (c != null) {
            for (Object a : c.keySet()) {
                list.add(a.toString());
            }
        }
        return list;
    }

    public String getString(String p) {
        Object value = get(p);
        return value != null ? value.toString() : null;
    }

    public boolean getBoolean(String p) {
        return Boolean.parseBoolean(getString(p));
    }

    public int getInt(String p) {
        return Integer.parseInt(getString(p));
    }

    @SuppressWarnings("unchecked")
    public List<String> getListString(String p) {
        try {
            return (List<String>) get(p);
        } catch (ClassCastException e) {
            return List.of("null");
        }
    }

    public long getLong(String p) {
        return Long.parseLong(getString(p));
    }

    public Object get(String p) {
        Object hashMap = null;
        String[] split = p.split("\\.");
        
        if (split.length == 0) {
            return null;
        }
        
        hashMap = map.get(split[0]);
        
        for (int i = 1; i < split.length; i++) {
            if (hashMap instanceof Map) {
                hashMap = ((Map<?, ?>) hashMap).get(split[i]);
            } else {
                return null;
            }
        }
        
        return hashMap;
    }
}