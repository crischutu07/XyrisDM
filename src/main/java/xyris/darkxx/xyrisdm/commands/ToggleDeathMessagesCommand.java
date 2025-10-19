package xyris.darkxx.xyrisdm.commands;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xyris.darkxx.xyrisdm.Main;
import xyris.darkxx.xyrisdm.MessagesManager;

public class ToggleDeathMessagesCommand implements CommandExecutor {
  private final Main plugin;
  
  private final MessagesManager messagesManager;
  
  private final File deathMessagesFile;
  
  private FileConfiguration deathMessagesConfig;
  
  public ToggleDeathMessagesCommand(Main plugin) {
    this.plugin = plugin;
    this.messagesManager = new MessagesManager((JavaPlugin)plugin);
    this.deathMessagesFile = new File(plugin.getDataFolder(), "death-messages-data.yml");
    this.deathMessagesConfig = (FileConfiguration)YamlConfiguration.loadConfiguration(this.deathMessagesFile);
    plugin.getCommand("toggledeathmessages").setExecutor(this);
  }
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (command.getName().equalsIgnoreCase("toggledeathmessages")) {
      if (sender instanceof Player) {
        String message;
        Player player = (Player)sender;
        UUID playerId = player.getUniqueId();
        boolean currentStatus = isDeathMessagesEnabled(playerId);
        this.deathMessagesConfig.set(playerId.toString(), Boolean.valueOf(!currentStatus));
        try {
          this.deathMessagesConfig.save(this.deathMessagesFile);
        } catch (IOException e) {
          e.printStackTrace();
        } 
        if (!currentStatus) {
          message = this.messagesManager.getDeathMessagesEnabledMessage();
        } else {
          message = this.messagesManager.getDeathMessagesDisabledMessage();
        } 
        player.sendMessage(message);
      } else {
        sender.sendMessage(formatColors("&cYou're not allowed to use this command."));
      } 
      return true;
    } 
    return false;
  }
  
  public static String formatColors(String message) {
    message = ChatColor.translateAlternateColorCodes('&', message);
    Pattern pattern = Pattern.compile("&#([A-Fa-f0-9]{6})");
    Matcher matcher = pattern.matcher(message);
    while (matcher.find()) {
      String colorCode = matcher.group();
      ChatColor color = ChatColor.of(colorCode.substring(1));
      message = message.replace(colorCode, color.toString());
    } 
    return message;
  }
  
  public boolean isDeathMessagesEnabled(UUID playerId) {
    return this.deathMessagesConfig.getBoolean(playerId.toString(), true);
  }
}
