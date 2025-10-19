package xyris.darkxx.xyrisdm.customactions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomActionsManager {
  private final JavaPlugin plugin;
  
  private final List<CustomAction> actions;
  
  public CustomActionsManager(JavaPlugin plugin) {
    this.plugin = plugin;
    this.actions = new ArrayList<>();
    loadActions();
  }
  
  private void loadActions() {
    File actionsFile = new File(this.plugin.getDataFolder(), "actions.xk");
    if (!actionsFile.exists())
      this.plugin.saveResource("actions.xk", false); 
    try {
      BufferedReader reader = new BufferedReader(new FileReader(actionsFile));
      try {
        String line;
        while ((line = reader.readLine()) != null) {
          line = line.trim();
          if (line.isEmpty() || line.startsWith("#") || line.startsWith("on-death"))
            continue; 
          processActionLine(line);
        } 
        reader.close();
      } catch (Throwable throwable) {
        try {
          reader.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        } 
        throw throwable;
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void reloadActions() {
    loadActions();
  }
  
  private void processActionLine(String line) {
    ActionType actionType;
    String[] parts = line.split("\\s+", 2);
    if (parts.length != 2)
      return; 
    String actionTypeStr = parts[0].toUpperCase();
    String parameter = parts[1];
    try {
      actionType = ActionType.valueOf(actionTypeStr);
    } catch (IllegalArgumentException e) {
      System.err.println("Invalid action type " + actionTypeStr);
      return;
    } 
    this.actions.add(new CustomAction(actionType, parameter));
  }
  
  public void executeActions(Player attacker, Player victim) {
    for (CustomAction action : this.actions)
      action.execute(attacker, victim); 
  }
}
