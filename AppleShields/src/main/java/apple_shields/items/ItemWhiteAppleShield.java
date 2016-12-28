package apple_shields.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemWhiteAppleShield extends ItemAppleShield {

	public ItemWhiteAppleShield(int hitPoints) {
		super(hitPoints);
	}

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> list, boolean advanced) {
    	list.add("Base Durability: " + getMaxHitpoints());
    	list.add("Anvil Repair: Block of Restone");
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return repair.getItem() == Item.getItemFromBlock(Blocks.REDSTONE_BLOCK);
    }
}
