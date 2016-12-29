package apple_shields.items;

import java.util.List;

import apple_shields.AppleShields;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemWhiteApple extends ItemFood {
    public ItemWhiteApple(int amount, float saturation, boolean isWolfFood) {
        super(amount, saturation, isWolfFood);
        this.setHasSubtypes(true);
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return AppleShields.TAB;
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return stack.getMetadata() > 0;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> list, boolean advanced) {
    	if(stack.getMetadata() > 0)
    		list.add("RF Version");
    	else
    		list.add("Non RF Version");
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        subItems.add(new ItemStack(itemIn));
        if(AppleShields.IS_RF_PRESENT)
        	subItems.add(new ItemStack(itemIn, 1, 1));
    }
}