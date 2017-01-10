package apple_shields;

import apple_shields.confighandler.ConfigHandler;
import apple_shields.events.AppleShieldSoundEvent;
import apple_shields.events.EntityShieldDamageEvent;
import apple_shields.items.ItemApplePie;
import apple_shields.items.ItemAppleShield;
import apple_shields.items.ItemEnergyShield;
import apple_shields.items.ItemWhiteApple;
import apple_shields.packets.ShieldDestroyMessage;
import apple_shields.packets.ShieldDestroyPacketHandler;
import apple_shields.proxy.CommonProxy;
import apple_shields.shieldtypes.ShieldTypeBasic;
import apple_shields.shieldtypes.ShieldTypeEnergy;
import apple_shields.shieldtypes.ShieldTypeFeeding;
import apple_shields.shieldtypes.ShieldTypeRepairing;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ModAPIManager;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

@Mod(modid = "apple_shields", name = "Apple Shields", version = "__VERSION__", guiFactory = "apple_shields.confighandler.ConfigGuiFactory")
public class AppleShields
{
    public static ItemWhiteApple ITEM_FOOD_WHITE_APPLE;
    public static ItemApplePie ITEM_FOOD_APPLE_PIE;
    
    public static ItemAppleShield ITEM_SHIELD_RED_APPLE;
    public static ItemAppleShield ITEM_SHIELD_WHITE_APPLE;
    public static ItemAppleShield ITEM_SHIELD_APPLE_PIE;
    public static ItemAppleShield ITEM_SHIELD_GOLD_APPLE;
    public static ItemAppleShield ITEM_SHIELD_ENCHANTED_GOLD_APPLE;
    public static ItemEnergyShield ITEM_SHIELD_RF_WHITE_APPLE;
    
    public static SoundEvent SOUND_APPLE_CRUNCH;
    public static SoundEvent SOUND_APPLE_SPLAT;
    public static SoundEvent SOUND_PARTY;
    
    public static SimpleNetworkWrapper NETWORK_WRAPPER;
    
    public static CreativeTabs creativeTab = new CreativeTabs("AppleShields")
    {
        @Override
        public Item getTabIconItem()
        {
            return AppleShields.ITEM_SHIELD_RED_APPLE;
        }
    };
    
    @SidedProxy(clientSide = "apple_shields.proxy.ClientProxy", serverSide = "apple_shields.proxy.CommonProxy")
    public static CommonProxy PROXY;
    
    public static boolean IS_RF_PRESENT;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        IS_RF_PRESENT = ModAPIManager.INSTANCE.hasAPI("CoFHAPI|energy");
        
        ConfigHandler.INSTANCE.loadConfig(event);
        
        registerSounds();
        createItems();
        createShieldTypes();
        registerItems();
        registerRecipes();
        registerNetworking();
        
        PROXY.preInit();
    }
    
    private static SoundEvent createSound(String locationName)
    {
        ResourceLocation location = new ResourceLocation(locationName);
        
        return new SoundEvent(location).setRegistryName(location);
    }
    
    private static void registerSounds()
    {
        SOUND_APPLE_CRUNCH = createSound("apple_shields:apple_crunch");
        SOUND_APPLE_SPLAT = createSound("apple_shields:apple_splat");
        SOUND_PARTY = createSound("apple_shields:party");
        
        GameRegistry.register(SOUND_APPLE_CRUNCH);
        GameRegistry.register(SOUND_APPLE_SPLAT);
        GameRegistry.register(SOUND_PARTY);
    }
    
    private static void createItems()
    {
        ITEM_FOOD_WHITE_APPLE = new ItemWhiteApple(4, 0.3F, false);
        ITEM_FOOD_APPLE_PIE = new ItemApplePie(8, 0.6F, false);
        
        ITEM_SHIELD_RED_APPLE = new ItemAppleShield();
        ITEM_SHIELD_WHITE_APPLE = new ItemAppleShield();
        ITEM_SHIELD_APPLE_PIE = new ItemAppleShield();
        ITEM_SHIELD_GOLD_APPLE = new ItemAppleShield();
        ITEM_SHIELD_ENCHANTED_GOLD_APPLE = new ItemAppleShield();
        
        if (IS_RF_PRESENT)
        {
            ITEM_SHIELD_RF_WHITE_APPLE = new ItemEnergyShield();
        }
    }
    
    public static void createShieldTypes()
    {
        ITEM_SHIELD_RED_APPLE.setShieldType(new ShieldTypeBasic(new ItemStack(Items.APPLE), new ItemStack(Items.APPLE), SOUND_APPLE_CRUNCH, SOUND_APPLE_SPLAT, ConfigHandler.SHIELD_DURABILITY_RED_APPLE));
        ITEM_SHIELD_WHITE_APPLE.setShieldType(new ShieldTypeBasic(new ItemStack(ITEM_FOOD_WHITE_APPLE), new ItemStack(Items.REDSTONE), SOUND_APPLE_CRUNCH, SOUND_APPLE_SPLAT, ConfigHandler.SHIELD_DURABILITY_WHITE_APPLE));
        ITEM_SHIELD_APPLE_PIE.setShieldType(new ShieldTypeFeeding(new ItemStack(ITEM_FOOD_APPLE_PIE, 1, 1), new ItemStack(ITEM_FOOD_APPLE_PIE), SOUND_PARTY, SOUND_PARTY, ConfigHandler.SHIELD_DURABILITY_APPLE_PIE, ConfigHandler.SHIELD_HUNGER_APPLE_PIE, ConfigHandler.SHIELD_SATURATION_APPLE_PIE, 20 * ConfigHandler.SHIELD_FEED_TIME_APPLE_PIE, ConfigHandler.SHIELD_DURABILITY_PER_HUNGER));
        ITEM_SHIELD_GOLD_APPLE.setShieldType(new ShieldTypeRepairing(new ItemStack(Items.GOLDEN_APPLE), new ItemStack(Items.GOLD_INGOT), SOUND_APPLE_CRUNCH, SOUND_APPLE_SPLAT, ConfigHandler.SHIELD_DURABILITY_GOLD_APPLE, ConfigHandler.SHIELD_HEAL_AMOUNT_GOLD_APPLE, 20 * ConfigHandler.SHIELD_HEAL_TIME_GOLD_APPLE));
        ITEM_SHIELD_ENCHANTED_GOLD_APPLE.setShieldType(new ShieldTypeRepairing(new ItemStack(Items.GOLDEN_APPLE, 1, 1), new ItemStack(Blocks.GOLD_BLOCK), SOUND_APPLE_CRUNCH, SOUND_APPLE_SPLAT, ConfigHandler.SHIELD_DURABILITY_RED_APPLE, ConfigHandler.SHIELD_HEAL_TIME_ENCHANTED_GOLD_APPLE, 20 * ConfigHandler.SHIELD_HEAL_TIME_ENCHANTED_GOLD_APPLE));
        
        
        if (IS_RF_PRESENT)
        {
            ITEM_SHIELD_RF_WHITE_APPLE.setShieldType(new ShieldTypeEnergy(new ItemStack(ITEM_FOOD_WHITE_APPLE, 1, 1), new ItemStack(Blocks.REDSTONE_BLOCK), SOUND_APPLE_CRUNCH, SOUND_APPLE_SPLAT, ConfigHandler.SHIELD_DURABILITY_RF_WHITE_APPLE));
        }
    }
    
    private static void registerItems()
    {
        ITEM_FOOD_WHITE_APPLE.register("white_apple");
        ITEM_FOOD_APPLE_PIE.register("apple_pie");
        
        ITEM_SHIELD_RED_APPLE.register("red_apple_shield");
        ITEM_SHIELD_WHITE_APPLE.register("white_apple_shield");
        ITEM_SHIELD_GOLD_APPLE.register("golden_apple_shield");
        ITEM_SHIELD_APPLE_PIE.register("apple_pie_shield");
        ITEM_SHIELD_ENCHANTED_GOLD_APPLE.register("enchanted_golden_apple_shield");
        
        if (IS_RF_PRESENT)
        {
            ITEM_SHIELD_RF_WHITE_APPLE.register("rf_apple_shield");
        }
    }
    
    private static void registerRecipes()
    {
        // Little a is normal apple
        // Big A is special "apple"
        // Little i is the ingot
        
        // Little r is redstone
        // Little w is white dye
        
        ItemStack apple = new ItemStack(Items.APPLE);
        
        addShapedRecipe(new ItemStack(ITEM_FOOD_WHITE_APPLE, 1), "www", "waw", "www", 'a', apple, 'w', "dyeWhite");
        addShapelessRecipe(new ItemStack(ITEM_FOOD_APPLE_PIE, 1), new ItemStack(Items.EGG), new ItemStack(Items.SUGAR), apple, apple);
        
        addShapedRecipe(new ItemStack(ITEM_SHIELD_RED_APPLE), "aia", "aAa", " A ", 'a', apple, 'A', apple, 'i', "ingotIron");
        addShapedRecipe(new ItemStack(ITEM_SHIELD_WHITE_APPLE), "aia", "aAa", " A ", 'a', apple, 'A', new ItemStack(ITEM_FOOD_WHITE_APPLE), 'i', "ingotIron");
        addShapedRecipe(new ItemStack(ITEM_SHIELD_APPLE_PIE), "aia", "aAa", " A ", 'a', apple, 'A', new ItemStack(ITEM_FOOD_APPLE_PIE), 'i', "ingotIron");
        addShapedRecipe(new ItemStack(ITEM_SHIELD_GOLD_APPLE), "aia", "aAa", " A ", 'a', apple, 'A', new ItemStack(Items.GOLDEN_APPLE), 'i', "ingotGold");
        addShapedRecipe(new ItemStack(ITEM_SHIELD_ENCHANTED_GOLD_APPLE), "aia", "aAa", " A ", 'a', apple, 'A', new ItemStack(Items.GOLDEN_APPLE, 1, 1), 'i', "ingotGold");
        
        if (IS_RF_PRESENT)
        {
            addShapedRecipe(new ItemStack(ITEM_FOOD_WHITE_APPLE, 1, 1), "wrw", "rar", "wrw", 'a', apple, 'w', "dyeWhite", 'r', "dustRedstone");
            addShapedRecipe(new ItemStack(ITEM_SHIELD_RF_WHITE_APPLE, 1), "aia", "aAa", " A ", 'a', apple, 'A', new ItemStack(ITEM_FOOD_WHITE_APPLE, 1, 1), 'i', "ingotIron");
        }
    }
    
    private void registerNetworking()
    {
        NETWORK_WRAPPER = NetworkRegistry.INSTANCE.newSimpleChannel("apple_shields");
        NETWORK_WRAPPER.registerMessage(ShieldDestroyPacketHandler.class, ShieldDestroyMessage.class, 0, Side.CLIENT);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new EntityShieldDamageEvent());
        MinecraftForge.EVENT_BUS.register(new AppleShieldSoundEvent());
        MinecraftForge.EVENT_BUS.register(ConfigHandler.INSTANCE);
    }
    
    private static void addShapedRecipe(ItemStack output, Object... objects)
    {
        GameRegistry.addRecipe(new ShapedOreRecipe(output, objects));
    }
    
    private static void addShapelessRecipe(ItemStack output, Object... objects)
    {
        GameRegistry.addRecipe(new ShapelessOreRecipe(output, objects));
    }
}
