package ru.virtue.itemedit.config;

import com.google.common.io.Files;
import org.bukkit.configuration.file.FileConfiguration;
import ru.virtue.itemedit.ItemLoreEdit;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Config {
    private static FileConfiguration configuration;
    private File file;
    private ItemLoreEdit edit;
    private static Map<String, Boolean> stringBooleanMap;
    public String stringNewLine;
    public String charNoLength;
    private static boolean lore;
    private int lengthLore;
    private boolean words;
    public static final double VERSION_CONFIG = 1.0;

    public Config(ItemLoreEdit edit) {
        this.edit = edit;
        stringBooleanMap = new HashMap<> ();
        file = new File (edit.getDataFolder (), "config.yml");
        configuration = edit.getConfig ();
        loadConfig ();
    }

    public void loadConfig() {
        exist ();
        try {
            double version = configuration.getDouble ("version-config");
            if(version != VERSION_CONFIG) oldConfig ();
            lore = configuration.getBoolean ("lore.enabled", true);
            if (!lore) {
                return;
            }
            lengthLore = configuration.getInt ("lore.characters-per-line", 30);
            words = configuration.getBoolean ("words", false);
            stringNewLine = configuration.getString ("newline-char");
            charNoLength = configuration.getString ("char-no-length");
            loadMap ();
        } catch (Exception ex) {
            ex.printStackTrace ();
        }

    }

    public void oldConfig()
    {
        File newFile = new File (edit.getDataFolder (), "oldConfig.yml");
        try {
            Files.move (file, newFile);
        } catch (IOException e) {
            e.printStackTrace ();
        }
        exist ();
    }

    private void loadMap() {
        stringBooleanMap.put ("inventory-open", getBool ("inventory-open"));
        stringBooleanMap.put ("inventory-close", getBool ("inventory-close"));
        stringBooleanMap.put ("inventory-drop", getBool ("inventory-drop"));
        stringBooleanMap.put ("player-command", getBool ("player-command"));
    }

    public static Map<String, Boolean> getStringBooleanMap() {
        return stringBooleanMap;
    }

    public static boolean getBool(String name) {
        return configuration.getBoolean (name, true) && lore;
    }

    public void exist() {
        if (!file.exists ()) {
            setFile (false);
        }else{
            try {
                configuration.load (file);
            }catch (Exception ex)
            {
                ex.printStackTrace ();
            }
        }
    }

    public String getStringNewLine() {
        return stringNewLine;
    }

    public String getCharNoLength() {
        return charNoLength;
    }

    public boolean isWords() {
        return words;
    }

    public void setFile(boolean replace) {
        edit.saveResource ("config.yml", replace);
    }

    public static boolean isLore() {
        return lore;
    }

    public int getLengthLore() {
        return lengthLore;
    }

}