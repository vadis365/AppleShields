package apple_shields.shieldtypes;

import apple_shields.items.ItemAppleShield;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ShieldTypeHealing extends ShieldTypeBasic {
	private int repairAmount;
	private int repairTime;

	public ShieldTypeHealing(ItemStack shieldItem, ItemStack repairItem, int durability, int repairAmount, int repairTime) {
		super(shieldItem, repairItem, durability);
		this.repairAmount = repairAmount;
		this.repairTime = repairTime;
	}

	@Override
	public void onUpdate(ItemAppleShield shield, ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		super.onUpdate(shield, stack, world, entity, slot, selected);
		if (world.getTotalWorldTime() % repairTime == 0)
			shield.repairShield(repairAmount, stack);
	}
}
