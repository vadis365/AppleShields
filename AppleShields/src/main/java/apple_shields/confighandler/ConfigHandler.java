package apple_shields.confighandler;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigHandler
{
    public static final ConfigHandler INSTANCE = new ConfigHandler();
    public Configuration config;
    
    public static int shieldDurabilityRedApple;
    public static int shieldDurabilityGoldApple;
    public static int shieldDurabilityEnchantedGoldApple;
    public static int shieldDurabilityRFWhiteApple;
    public static int shieldDurabilityWhiteApple;
    
    public static int shieldEnergyRFPerDamage;
    
    public static int shieldHealTimeGoldApple;
    public static int shieldHealTimeEnchanntedGoldApple;
    public static int shieldHealAmountGoldApple;
    public static int shieldHealAmountEnchantedGoldApple;
    
    public final String[] usedCategories = { "Apple Shield Settings" };
    
    public void loadConfig(FMLPreInitializationEvent event)
    {
        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        syncConfigs();
    }
    
    private void syncConfigs()
    {
        shieldDurabilityRedApple = config.get("Apple Shield Settings", "Red Apple Shield Durability", 336).getInt(1);
        shieldDurabilityGoldApple = config.get("Apple Shield Settings", "Golden Apple Shield Durability", 504).getInt(504);
        shieldDurabilityEnchantedGoldApple = config.get("Apple Shield Settings", "Enchanted Golden Apple Shield Durability", 672).getInt(672);
        shieldDurabilityRFWhiteApple = config.get("Apple Shield Settings", "RF iShield Max RF Charge", 10000).getInt(10000);
        shieldDurabilityWhiteApple = config.get("Apple Shield Settings", "Non-RF iShield Durability", 504).getInt(504);
        
        shieldEnergyRFPerDamage = config.get("Apple Shield Settings", "RF iShield RF Consumed per Damage Point", 20).getInt(20);
        
        shieldHealTimeGoldApple = config.get("Apple Shield Settings", "Golden Apple Auto Repair Cycle (Seconds)", 8).getInt(8);
        shieldHealTimeEnchanntedGoldApple = config.get("Apple Shield Settings", "Enchanted Golden Apple Auto Repair Cycle (Seconds)", 4).getInt(4);
        shieldHealAmountGoldApple = config.get("Apple Shield Settings", "Golden Apple Durability Auto Repaired", 1).getInt(1);
        shieldHealAmountEnchantedGoldApple = config.get("Apple Shield Settings", "Enchanted Golden Apple Durability Auto Repaired", 1).getInt(1);
        
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
        }
    }
}
