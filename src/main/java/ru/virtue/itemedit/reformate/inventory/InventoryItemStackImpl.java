package ru.virtue.itemedit.reformate.inventory;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import ru.virtue.itemedit.reformate.ReformateFacade;
import ru.virtue.itemedit.reformate.item.ItemStacksEdits;

public class InventoryItemStackImpl
implements InventoryItemStacks
{
    private ReformateFacade facade;
    private ItemStacksEdits edits;

    public InventoryItemStackImpl(ReformateFacade facade)
    {
        this.facade = facade;
        this.edits = facade.getItemStacksEdits ();
    }

    @Override
    public Inventory getItemsRefracting(Inventory inv) {
        new BukkitRunnable () {
            @Override
            public void run() {
                for (int index = 0; index < inv.getSize (); index++)
                {
                    ItemStack item = inv.getItem (index);
                    if(item == null || item.getType () == Material.AIR)
                    {continue;}
                    if(isItemLoreSame (item)) continue;
                    inv.setItem (index, edits.refractingLore (item));
                }
            }
        }.runTaskLaterAsynchronously (facade.getEdit (), 2L);
        return inv;
    }

    @Override
    public boolean isItemLoreSame(ItemStack itemStack) {
        ItemStack itemEdit = edits.refractingLore (itemStack);
        ItemMeta meta = itemEdit.getItemMeta ();
        ItemMeta metaEdit = itemEdit.getItemMeta ();
        if(meta.hasLore () && metaEdit.hasLore ())
        {
            if(meta.getLore ().equals (metaEdit.getLore ()))
            {
                return true;
            }
        }
        return false;
    }
}
