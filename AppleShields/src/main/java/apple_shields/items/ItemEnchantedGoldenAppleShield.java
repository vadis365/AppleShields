package apple_shields.items;

import java.util.List;

import apple_shields.confighandler.ConfigHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemEnchantedGoldenAppleShield extends ItemAppleShield {

	public ItemEnchantedGoldenAppleShield(int hitPoints) {
		super(hitPoints);
	}

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> list, boolean advanced) {
    	list.add("Base Durability: " + getMaxHitpoints());
    	list.add("Self repairs " + ConfigHandler.ENCHANTED_GOLDEN_APPLE_SHIELD_HEAL + " every " + ConfigHandler.ENCHANTED_GOLDEN_APPLE_SHIELD_HEAL_TIME + " seconds");
    	list.add("Anvil Repair: Block of Gold");
    }

    @Override
	  public void onUpdate(ItemStack stack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
    	if(hasTag(stack) && stack.getTagCompound().hasKey("damage") && stack.getTagCompound().getInteger("damage") > 0) {
    		if(world.getWorldTime()%(20 * ConfigHandler.ENCHANTED_GOLDEN_APPLE_SHIELD_HEAL_TIME) == 0)
    			stack.getTagCompound().setInteger("damage", stack.getTagCompound().getInteger("damage") - ConfigHandler.ENCHANTED_GOLDEN_APPLE_SHIELD_HEAL < 0 ? 0 : stack.getTagCompound().getInteger("damage") - ConfigHandler.ENCHANTED_GOLDEN_APPLE_SHIELD_HEAL);
    	}
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return repair.getItem() == Item.getItemFromBlock(Blocks.GOLD_BLOCK);
    }

    private static boolean hasTag(ItemStack stack) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
			return false;
		}
		return true;
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return true;
    }
}
