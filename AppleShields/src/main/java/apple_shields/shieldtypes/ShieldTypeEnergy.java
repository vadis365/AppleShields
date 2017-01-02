package apple_shields.shieldtypes;

import java.util.List;

import apple_shields.items.ItemAppleShield;
import apple_shields.items.ItemEnergyShield;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ShieldTypeEnergy extends ShieldTypeBasic {

	public ShieldTypeEnergy(ItemStack shieldItem, ItemStack repairItem, int durability) {
		super(shieldItem, repairItem, durability);
	}

	@Override
	public void addInformation(ItemAppleShield shield, ItemStack stack, EntityPlayer player, List<String> information, boolean advanced) {
		if (shield instanceof ItemEnergyShield) {
			ItemEnergyShield energyShield = (ItemEnergyShield) shield;
			information.add("Energy Stored: " + energyShield.getEnergyStored(stack) + " / " + energyShield.getMaxEnergyStored(stack));
			information.add("Anvil Repair: " + getRepairItem().getDisplayName());
		} else {
			information.add("This item is broken!");
			information.add("Please alert the mod dev if this is reproducible!");
		}
	}
}
