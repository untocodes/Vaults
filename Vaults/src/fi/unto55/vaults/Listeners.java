package fi.unto55.vaults;

import java.sql.SQLException;
import java.util.Map;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class Listeners implements Listener {

  //@SuppressWarnings("unused")
  @EventHandler
  public void inventoryClose(InventoryCloseEvent event) throws SQLException {
    Map < Inventory,
    Vault > l = Main.viewing;
    Inventory i = event.getInventory();
    Vault v = l.get(i);
    if (! (v == null)) {
      String base = Invtobase.toBase64(i);
      SQLs.updateString(Vault.id, Vault.owner, base);
      l.remove(i);
    }
  }

}
