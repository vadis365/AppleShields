package apple_shields.confighandler;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigHandler {
	public static final ConfigHandler INSTANCE = new ConfigHandler();
	public Configuration config;

	public static int SHIELD_DURABILITY_RED_APPLE;
	public static int SHIELD_DURABILITY_GOLD_APPLE;
	public static int SHIELD_DURABILITY_ENCHANTED_GOLD_APPLE;
	public static int SHIELD_DURABILITY_RF_WHITE_APPLE;
	public static int SHIELD_DURABILITY_WHITE_APPLE;

	public static int SHIELD_ENERGY_RF_PER_DAMAGE;

	public static int SHIELD_HEAL_TIME_GOLD_APPLE;
	public static int SHIELD_HEAL_TIME_ENCHANTED_GOLD_APPLE;
	public static int SHIELD_HEAL_AMOUNT_GOLD_APPLE;
	public static int SHIELD_HEAL_AMOUNT_ENCHANTED_GOLD_APPLE;

	public final String[] usedCategories = { "Apple Shield Settings" };

	public void loadConfig(FMLPreInitializationEvent event) {
		config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		syncConfigs();
	}

	private void syncConfigs() {
		SHIELD_DURABILITY_RED_APPLE = config.get("Apple Shield Settings", "Red Apple Shield Durability", 336).getInt(1);
		SHIELD_DURABILITY_GOLD_APPLE = config.get("Apple Shield Settings", "Golden Apple Shield Durability", 504).getInt(504);
		SHIELD_DURABILITY_ENCHANTED_GOLD_APPLE = config.get("Apple Shield Settings", "Enchanted Golden Apple Shield Durability", 672).getInt(672);
		SHIELD_DURABILITY_RF_WHITE_APPLE = config.get("Apple Shield Settings", "RF iShield Max RF Charge", 10000).getInt(10000);
		SHIELD_DURABILITY_WHITE_APPLE = config.get("Apple Shield Settings", "Non-RF iShield Durability", 504).getInt(504);

		SHIELD_ENERGY_RF_PER_DAMAGE = config.get("Apple Shield Settings", "RF iShield RF Consumed per Damage Point", 20).getInt(20);

		SHIELD_HEAL_TIME_GOLD_APPLE = config.get("Apple Shield Settings", "Golden Apple Auto Repair Cycle (Seconds)", 8).getInt(8);
		SHIELD_HEAL_TIME_ENCHANTED_GOLD_APPLE = config.get("Apple Shield Settings", "Enchanted Golden Apple Auto Repair Cycle (Seconds)", 4).getInt(4);
		SHIELD_HEAL_AMOUNT_GOLD_APPLE = config.get("Apple Shield Settings", "Golden Apple Durability Auto Repaired", 1).getInt(1);
		SHIELD_HEAL_AMOUNT_ENCHANTED_GOLD_APPLE = config.get("Apple Shield Settings", "Enchanted Golden Apple Durability Auto Repaired", 1).getInt(1);

		if (config.hasChanged()) {
			config.save();
		}
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals("apple_shields")) {
			syncConfigs();
		}
	}
}
