
package fi.unto55.vaults;

import java.io.IOException;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;




public class Main extends JavaPlugin {
	public  static Map<Inventory,Vault> viewing = new HashMap<Inventory,Vault>();	
    @Override
    public void onEnable(){
    	this.saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new Listeners(), this);
        SQLs.Connect();
    	
    }
	
    
    

   
   
    @Override
    public void onDisable() {
    	SQLs.Disconnect();

    }
   public void showHelppage(Player p) {
	   p.sendMessage(ChatColor.GOLD + "[Vaults]" + ChatColor.AQUA +" Help page");
	   p.sendMessage("/vault [Number] to open vault");
	   p.sendMessage("/adminvault [Number] [UserName] to open vault");
   }

   
    @Override
    public boolean onCommand(CommandSender sender,
            Command command,
            String label,
            String[] args) {
    		
        	if (command.getName().equalsIgnoreCase("vault")) {
            
            		Player p = (Player) sender;
            		String perm = "vaults.use.";
            		int lastperm = 0;
            		for(int i = 0; i < 501;i++) {
            			if(p.hasPermission(perm + Integer.toString(i))) {
            				lastperm = i;
            			}
            		}
            		if (args.length == 0 ||!StringUtils.isNumeric(args[0]) || Integer.parseInt(args[0]) > 500 ) {
            			showHelppage(p);
            		} else {
            			if(Integer.parseInt(args[0]) < lastperm) {
            				
            			
            			
            		 
            			Inventory vault;
            			  try {
  							
  							vault = Invtobase.fromBase64(SQLs.getString(Integer.parseInt(args[0]),p.getName().toLowerCase()));
  							Vault v = new Vault();
  							Vault.id = Integer.parseInt(args[0]);
  							Vault.owner = p.getName();
  							viewing.put(vault, v);
  						} catch (NumberFormatException e) {
  							vault = null;
  							System.out.println(e.getMessage() + " ERROR!!!!");
  						} catch (IOException e) {
  							vault = null;
  							System.out.println(e.getMessage() + " ERROR!!!!");							//e.printStackTrace();
  						} catch (SQLException e) {
  							vault = null;
  							System.out.println(e.getMessage() + " ERROR!!!!");							//e.printStackTrace();
  						}
              		  
              		  
  					
  					p.openInventory(vault);
            			}
            			else {
            				  p.sendMessage(ChatColor.GOLD + "[Vaults]" + ChatColor.AQUA +" Error");
            				  p.sendMessage(ChatColor.RED + "You do not have a permission to use that vault.");            			}
                			}
            		}
        	if (command.getName().equalsIgnoreCase("adminvault")) {
                
        		Player p = (Player) sender;
        		String perm = "vaults.admin";
        		if(!p.hasPermission(perm)) {
  				  p.sendMessage(ChatColor.GOLD + "[Vaults]" + ChatColor.AQUA +" Error");
  				  p.sendMessage(ChatColor.RED + "You do not have a permission to use that vault.");
  				  return true;
        		}
        		if (!(args.length == 2) ||!StringUtils.isNumeric(args[0])) {
        			showHelppage(p);
        			return true;
        		}

        		
        				
        			
        			
        		 
        			Inventory vault;
        			  try {
							
							vault = Invtobase.fromBase64(SQLs.getString(Integer.parseInt(args[0]),args[1].toLowerCase()));
							Vault v = new Vault();
							Vault.id = Integer.parseInt(args[0]);
							Vault.owner = p.getName();
							viewing.put(vault, v);
						} catch (NumberFormatException e) {
							vault = null;
							System.out.println(e.getMessage() + " ERROR!!!!");
						} catch (IOException e) {
							vault = null;
							System.out.println(e.getMessage() + " ERROR!!!!");							//e.printStackTrace();
						} catch (SQLException e) {
							vault = null;
							System.out.println(e.getMessage() + " ERROR!!!!");							//e.printStackTrace();
						}
          		  
          		  
					
					p.openInventory(vault);
        			}

            			
            return true;
 
    }

   
}