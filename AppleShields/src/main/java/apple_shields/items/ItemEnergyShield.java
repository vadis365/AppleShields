package apple_shields.items;

import java.util.List;

import apple_shields.AppleShields;
import apple_shields.confighandler.ConfigHandler;
import apple_shields.shieldtypes.IShieldType;
import cofh.api.energy.IEnergyContainerItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Optional.Interface(iface = "cofh.api.energy.IEnergyContainerItem", modid = "CoFHAPI")
public class ItemEnergyShield extends ItemAppleShield implements IEnergyContainerItem {
	private final int capacity;

	public ItemEnergyShield(IShieldType shieldType) {
		super(shieldType);

		this.capacity = shieldType.getDurability();
		setMaxStackSize(1);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		if (getEnergyStored(stack) > 0)
			return EnumAction.BLOCK;
		else
			return EnumAction.NONE;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> list) {
		super.getSubItems(item, tab, list);

		if (AppleShields.IS_RF_PRESENT) {
			ItemStack charged = new ItemStack(item);
			receiveEnergy(charged, capacity, false);
			list.add(charged);
		}
	}

	public boolean damageShield(int toDamage, ItemStack stack, EntityLivingBase entity) {
		extractEnergy(stack, toDamage * ConfigHandler.SHIELD_ENERGY_RF_PER_DAMAGE, false);
		return true;
	}

	public boolean repairShield(int toRepair, ItemStack stack) {
		return receiveEnergy(stack, toRepair, false) > 0;
	}

	/* ENERGY */

	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
		int energyStored = getEnergyStored(container);
		int energyReceived = Math.min(getMaxEnergyStored(container) - energyStored, maxReceive);

		if (!simulate) {
			energyStored += energyReceived;
			container.setItemDamage(getMaxEnergyStored(container) - energyStored);
		}

		return energyReceived;
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {
		int energyStored = getEnergyStored(container);
		int energyExtracted = Math.min(energyStored, maxExtract);

		if (!simulate) {
			energyStored -= energyExtracted;
			container.setItemDamage(getMaxEnergyStored(container) - energyStored);
		}

		return energyExtracted;
	}

	@Override
	public int getEnergyStored(ItemStack container) {
		return getMaxEnergyStored(container) - container.getItemDamage();
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {
		return capacity;
	}
}
