package xyris.darkxx.xyrisdm;

import org.bukkit.entity.Player;

public class TitleManager {
  private final Main plugin;
  
  public TitleManager(Main plugin) {
    this.plugin = plugin;
  }
  
  public void sendKillTitles(Player attacker, Player victim) {
    if (this.plugin.getConfig().getBoolean("kill-titles.enabled")) {
      String titleMessage = this.plugin.getConfig().getString("kill-titles.kill-title");
      String subtitleMessage = this.plugin.getConfig().getString("kill-titles.kill-subtitle");
      titleMessage = titleMessage.replace("%attacker%", (attacker != null) ? attacker.getName() : "?");
      titleMessage = titleMessage.replace("%victim%", (victim != null) ? victim.getName() : "?");
      subtitleMessage = subtitleMessage.replace("%attacker%", (attacker != null) ? attacker.getName() : "?");
      subtitleMessage = subtitleMessage.replace("%victim%", (victim != null) ? victim.getName() : "?");
      String formattedTitleMessage = Main.formatColors(titleMessage);
      String formattedSubtitleMessage = Main.formatColors(subtitleMessage);
      sendTitle(attacker, formattedTitleMessage, formattedSubtitleMessage);
    } 
  }
  
  public void sendDeathTitles(Player victim, Player attacker) {
    if (this.plugin.getConfig().getBoolean("death-titles.enabled")) {
      String titleMessage = this.plugin.getConfig().getString("death-titles.death-title");
      String subtitleMessage = this.plugin.getConfig().getString("death-titles.death-subtitle");
      titleMessage = titleMessage.replace("%attacker%", (attacker != null) ? attacker.getName() : "?");
      titleMessage = titleMessage.replace("%victim%", (victim != null) ? victim.getName() : "?");
      subtitleMessage = subtitleMessage.replace("%attacker%", (attacker != null) ? attacker.getName() : "?");
      subtitleMessage = subtitleMessage.replace("%victim%", (victim != null) ? victim.getName() : "?");
      String formattedTitleMessage = Main.formatColors(titleMessage);
      String formattedSubtitleMessage = Main.formatColors(subtitleMessage);
      sendTitle(victim, formattedTitleMessage, formattedSubtitleMessage);
    } 
  }
  
  private void sendTitle(Player player, String title, String subtitle) {
    player.sendTitle(title, subtitle, 10, 40, 10);
  }
}
