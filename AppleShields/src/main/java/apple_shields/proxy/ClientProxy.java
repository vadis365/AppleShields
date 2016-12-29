package apple_shields.proxy;

import apple_shields.AppleShields;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

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
	}
}
