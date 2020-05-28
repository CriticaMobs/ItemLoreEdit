package ru.virtue.itemedit.commands;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import ru.virtue.itemedit.ItemLoreEdit;
import ru.virtue.itemedit.config.Words;
import ru.virtue.itemedit.reformate.Reformate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class ItemCommand implements CommandExecutor, TabCompleter {

    private ItemLoreEdit edit;
    private Random random;
    private Words words;

    public ItemCommand(ItemLoreEdit edit) {
        this.edit = edit;
        this.words = edit.getWords ();
        this.random = new Random ();
    }

    public String randomWord()
    {
        String result;
        List<String> words = edit.getWords ().getStrings ();
        if(words.size () == 0) return "";
        try {
            result = words.get (random.nextInt ((words.size ())));
            return result;
        }catch (Exception ex)
        {return randomWord (); }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player))
        {
            Bukkit.getLogger ().severe ("command only player");
            return false;
        }
        Player p = (Player) sender;
        if(!p.hasPermission ("itemedit.admin")) return false;
        if(args.length == 0)
        {
            List<String> messages = Arrays.asList (
                    "&b|", "&b| &7/itemedit &a{количество строк в lore}",
                    "&b| &7/itemedit &areload", "&b|");
            messages.stream ().map (message -> ChatColor.translateAlternateColorCodes ('&', message))
                    .forEach (p::sendMessage);
        }else if(args.length == 1)
        {
            if(NumberUtils.isDigits (args[0]))
            {
                if(!edit.getConfigFile ().isWords ())
                {
                    p.sendMessage (ChatColor.RED + "Включите words для выполнения этой команды");
                    return false;
                }
                p.sendMessage (ChatColor.GOLD + "Создаём предмет....");
                ItemStack item = new ItemStack (Material.STICK);
                Bukkit.getScheduler ().runTaskLaterAsynchronously (edit, () ->
                {
                    int i;
                    i = Integer.parseInt (args[0]);
                    if(i > 50) i = 50;
                    List<String> list = new ArrayList<> ();
                    ChatColor[] colors = ChatColor.values ();
                    for(int length = 0; length < i; length++)
                    {
                        ChatColor color = colors[random.nextInt (colors.length)];
                        String result = color + randomWord ();
                        list.add (result);
                    }
                    ItemMeta meta = item.getItemMeta ();
                    meta.setLore (list);
                    item.setItemMeta (meta);
                    p.sendMessage (ChatColor.GOLD + "Предмет успешно создан, выдаём...");
                    p.getInventory ().addItem (item);
                }, 0L);
            }else{
                switch (args[0])
                {
                    case ("reload"):
                        edit.getConfigFile ().loadConfig ();
                        if(edit.getConfigFile ().isWords ()) words.loadFile ();
                        p.sendMessage (ChatColor.GREEN + "Конфиг перезагружен");
                        break;
                    default:
                        p.sendMessage (ChatColor.RED + "Пожалуйста используйте цифры");
                        break;
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> strings = new ArrayList<> ();
        if(args.length == 1)
        {
            strings = Arrays.asList ("reload");
        }
        return strings;
    }
}
