package apple_shields.confighandler;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigHandler {

	public static final ConfigHandler INSTANCE = new ConfigHandler();
	public Configuration CONFIG;
	public static int RED_APPLE_SHIELD_DURABILITY;
	public static int GOLDEN_APPLE_SHIELD_DURABILITY;
	public static int ENCHANTED_GOLDEN_APPLE_SHIELD_DURABILITY;
	public static int RF_WHITE_APPLE_SHIELD_DURABILITY;
	public static int RF_WHITE_APPLE_SHIELD_DAMAGE;
	public static int WHITE_APPLE_SHIELD_DURABILITY;
	public static int GOLDEN_APPLE_SHIELD_HEAL_TIME;
	public static int GOLDEN_APPLE_SHIELD_HEAL;
	public static int ENCHANTED_GOLDEN_APPLE_SHIELD_HEAL_TIME;
	public static int ENCHANTED_GOLDEN_APPLE_SHIELD_HEAL;

	public final String[] usedCategories = { "Apple Shield Settings" };

	public void loadConfig(FMLPreInitializationEvent event) {
		CONFIG = new Configuration(event.getSuggestedConfigurationFile());
		CONFIG.load();
		syncConfigs();
	}

	private void syncConfigs() {
		RED_APPLE_SHIELD_DURABILITY = CONFIG.get("Apple Shield Settings", "Red Apple Shield Durability", 336).getInt(1);
		GOLDEN_APPLE_SHIELD_DURABILITY = CONFIG.get("Apple Shield Settings", "Golden Apple Shield Durability", 504).getInt(504);
		WHITE_APPLE_SHIELD_DURABILITY = CONFIG.get("Apple Shield Settings", "Non-RF iShield Durability", 504).getInt(504);
		ENCHANTED_GOLDEN_APPLE_SHIELD_DURABILITY = CONFIG.get("Apple Shield Settings", "Enchanted Golden Apple Shield Durability", 672).getInt(672);
		RF_WHITE_APPLE_SHIELD_DURABILITY = CONFIG.get("Apple Shield Settings", "RF iShield Max RF Charge", 10000).getInt(10000);
		RF_WHITE_APPLE_SHIELD_DAMAGE = CONFIG.get("Apple Shield Settings", "RF iShield RF Consumed per Damage Point", 20).getInt(20);
		GOLDEN_APPLE_SHIELD_HEAL_TIME = CONFIG.get("Apple Shield Settings", "Golden Apple Auto Repair Cycle (Seconds)", 8).getInt(8);
		GOLDEN_APPLE_SHIELD_HEAL = CONFIG.get("Apple Shield Settings", "Golden Apple Durability Auto Repaired", 1).getInt(1);
		ENCHANTED_GOLDEN_APPLE_SHIELD_HEAL_TIME = CONFIG.get("Apple Shield Settings", "Enchanted Golden Apple Auto Repair Cycle (Seconds)", 4).getInt(4);
		ENCHANTED_GOLDEN_APPLE_SHIELD_HEAL = CONFIG.get("Apple Shield Settings", "Enchanted Golden Apple Durability Auto Repaired", 1).getInt(1);
		if (CONFIG.hasChanged())
			CONFIG.save();
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals("apple_shields"))
			syncConfigs();
	}
}