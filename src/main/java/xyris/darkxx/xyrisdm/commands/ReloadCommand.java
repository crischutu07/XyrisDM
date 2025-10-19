package xyris.darkxx.xyrisdm.commands;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import xyris.darkxx.xyrisdm.Main;
import xyris.darkxx.xyrisdm.MessagesManager;

public class ReloadCommand implements CommandExecutor, TabCompleter {
  private final Main mainInstance;
  
  private final MessagesManager messagesManager;
  
  public ReloadCommand(Main mainInstance, MessagesManager messagesManager) {
    this.mainInstance = mainInstance;
    this.messagesManager = new MessagesManager((JavaPlugin)mainInstance);
    mainInstance.getCommand("xyrisdm").setExecutor(this);
    mainInstance.getCommand("xyrisdm").setTabCompleter(this);
  }
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (label.equalsIgnoreCase("xyrisdm") && args.length == 1 && args[0].equalsIgnoreCase("reload")) {
      if (sender.hasPermission("xyrisdeathm.reload")) {
        reloadConfigs(sender);
        return true;
      } 
      sender.sendMessage("§cYou do not have access to this command.");
    } 
    return false;
  }
  
  private void reloadConfigs(CommandSender sender) {
    this.mainInstance.reloadPluginConfig();
    this.messagesManager.reloadMessagesConfig();
    sender.sendMessage("§aDone! Config reloaded, messages.yml will not be reloaded restart the server to apply the changes.");
  }
  
  public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
    List<String> completions = new ArrayList<>();
    if (args.length == 1 && "reload".startsWith(args[0].toLowerCase()))
      completions.add("reload"); 
    return completions;
  }
}
