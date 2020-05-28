package ru.virtue.itemedit.reformate.inventory;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface InventoryItemStacks
{
    public Inventory getItemsRefracting(Inventory inventory);

    public boolean isItemLoreSame(ItemStack itemStack);

}
