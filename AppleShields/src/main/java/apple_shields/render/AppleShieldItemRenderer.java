package apple_shields.render;

import apple_shields.items.ItemAppleShield;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelShield;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class AppleShieldItemRenderer extends TileEntityItemStackRenderer
{
    public final TileEntityItemStackRenderer parent;
    
    private final ModelShield modelShield = new ModelAppleShield();
    private final ResourceLocation modelTexture = new ResourceLocation("apple_shields:textures/models/shield_boss_and_handle.png");
    
    public AppleShieldItemRenderer(TileEntityItemStackRenderer previous)
    {
        parent = previous;
    }
    
    @Override
    public void renderByItem(ItemStack stack)
    {
        if ((stack != null) && (stack.getItem() instanceof ItemAppleShield))
        {
            ItemAppleShield item = (ItemAppleShield) stack.getItem();
            
            Minecraft.getMinecraft().getTextureManager().bindTexture(modelTexture);
            
            GlStateManager.pushMatrix();
            GlStateManager.scale(1.0, -1.0, -1.0);

            new ModelAppleShield().render();
            
            Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

            GlStateManager.scale(1.25, -1.25, -1.25);
            GlStateManager.translate(0, 0.125, 0.08125);
            renderItem(item.getShieldType().getShieldItem());
            
            GlStateManager.popMatrix();
        }
        else
        {
            parent.renderByItem(stack);
        }
    }
    
    private void renderItem(ItemStack stack)
    {
        Minecraft.getMinecraft().getRenderItem().renderItem(stack, Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack, (World) null, (EntityLivingBase) null));
    }
}
