package apple_shields;

import net.minecraft.creativetab.CreativeTabs;
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
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.ShapedOreRecipe;
import apple_shields.confighandler.ConfigHandler;
import apple_shields.events.AnvilEventAppleShield;
import apple_shields.events.AppleShieldSoundEvent;
import apple_shields.events.EntityShieldDamageEvent;
import apple_shields.items.ItemAppleShield;
import apple_shields.items.ItemEnchantedGoldenAppleShield;
import apple_shields.items.ItemGoldenAppleShield;
import apple_shields.items.ItemWhiteApple;
import apple_shields.items.ItemWhiteAppleShield;
import apple_shields.items.ItemWhiteAppleShieldRF;
import apple_shields.packets.ShieldDestroyMessage;
import apple_shields.packets.ShieldDestroyPacketHandler;
import apple_shields.proxy.CommonProxy;

@Mod(modid = "apple_shields", name = "apple_shields", version = "0.1.0", guiFactory = "apple_shields.confighandler.ConfigGuiFactory")

public class AppleShields {

	@SidedProxy(clientSide = "apple_shields.proxy.ClientProxy", serverSide = "apple_shields.proxy.CommonProxy")
	public static CommonProxy PROXY;
	public static Item RED_APPLE_SHIELD;
	public static Item GOLDEN_APPLE_SHIELD;
	public static Item ENCHANTED_GOLDEN_APPLE_SHIELD;
	public static Item RF_WHITE_APPLE_SHIELD;
	public static Item WHITE_APPLE_SHIELD;
	public static Item WHITE_APPLE;
	public static SoundEvent APPLE_CRUNCH;
    public static SoundEvent APPLE_SPLAT;
	public static boolean IS_RF_PRESENT;
	public static SimpleNetworkWrapper NETWORK_WRAPPER;
	public static CreativeTabs TAB = new CreativeTabs("AppleShields") {

		@Override
		public Item getTabIconItem() {
			return AppleShields.ENCHANTED_GOLDEN_APPLE_SHIELD;
		}
	};

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		IS_RF_PRESENT = ModAPIManager.INSTANCE.hasAPI("CoFHAPI|energy");
		ConfigHandler.INSTANCE.loadConfig(event);
		RED_APPLE_SHIELD = new ItemAppleShield(ConfigHandler.RED_APPLE_SHIELD_DURABILITY);
		GOLDEN_APPLE_SHIELD = new ItemGoldenAppleShield(ConfigHandler.GOLDEN_APPLE_SHIELD_DURABILITY);
		ENCHANTED_GOLDEN_APPLE_SHIELD = new ItemEnchantedGoldenAppleShield(ConfigHandler.ENCHANTED_GOLDEN_APPLE_SHIELD_DURABILITY);
		RF_WHITE_APPLE_SHIELD = new ItemWhiteAppleShieldRF(ConfigHandler.RF_WHITE_APPLE_SHIELD_DURABILITY);
		WHITE_APPLE_SHIELD = new ItemWhiteAppleShield(ConfigHandler.WHITE_APPLE_SHIELD_DURABILITY);
		WHITE_APPLE = new ItemWhiteApple(4, 0.3F, false);
		
		GameRegistry.register(RED_APPLE_SHIELD.setRegistryName("apple_shields", "red_apple_shield").setUnlocalizedName("apple_shields.red_apple_shield"));
		GameRegistry.register(GOLDEN_APPLE_SHIELD.setRegistryName("apple_shields", "golden_apple_shield").setUnlocalizedName("apple_shields.golden_apple_shield"));
		GameRegistry.register(ENCHANTED_GOLDEN_APPLE_SHIELD.setRegistryName("apple_shields", "enchanted_golden_apple_shield").setUnlocalizedName("apple_shields.enchanted_golden_apple_shield"));
		if(IS_RF_PRESENT)
			GameRegistry.register(RF_WHITE_APPLE_SHIELD.setRegistryName("apple_shields", "rf_apple_shield").setUnlocalizedName("apple_shields.rf_apple_shield"));

		GameRegistry.register(WHITE_APPLE_SHIELD.setRegistryName("apple_shields", "white_apple_shield").setUnlocalizedName("apple_shields.white_apple_shield"));
		GameRegistry.register(WHITE_APPLE.setRegistryName("apple_shields", "white_apple").setUnlocalizedName("apple_shields.white_apple"));
		
		PROXY.registerRenderers();
	// recipes
		addShapedRecipe(new ItemStack(WHITE_APPLE, 1), "bbb", "bxb", "bbb", 'x', new ItemStack(Items.APPLE), 'b', "dyeWhite");
		addShapedRecipe(new ItemStack(RED_APPLE_SHIELD, 1), "#i#", "###", " # ", '#', new ItemStack(Items.APPLE), 'i' ,"ingotIron");
		addShapedRecipe(new ItemStack(GOLDEN_APPLE_SHIELD, 1), "#i#", "#x#", " # ", '#', new ItemStack(Items.APPLE), 'x', new ItemStack(Items.GOLDEN_APPLE, 1, 0), 'i', "ingotIron");
		addShapedRecipe(new ItemStack(ENCHANTED_GOLDEN_APPLE_SHIELD, 1), "#i#", "#x#", " # ", '#', new ItemStack(Items.APPLE), 'x', new ItemStack(Items.GOLDEN_APPLE, 1, 1), 'i' ,"ingotIron");
		if(IS_RF_PRESENT) {
			addShapedRecipe(new ItemStack(RF_WHITE_APPLE_SHIELD, 1), "#i#", "###", " # ", '#', new ItemStack(WHITE_APPLE, 1, 1), 'i', "ingotIron");
			addShapedRecipe(new ItemStack(WHITE_APPLE, 1, 1), "brb", "rxr", "brb", 'x', new ItemStack(Items.APPLE), 'b', "dyeWhite", 'r', "dustRedstone");
		}
		addShapedRecipe(new ItemStack(WHITE_APPLE_SHIELD, 1), "#i#", "###", " # ", '#', new ItemStack(WHITE_APPLE), 'i', "ingotIron");
		
		NETWORK_WRAPPER = NetworkRegistry.INSTANCE.newSimpleChannel("apple_shields");
		NETWORK_WRAPPER.registerMessage(ShieldDestroyPacketHandler.class, ShieldDestroyMessage.class, 0, Side.CLIENT);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		APPLE_CRUNCH = new SoundEvent(new ResourceLocation("apple_shields", "apple_crunch")).setRegistryName("apple_shields", "apple_crunch");
		GameRegistry.register(APPLE_CRUNCH);
		APPLE_SPLAT = new SoundEvent(new ResourceLocation("apple_shields", "apple_splat")).setRegistryName("apple_shields", "apple_splat");
		GameRegistry.register(APPLE_SPLAT);
		MinecraftForge.EVENT_BUS.register(new EntityShieldDamageEvent());
		MinecraftForge.EVENT_BUS.register(new AnvilEventAppleShield());
		MinecraftForge.EVENT_BUS.register(new AppleShieldSoundEvent());
		MinecraftForge.EVENT_BUS.register(ConfigHandler.INSTANCE);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		PROXY.postInit();
	}

	private static void addShapedRecipe(ItemStack output, Object... objects) {
		GameRegistry.addRecipe(new ShapedOreRecipe(output, objects));
	}

}