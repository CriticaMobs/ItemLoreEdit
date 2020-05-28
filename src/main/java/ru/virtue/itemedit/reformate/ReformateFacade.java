package ru.virtue.itemedit.reformate;

import ru.virtue.itemedit.reformate.inventory.InventoryItemStackImpl;
import ru.virtue.itemedit.reformate.inventory.InventoryItemStacks;
import ru.virtue.itemedit.reformate.item.ItemStacksEdits;
import ru.virtue.itemedit.reformate.item.ItemStacksEditsImpl;
import ru.virtue.itemedit.ItemLoreEdit;

public class ReformateFacade
{
    private ItemStacksEdits itemStacksEdits;

    private InventoryItemStacks inventoryItemStacks;


    private ItemLoreEdit edit;

    public ReformateFacade(ItemLoreEdit edit)
    {
        this.edit = edit;
        loadItemStacks ();
    }

    public void loadItemStacks() {
        itemStacksEdits = new ItemStacksEditsImpl (this);
        inventoryItemStacks = new InventoryItemStackImpl (this);
    }

    public ItemStacksEdits getItemStacksEdits() {
        return itemStacksEdits;
    }

    public ItemLoreEdit getEdit() {
        return edit;
    }

    public InventoryItemStacks getInventoryItemStacks() {
        return inventoryItemStacks;
    }
}
