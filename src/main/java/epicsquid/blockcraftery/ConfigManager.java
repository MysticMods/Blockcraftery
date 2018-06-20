package epicsquid.blockcraftery;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigManager {
  public static Configuration config;

  public static boolean freeDecoration, rightClickReplace;

  public static void init(File configFile) {
    if (config == null) {
      config = new Configuration(configFile);
      load();
    }
  }

  public static void load() {
    freeDecoration = config
        .getBoolean("freeDecoration", Configuration.CATEGORY_GENERAL, false, "Toggles whether giving framed blocks textures costs one block.");
    rightClickReplace = config.getBoolean("rightClickReplace", Configuration.CATEGORY_GENERAL, false,
        "Toggles whether or not framed block textures can be freely replaced by right-clicking with another block.");

    if (config.hasChanged()) {
      config.save();
    }
  }

  @SubscribeEvent
  public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
    if (event.getModID().equalsIgnoreCase(Blockcraftery.MODID)) {
      load();
    }
  }
}
