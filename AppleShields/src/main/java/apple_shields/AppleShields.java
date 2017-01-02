package apple_shields;

import apple_shields.confighandler.ConfigHandler;
import apple_shields.events.AppleShieldSoundEvent;
import apple_shields.events.EntityShieldDamageEvent;
import apple_shields.items.ItemAppleShield;
import apple_shields.items.ItemEnergyShield;
import apple_shields.items.ItemWhiteApple;
import apple_shields.packets.ShieldDestroyMessage;
import apple_shields.packets.ShieldDestroyPacketHandler;
import apple_shields.render.AppleShieldItemRenderer;
import apple_shields.shieldtypes.ShieldTypeBasic;
import apple_shields.shieldtypes.ShieldTypeEnergy;
import apple_shields.shieldtypes.ShieldTypeHealing;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ModAPIManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.ShapedOreRecipe;

@Mod(modid = "apple_shields", name = "apple_shields", version = "0.1.0", guiFactory = "apple_shields.confighandler.ConfigGuiFactory")
public class AppleShields
{
    public static ItemAppleShield itemShieldRedApple;
    public static ItemAppleShield itemShieldWhiteApple;
    public static ItemAppleShield itemShieldGoldApple;
    public static ItemAppleShield itemShieldEnchantedGoldApple;
    public static ItemEnergyShield itemShieldRFWhiteApple;
    
    public static ItemWhiteApple itemWhiteApple;
    
    public static SoundEvent soundAppleCrunch;
    public static SoundEvent soundAppleSplat;
    public static boolean rfPresent;
    public static SimpleNetworkWrapper networkWrapper;
    
    public static CreativeTabs creativeTab = new CreativeTabs("AppleShields")
    {
        
        @Override
        public Item getTabIconItem()
        {
            return AppleShields.itemShieldRedApple;
        }
    };
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        rfPresent = ModAPIManager.INSTANCE.hasAPI("CoFHAPI|energy");
        
        ConfigHandler.INSTANCE.loadConfig(event);

        itemWhiteApple = new ItemWhiteApple(4, 0.3F, false);
        itemShieldRedApple = new ItemAppleShield(new ShieldTypeBasic(new ItemStack(Items.APPLE), new ItemStack(Items.APPLE), ConfigHandler.shieldDurabilityRedApple));
        itemShieldGoldApple = new ItemAppleShield(new ShieldTypeHealing(new ItemStack(Items.GOLDEN_APPLE), new ItemStack(Items.GOLD_INGOT), ConfigHandler.shieldDurabilityGoldApple, ConfigHandler.shieldHealAmountGoldApple, ConfigHandler.shieldHealTimeGoldApple));
        itemShieldEnchantedGoldApple = new ItemAppleShield(new ShieldTypeHealing(new ItemStack(Items.GOLDEN_APPLE, 1, 1), new ItemStack(Blocks.GOLD_BLOCK), ConfigHandler.shieldDurabilityRedApple, ConfigHandler.shieldHealAmountEnchantedGoldApple, ConfigHandler.shieldHealTimeEnchanntedGoldApple));
        itemShieldWhiteApple = new ItemAppleShield(new ShieldTypeBasic(new ItemStack(itemWhiteApple), new ItemStack(Blocks.REDSTONE_BLOCK), ConfigHandler.shieldDurabilityWhiteApple));
        itemShieldRFWhiteApple = new ItemEnergyShield(new ShieldTypeEnergy(new ItemStack(itemWhiteApple), new ItemStack(Blocks.REDSTONE_BLOCK), ConfigHandler.shieldDurabilityRFWhiteApple));

        itemWhiteApple.register("white_apple");
        itemShieldRedApple.register("red_apple_shield");
        itemShieldGoldApple.register("golden_apple_shield");
        itemShieldEnchantedGoldApple.register("enchanted_golden_apple_shield");
        itemShieldWhiteApple.register("white_apple_shield");
        itemShieldRFWhiteApple.register("rf_apple_shield");
        
        addShapedRecipe(new ItemStack(itemWhiteApple, 1), "bbb", "bxb", "bbb", 'x', new ItemStack(Items.APPLE), 'b', "dyeWhite");
        addShapedRecipe(new ItemStack(itemShieldRedApple, 1), "#i#", "###", " # ", '#', new ItemStack(Items.APPLE), 'i', "ingotIron");
        
        if (rfPresent)
        {
            addShapedRecipe(new ItemStack(itemWhiteApple, 1, 1), "brb", "rxr", "brb", 'x', new ItemStack(Items.APPLE), 'b', "dyeWhite", 'r', "dustRedstone");
        }
        
        networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel("apple_shields");
        networkWrapper.registerMessage(ShieldDestroyPacketHandler.class, ShieldDestroyMessage.class, 0, Side.CLIENT);
        
        if (FMLCommonHandler.instance().getSide().isClient())
        {
            // Why do it the "normal" way, when you can do it the way that actually works? :D
            TileEntityItemStackRenderer.instance = new AppleShieldItemRenderer(TileEntityItemStackRenderer.instance);

            itemWhiteApple.registerModels();
            itemShieldRedApple.registerModels();
            itemShieldGoldApple.registerModels();
            itemShieldEnchantedGoldApple.registerModels();
            itemShieldWhiteApple.registerModels();
            itemShieldRFWhiteApple.registerModels();
        }
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        soundAppleCrunch = new SoundEvent(new ResourceLocation("apple_shields", "apple_crunch")).setRegistryName("apple_shields", "apple_crunch");
        soundAppleSplat = new SoundEvent(new ResourceLocation("apple_shields", "apple_splat")).setRegistryName("apple_shields", "apple_splat");
        
        GameRegistry.register(soundAppleCrunch);
        GameRegistry.register(soundAppleSplat);
        
        MinecraftForge.EVENT_BUS.register(new EntityShieldDamageEvent());
        MinecraftForge.EVENT_BUS.register(new AppleShieldSoundEvent());
        MinecraftForge.EVENT_BUS.register(ConfigHandler.INSTANCE);
    }
    
    private static void addShapedRecipe(ItemStack output, Object... objects)
    {
        GameRegistry.addRecipe(new ShapedOreRecipe(output, objects));
    }
}
