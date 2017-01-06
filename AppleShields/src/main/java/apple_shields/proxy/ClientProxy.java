package apple_shields.proxy;

import apple_shields.AppleShields;
import apple_shields.render.AppleShieldItemRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenderers() {
		TileEntityItemStackRenderer.instance = new AppleShieldItemRenderer(TileEntityItemStackRenderer.instance);
		AppleShields.ITEM_WHITE_APPLE.registerModels();
		AppleShields.ITEM_SHIELD_RED_APPLE.registerModels();
		AppleShields.ITEM_SHIELD_GOLD_APPLE.registerModels();
		AppleShields.ITEM_SHIELD_ENCHANTED_GOLD_APPLE.registerModels();
		AppleShields.ITEM_SHIELD_WHITE_APPLE.registerModels();
		if (AppleShields.IS_RF_PRESENT)
			AppleShields.ITEM_SHIELD_RF_WHITE_APPLE.registerModels();
	}
}
