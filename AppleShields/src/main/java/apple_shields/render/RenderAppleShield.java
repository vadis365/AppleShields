package apple_shields.render;

import apple_shields.AppleShields;
import apple_shields.models.ModelShieldBossAndHandle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderAppleShield extends TileEntitySpecialRenderer<TileEntity> {
	private ResourceLocation MODEL_TEXTURE = new ResourceLocation("apple_shields", "textures/models/shield_boss_and_handle.png");
    private ModelShieldBossAndHandle MODEL_SHIELD = new ModelShieldBossAndHandle();
    
    public Shieldtype TYPE;

    public RenderAppleShield(Shieldtype type) {
        this.TYPE = type;
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {
    	bindTexture(MODEL_TEXTURE);
        GlStateManager.pushMatrix();
        GlStateManager.scale(1.0F, -1.0F, -1.0F);
        this.MODEL_SHIELD.render();
        GlStateManager.popMatrix();
        renderItem();
    }
    
    private void renderItem() {
        RenderHelper.enableStandardItemLighting();
		GlStateManager.enableLighting();
		GlStateManager.pushMatrix();
		GlStateManager.translate(0.0D, 0.15D, 0.1D);
		GlStateManager.scale(1.25f, 1.25f, 1.25f);
		Minecraft.getMinecraft().getRenderItem().renderItem(TYPE.APPLE, ItemCameraTransforms.TransformType.NONE);
		GlStateManager.popMatrix();
    }

    public enum Shieldtype{
        RED_APPLE(new ItemStack(Items.APPLE)),
        GOLD_APPLE(new ItemStack(Items.GOLDEN_APPLE)),
        ENCHANTED_GOLD_APPLE(new ItemStack(Items.GOLDEN_APPLE, 1, 1)),
        WHITE_APPLE(new ItemStack(AppleShields.WHITE_APPLE)),
        RF_WHITE_APPLE(new ItemStack(AppleShields.WHITE_APPLE, 1, 1));

        public ItemStack APPLE;
        Shieldtype(ItemStack apple){
            this.APPLE = apple;
        }

    }
}
