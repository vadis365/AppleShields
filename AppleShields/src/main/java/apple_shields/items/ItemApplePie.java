package apple_shields.items;

import apple_shields.AppleShields;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemApplePie extends ItemFood
{
    public ItemApplePie(int amount, float saturation, boolean isWolfFood)
    {
        super(amount, saturation, isWolfFood);
    }
    
    public void register(String unlocalizedName)
    {
        setUnlocalizedName("apple_shields." + unlocalizedName);
        GameRegistry.register(this, new ResourceLocation("apple_shields", unlocalizedName));
    }
    
    @SideOnly(Side.CLIENT)
    public void registerModels()
    {
        ModelLoader.setCustomMeshDefinition(this, (stack) -> new ModelResourceLocation("apple_shields:apple_pie", "inventory"));
    }
    
    @Override
    public CreativeTabs getCreativeTab()
    {
        return AppleShields.creativeTab;
    }
    
}
