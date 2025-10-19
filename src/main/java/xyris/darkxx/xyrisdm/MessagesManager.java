package xyris.darkxx.xyrisdm;

import java.io.File;
import java.io.IOException;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class MessagesManager {
  private final JavaPlugin plugin;
  
  private FileConfiguration messagesConfig;
  
  private final File messagesFile;
  
  public MessagesManager(JavaPlugin plugin) {
    this.plugin = plugin;
    this.messagesFile = new File(plugin.getDataFolder(), "messages.yml");
    reloadMessagesConfig();
  }
  
  public String getDeathMessagesEnabledMessage() {
    return ChatColor.translateAlternateColorCodes('&', this.messagesConfig.getString("deathMessagesEnabled"));
  }
  
  public String getDeathMessagesDisabledMessage() {
    return ChatColor.translateAlternateColorCodes('&', this.messagesConfig.getString("deathMessagesDisabled"));
  }
  
  public void setDeathMessagesEnabledMessage(String message) {
    this.messagesConfig.set("deathMessagesEnabled", message);
    saveMessagesConfig();
  }
  
  public void setDeathMessagesDisabledMessage(String message) {
    this.messagesConfig.set("deathMessagesDisabled", message);
    saveMessagesConfig();
  }
  
  public void reloadMessagesConfig() {
    if (!this.messagesFile.exists())
      this.plugin.saveResource("messages.yml", false); 
    this.messagesConfig = (FileConfiguration)YamlConfiguration.loadConfiguration(this.messagesFile);
  }
  
  public FileConfiguration getMessagesConfig() {
    return this.messagesConfig;
  }
  
  private void saveMessagesConfig() {
    try {
      this.messagesConfig.save(this.messagesFile);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
}
