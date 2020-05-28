package ru.virtue.itemedit.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.Inventory;
import ru.virtue.itemedit.config.FunctionConfig;
import ru.virtue.itemedit.reformate.ReformateFacade;
import ru.virtue.itemedit.ItemLoreEdit;

public class InventoryEvent
implements Listener {

    private ReformateFacade reformateFacade;
    private ItemLoreEdit edit;

    public InventoryEvent(ItemLoreEdit itemEdit) {
        this.edit = itemEdit;
        reformateFacade = itemEdit.getFacade ();
    }

    @EventHandler
    public void openInventory(InventoryOpenEvent e) {
        if (FunctionConfig.INVENTORY_OPEN.isEnable ()) {
            reformateFacade.getInventoryItemStacks ().getItemsRefracting (e.getInventory ());
        }

    }

    @EventHandler
    public void closeInventory(InventoryCloseEvent e) {
        if (FunctionConfig.INVENTORY_CLOSE.isEnable ()) {
            Inventory inv = e.getInventory ();
            if(inv.getHolder ().equals (e.getPlayer ().getInventory ().getHolder ()))
            {
                inv = e.getPlayer ().getInventory ();
            }
            reformateFacade.getInventoryItemStacks ().getItemsRefracting (inv);
        }
    }

    @EventHandler
    public void spawnItem(ItemSpawnEvent e) {
        if (FunctionConfig.INVENTORY_DROP.isEnable ()) {
            reformateFacade.getItemStacksEdits ().refractingLore (e.getEntity ().getItemStack ());
        }
    }

    @EventHandler
    public void playerCommand(PlayerCommandPreprocessEvent e) {
        if (FunctionConfig.PLAYER_COMMAND.isEnable ()) {
            reformateFacade.getInventoryItemStacks ().getItemsRefracting (e.getPlayer ().getInventory ());
        }
    }
}
