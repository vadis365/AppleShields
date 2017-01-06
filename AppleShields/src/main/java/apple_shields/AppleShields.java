package apple_shields;

import apple_shields.confighandler.ConfigHandler;
import apple_shields.events.AppleShieldSoundEvent;
import apple_shields.events.EntityShieldDamageEvent;
import apple_shields.items.ItemAppleShield;
import apple_shields.items.ItemEnergyShield;
import apple_shields.items.ItemWhiteApple;
import apple_shields.packets.ShieldDestroyMessage;
import apple_shields.packets.ShieldDestroyPacketHandler;
import apple_shields.proxy.CommonProxy;
import apple_shields.shieldtypes.ShieldTypeBasic;
import apple_shields.shieldtypes.ShieldTypeEnergy;
import apple_shields.shieldtypes.ShieldTypeHealing;
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

@Mod(modid = "apple_shields", name = "apple_shields", version = "0.1.1", guiFactory = "apple_shields.confighandler.ConfigGuiFactory")
public class AppleShields {
    public static ItemAppleShield ITEM_SHIELD_RED_APPLE;
    public static ItemAppleShield ITEM_SHIELD_WHITE_APPLE;
    public static ItemAppleShield ITEM_SHIELD_GOLD_APPLE;
    public static ItemAppleShield ITEM_SHIELD_ENCHANTED_GOLD_APPLE;
    public static ItemEnergyShield ITEM_SHIELD_RF_WHITE_APPLE;
    public static ItemWhiteApple ITEM_WHITE_APPLE;
    public static SoundEvent SOUND_APPLE_CRUNCH;
    public static SoundEvent SOUND_APPLE_SPLAT;
    public static boolean IS_RF_PRESENT;
    public static SimpleNetworkWrapper NETWORK_WRAPPER;
    
    public static CreativeTabs creativeTab = new CreativeTabs("AppleShields") {
        @Override
        public Item getTabIconItem() {
            return AppleShields.ITEM_SHIELD_RED_APPLE;
        }
    };
    
    @SidedProxy(clientSide = "apple_shields.proxy.ClientProxy", serverSide = "apple_shields.proxy.CommonProxy")
    public static CommonProxy proxy;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        IS_RF_PRESENT = ModAPIManager.INSTANCE.hasAPI("CoFHAPI|energy");

        ConfigHandler.INSTANCE.loadConfig(event);

        ITEM_WHITE_APPLE = new ItemWhiteApple(4, 0.3F, false);
        ITEM_SHIELD_RED_APPLE = new ItemAppleShield(new ShieldTypeBasic(new ItemStack(Items.APPLE), new ItemStack(Items.APPLE), ConfigHandler.SHIELD_DURABILITY_RED_APPLE));
        ITEM_SHIELD_GOLD_APPLE = new ItemAppleShield(new ShieldTypeHealing(new ItemStack(Items.GOLDEN_APPLE), new ItemStack(Items.GOLD_INGOT), ConfigHandler.SHIELD_DURABILITY_GOLD_APPLE, ConfigHandler.SHIELD_HEAL_AMOUNT_GOLD_APPLE, ConfigHandler.SHIELD_HEAL_TIME_GOLD_APPLE));
        ITEM_SHIELD_ENCHANTED_GOLD_APPLE = new ItemAppleShield(new ShieldTypeHealing(new ItemStack(Items.GOLDEN_APPLE, 1, 1), new ItemStack(Blocks.GOLD_BLOCK), ConfigHandler.SHIELD_DURABILITY_RED_APPLE, ConfigHandler.SHIELD_HEAL_TIME_ENCHANTED_GOLD_APPLE, ConfigHandler.SHIELD_HEAL_TIME_ENCHANTED_GOLD_APPLE));
        ITEM_SHIELD_WHITE_APPLE = new ItemAppleShield(new ShieldTypeBasic(new ItemStack(ITEM_WHITE_APPLE), new ItemStack(Blocks.REDSTONE_BLOCK), ConfigHandler.SHIELD_DURABILITY_WHITE_APPLE));
        ITEM_SHIELD_RF_WHITE_APPLE = new ItemEnergyShield(new ShieldTypeEnergy(new ItemStack(ITEM_WHITE_APPLE), new ItemStack(Blocks.REDSTONE_BLOCK), ConfigHandler.SHIELD_DURABILITY_RF_WHITE_APPLE));

        ITEM_WHITE_APPLE.register("white_apple");
        ITEM_SHIELD_RED_APPLE.register("red_apple_shield");
        ITEM_SHIELD_GOLD_APPLE.register("golden_apple_shield");
        ITEM_SHIELD_ENCHANTED_GOLD_APPLE.register("enchanted_golden_apple_shield");
        ITEM_SHIELD_WHITE_APPLE.register("white_apple_shield");
        ITEM_SHIELD_RF_WHITE_APPLE.register("rf_apple_shield");

        addShapedRecipe(new ItemStack(ITEM_WHITE_APPLE, 1), "bbb", "bxb", "bbb", 'x', new ItemStack(Items.APPLE), 'b', "dyeWhite");
        addShapedRecipe(new ItemStack(ITEM_SHIELD_RED_APPLE, 1), "#i#", "###", " # ", '#', new ItemStack(Items.APPLE), 'i', "ingotIron");

        if (IS_RF_PRESENT)
            addShapedRecipe(new ItemStack(ITEM_WHITE_APPLE, 1, 1), "brb", "rxr", "brb", 'x', new ItemStack(Items.APPLE), 'b', "dyeWhite", 'r', "dustRedstone");

        NETWORK_WRAPPER = NetworkRegistry.INSTANCE.newSimpleChannel("apple_shields");
        NETWORK_WRAPPER.registerMessage(ShieldDestroyPacketHandler.class, ShieldDestroyMessage.class, 0, Side.CLIENT);
        
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        SOUND_APPLE_CRUNCH = new SoundEvent(new ResourceLocation("apple_shields", "apple_crunch")).setRegistryName("apple_shields", "apple_crunch");
        SOUND_APPLE_SPLAT = new SoundEvent(new ResourceLocation("apple_shields", "apple_splat")).setRegistryName("apple_shields", "apple_splat");
        GameRegistry.register(SOUND_APPLE_CRUNCH);
        GameRegistry.register(SOUND_APPLE_SPLAT);
        MinecraftForge.EVENT_BUS.register(new EntityShieldDamageEvent());
        MinecraftForge.EVENT_BUS.register(new AppleShieldSoundEvent());
        MinecraftForge.EVENT_BUS.register(ConfigHandler.INSTANCE);
    }

    private static void addShapedRecipe(ItemStack output, Object... objects) {
        GameRegistry.addRecipe(new ShapedOreRecipe(output, objects));
    }
}
