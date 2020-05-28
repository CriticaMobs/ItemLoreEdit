package ru.virtue.itemedit.reformate.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.virtue.itemedit.config.Config;
import ru.virtue.itemedit.reformate.Reformate;
import ru.virtue.itemedit.reformate.ReformateFacade;

import java.util.List;

public class ItemStacksEditsImpl
implements ItemStacksEdits
{
    private Config config;

    public ItemStacksEditsImpl(ReformateFacade facade)
    {
        this.config = facade.getEdit ().getConfigFile ();
    }

    @Override
    public ItemStack refractingLore(ItemStack item) {
        if(item == null || item.getType () == Material.AIR)
        {
            return item;
        }
        if(!Config.isLore ()) return item;
        if(!item.hasItemMeta ()){ return item;}
        ItemMeta meta = item.getItemMeta ();
        if(!item.getItemMeta ().hasLore ())
        {
            return item;
        }
        List<String> lore = new Reformate (meta.getLore ())
                .setRestriction (config.getLengthLore ())
                .setStringNewLine (config.getStringNewLine ())
                .setSymbolsNoLength (config.getCharNoLength ()).build ();
        meta.setLore (lore);
        item.setItemMeta (meta);
        return item;
    }
}
