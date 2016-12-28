package apple_shields.items;

import java.util.List;

import apple_shields.confighandler.ConfigHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemGoldenAppleShield extends ItemAppleShield {

	public ItemGoldenAppleShield(int hitPoints) {
		super(hitPoints);
	}

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> list, boolean advanced) {
    	list.add("Base Durability: " + getMaxHitpoints());
    	list.add("Self repairs " + ConfigHandler.GOLDEN_APPLE_SHIELD_HEAL + " every " + ConfigHandler.GOLDEN_APPLE_SHIELD_HEAL_TIME + " seconds");
    	list.add("Anvil Repair: Gold Ingot");
    }

    @Override
	  public void onUpdate(ItemStack stack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
    	if(hasTag(stack) && stack.getTagCompound().hasKey("damage") && stack.getTagCompound().getInteger("damage") > 0) {
    		if(world.getWorldTime()%(20 * ConfigHandler.GOLDEN_APPLE_SHIELD_HEAL_TIME) == 0)
    			stack.getTagCompound().setInteger("damage", stack.getTagCompound().getInteger("damage") - ConfigHandler.GOLDEN_APPLE_SHIELD_HEAL < 0 ? 0 : stack.getTagCompound().getInteger("damage") - ConfigHandler.GOLDEN_APPLE_SHIELD_HEAL);
    	}
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return repair.getItem() == Items.GOLD_INGOT;
    }

    private static boolean hasTag(ItemStack stack) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
			return false;
		}
		return true;
    }
}
