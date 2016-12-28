package apple_shields.items;

import java.util.List;

import apple_shields.AppleShields;
import cofh.api.energy.IEnergyContainerItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Optional.Interface(iface = "cofh.api.energy.IEnergyContainerItem", modid = "CoFHAPI")
public abstract class ItemEnergy extends ItemAppleShield implements IEnergyContainerItem {

	private final int capacity;

	public ItemEnergy(int capacity) {
		super(capacity);
		this.capacity = capacity;
		setMaxStackSize(1);
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return 1 - (double) getEnergyStored(stack) / (double) getMaxEnergyStored(stack);
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return AppleShields.IS_RF_PRESENT && getDurabilityForDisplay(stack) > 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		super.getSubItems(item, tab, list);
		if (AppleShields.IS_RF_PRESENT) {
			ItemStack charged = new ItemStack(item);
			receiveEnergy(charged, capacity, false);
			list.add(charged);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag) {
		list.add("Charge: " + getEnergyStored(stack) + "RF / " + getMaxEnergyStored(stack) + "RF");

	}

	/* ENERGY */

	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
		if (container.getTagCompound() == null)
			container.setTagCompound(new NBTTagCompound());
		int energy = container.getTagCompound().getInteger("damage");
		int energyReceived = Math.min(capacity - energy, maxReceive);

		if (!simulate) {
			energy += energyReceived;
			container.getTagCompound().setInteger("damage", energy);
		}
		return energyReceived;
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {
		if (container.getTagCompound() == null || !container.getTagCompound().hasKey("damage"))
			return 0;
		int energy = container.getTagCompound().getInteger("damage");
		int energyExtracted = Math.min(energy, maxExtract);

		if (!simulate) {
			energy -= energyExtracted;
			container.getTagCompound().setInteger("damage", energy);
		}
		return energyExtracted;
	}

	@Override
	public int getEnergyStored(ItemStack container) {
		if (container.getTagCompound() == null || !container.getTagCompound().hasKey("damage"))
			return 0;
		return container.getTagCompound().getInteger("damage");
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {
		return capacity;
	}

	protected static boolean hasTag(ItemStack stack) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
			return false;
		}
		return true;
	}

}
