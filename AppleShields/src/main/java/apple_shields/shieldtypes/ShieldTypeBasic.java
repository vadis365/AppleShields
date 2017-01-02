package apple_shields.shieldtypes;

import java.util.List;

import apple_shields.items.ItemAppleShield;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ShieldTypeBasic implements IShieldType {
	private ItemStack shieldItem;
	private ItemStack repairItem;
	private int durability;

	public ShieldTypeBasic(ItemStack shieldItem, ItemStack repairItem, int durability) {
		this.shieldItem = shieldItem;
		this.repairItem = repairItem;
		this.durability = durability;
	}

	public ItemStack getShieldItem() {
		return shieldItem;
	}

	public ItemStack getRepairItem() {
		return repairItem;
	}

	public int getDurability() {
		return durability;
	}

	public void addInformation(ItemAppleShield shield, ItemStack stack, EntityPlayer player, List<String> information, boolean advanced) {
		information.add("Base Durability: " + shield.getMaxDamage(stack));
		information.add("Anvil Repair: " + repairItem.getDisplayName());
	}

	public void onUpdate(ItemAppleShield sheild, ItemStack stack, World world, Entity entity, int slot,
			boolean selected) {

	}
}
