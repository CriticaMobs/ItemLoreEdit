package ru.virtue.itemedit;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.virtue.itemedit.config.Config;
import ru.virtue.itemedit.config.Words;
import ru.virtue.itemedit.event.InventoryEvent;
import ru.virtue.itemedit.reformate.ReformateFacade;
import ru.virtue.itemedit.commands.ItemCommand;

public class ItemLoreEdit extends JavaPlugin {

    private Config config;
    private ReformateFacade facade;
    private Words words;
    private ItemCommand command;

    @Override
    public void onEnable() {
        config = new Config (this);
        facade = new ReformateFacade (this);
        words = new Words (this);
        getCommand ("itemedit").setExecutor (command = new ItemCommand (this));
        Bukkit.getPluginManager ().registerEvents (new InventoryEvent (this), this);
    }

    public Config getConfigFile()
    {
        return config;
    }


    public Words getWords() {
        return words;
    }

    public ReformateFacade getFacade() {
        return facade;
    }
}
