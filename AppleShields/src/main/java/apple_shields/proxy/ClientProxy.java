package apple_shields.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import apple_shields.AppleShields;
import apple_shields.render.RenderAppleShield;
import apple_shields.tileentities.TileEntityEnchantedGoldenAppleShield;
import apple_shields.tileentities.TileEntityGoldenAppleShield;
import apple_shields.tileentities.TileEntityRedAppleShield;
import apple_shields.tileentities.TileEntityWhiteAppleShield;
import apple_shields.tileentities.TileEntityWhiteAppleShieldRF;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void registerRenderers() {
		ModelLoader.setCustomModelResourceLocation(AppleShields.RED_APPLE_SHIELD, 0, new ModelResourceLocation("apple_shields:red_apple_shield", "inventory"));
		ModelLoader.setCustomModelResourceLocation(AppleShields.GOLDEN_APPLE_SHIELD, 0, new ModelResourceLocation("apple_shields:golden_apple_shield", "inventory"));
		ModelLoader.setCustomModelResourceLocation(AppleShields.ENCHANTED_GOLDEN_APPLE_SHIELD, 0, new ModelResourceLocation("apple_shields:enchanted_golden_apple_shield", "inventory"));
		if(AppleShields.IS_RF_PRESENT)
			ModelLoader.setCustomModelResourceLocation(AppleShields.RF_WHITE_APPLE_SHIELD, 0, new ModelResourceLocation("apple_shields:rf_apple_shield", "inventory"));

		ModelLoader.setCustomModelResourceLocation(AppleShields.WHITE_APPLE_SHIELD, 0, new ModelResourceLocation("apple_shields:white_apple_shield", "inventory"));
		ModelLoader.setCustomModelResourceLocation(AppleShields.WHITE_APPLE, 0, new ModelResourceLocation("apple_shields:white_apple", "inventory"));
		ModelLoader.setCustomModelResourceLocation(AppleShields.WHITE_APPLE, 1, new ModelResourceLocation("apple_shields:white_apple", "inventory"));	
	}

	@Override
	public void postInit() {
		// shield rendering
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRedAppleShield.class, new RenderAppleShield(RenderAppleShield.Shieldtype.RED_APPLE));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGoldenAppleShield.class, new RenderAppleShield(RenderAppleShield.Shieldtype.GOLD_APPLE));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnchantedGoldenAppleShield.class, new RenderAppleShield(RenderAppleShield.Shieldtype.ENCHANTED_GOLD_APPLE));
		if(AppleShields.IS_RF_PRESENT)
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWhiteAppleShieldRF.class, new RenderAppleShield(RenderAppleShield.Shieldtype.RF_WHITE_APPLE));

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWhiteAppleShield.class, new RenderAppleShield(RenderAppleShield.Shieldtype.WHITE_APPLE));

		// item models
		ForgeHooksClient.registerTESRItemStack(AppleShields.RED_APPLE_SHIELD, 0, TileEntityRedAppleShield.class);
		ForgeHooksClient.registerTESRItemStack(AppleShields.GOLDEN_APPLE_SHIELD, 0, TileEntityGoldenAppleShield.class);
		ForgeHooksClient.registerTESRItemStack(AppleShields.ENCHANTED_GOLDEN_APPLE_SHIELD, 0, TileEntityEnchantedGoldenAppleShield.class);
		if(AppleShields.IS_RF_PRESENT)
			ForgeHooksClient.registerTESRItemStack(AppleShields.RF_WHITE_APPLE_SHIELD, 0, TileEntityWhiteAppleShieldRF.class);

		ForgeHooksClient.registerTESRItemStack(AppleShields.WHITE_APPLE_SHIELD, 0, TileEntityWhiteAppleShield.class);
	}
}
