package apple_shields.confighandler;

import apple_shields.AppleShields;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigHandler
{
    public static final ConfigHandler INSTANCE = new ConfigHandler();
    public Configuration config;
    
    public static int SHIELD_DURABILITY_RED_APPLE;
    public static int SHIELD_DURABILITY_APPLE_PIE;
    public static int SHIELD_DURABILITY_GOLD_APPLE;
    public static int SHIELD_DURABILITY_ENCHANTED_GOLD_APPLE;
    public static int SHIELD_DURABILITY_RF_WHITE_APPLE;
    public static int SHIELD_DURABILITY_WHITE_APPLE;
    
    public static int SHIELD_HUNGER_APPLE_PIE;
    public static float SHIELD_SATURATION_APPLE_PIE;
    public static int SHIELD_FEED_TIME_APPLE_PIE;
    
    public static int SHIELD_DURABILITY_PER_HUNGER;
    public static int SHIELD_ENERGY_RF_PER_DAMAGE;
    
    public static int SHIELD_HEAL_TIME_GOLD_APPLE;
    public static int SHIELD_HEAL_TIME_ENCHANTED_GOLD_APPLE;
    public static int SHIELD_HEAL_AMOUNT_GOLD_APPLE;
    public static int SHIELD_HEAL_AMOUNT_ENCHANTED_GOLD_APPLE;
    
    public final String[] usedCategories = { "Apple Shield Settings" };
    
    public void loadConfig(FMLPreInitializationEvent event)
    {
        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        syncConfigs();
    }
    
    private void syncConfigs()
    {
        String category = usedCategories[0];
        int maxInt = Integer.MAX_VALUE;
        float maxFloat = Float.MAX_VALUE;
        
        SHIELD_DURABILITY_RED_APPLE = config.getInt("Red Apple Shield Durability", category, 336, 1, maxInt, "");
        SHIELD_DURABILITY_APPLE_PIE = config.getInt("Apple Pie Shield Durability", category, 504, 1, maxInt, "");
        SHIELD_DURABILITY_GOLD_APPLE = config.getInt("Golden Apple Shield Durability", category, 504, 1, maxInt, "");
        SHIELD_DURABILITY_ENCHANTED_GOLD_APPLE = config.getInt("Enchanted Golden Apple Shield Durability", category, 672, 1, maxInt, "");
        SHIELD_DURABILITY_RF_WHITE_APPLE = config.getInt("RF iShield Max RF Charge", category, 10000, 1, maxInt, "");
        SHIELD_DURABILITY_WHITE_APPLE = config.getInt("Non-RF iShield Durability", category, 504, 1, maxInt, "");
        
        SHIELD_HUNGER_APPLE_PIE = config.getInt("Apple Pie Shield Hunger Restored", category, 1, 0, maxInt, "");
        SHIELD_SATURATION_APPLE_PIE = config.getFloat("Apple Pie Shield Saturation", category, 0.6F, 0, maxFloat, "");
        SHIELD_FEED_TIME_APPLE_PIE = config.getInt("Apple Pie Shield Feeding Cycle (Seconds)", category, 10, 1, maxInt, "");
        
        SHIELD_DURABILITY_PER_HUNGER = config.getInt("Apple Pie Shield Damage Per Hunger Restored", category, 4, 0, maxInt, "");
        SHIELD_ENERGY_RF_PER_DAMAGE = config.getInt("RF iShield RF Consumed per Damage Point", category, 20, 0, maxInt, "");
        
        SHIELD_HEAL_TIME_GOLD_APPLE = config.getInt("Golden Apple Auto Repair Cycle (Seconds)", category, 60, 1, maxInt, "");
        SHIELD_HEAL_TIME_ENCHANTED_GOLD_APPLE = config.getInt("Enchanted Golden Apple Auto Repair Cycle (Seconds)", category, 30, 1, maxInt, "");
        SHIELD_HEAL_AMOUNT_GOLD_APPLE = config.getInt("Golden Apple Durability Auto Repaired", category, 1, 0, maxInt, "");
        SHIELD_HEAL_AMOUNT_ENCHANTED_GOLD_APPLE = config.getInt("Enchanted Golden Apple Durability Auto Repaired", category, 1, 0, maxInt, "");
        
        if (config.hasChanged())
        {
            config.save();
        }
    }
    
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equals("apple_shields"))
        {
            syncConfigs();
            
            AppleShields.createShieldTypes();
        }
        
        AppleShields.createShieldTypes();
    }
}
