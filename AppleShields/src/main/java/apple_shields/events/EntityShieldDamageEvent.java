package apple_shields.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import apple_shields.AppleShields;
import apple_shields.confighandler.ConfigHandler;
import apple_shields.items.ItemAppleShield;
import apple_shields.items.ItemWhiteAppleShieldRF;
import apple_shields.packets.ShieldDestroyMessage;

public class EntityShieldDamageEvent {

	@SubscribeEvent
	public void onEntityShielded(LivingHurtEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			ItemStack stack  = player.getActiveItemStack();
			if (stack != null && stack.getItem() instanceof ItemAppleShield) {
				int damageInflicted = 1 + MathHelper.floor_float(event.getAmount());
				if(!((ItemAppleShield) stack.getItem() instanceof ItemWhiteAppleShieldRF))
					((ItemAppleShield) stack.getItem()).damageShield(damageInflicted, stack, player);
				else
					((ItemWhiteAppleShieldRF) stack.getItem()).extractEnergy(stack, damageInflicted * ConfigHandler.RF_WHITE_APPLE_SHIELD_DAMAGE, false);

				if (player.getActiveItemStack().stackSize <= 0 && !((ItemAppleShield) stack.getItem() instanceof ItemWhiteAppleShieldRF)) {
					EnumHand enumhand = player.getActiveHand();
					ForgeEventFactory.onPlayerDestroyItem(player, stack, enumhand);
					AppleShields.NETWORK_WRAPPER.sendToAll(new ShieldDestroyMessage(player));
					if (enumhand == EnumHand.MAIN_HAND)
						player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, null);
					else
						player.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, null);
				}
				else
					if ((ItemAppleShield) stack.getItem() instanceof ItemWhiteAppleShieldRF && ((ItemWhiteAppleShieldRF) stack.getItem()).getEnergyStored(stack) <= 0) {
						EnumHand enumhand = player.getActiveHand();
						ForgeEventFactory.onPlayerDestroyItem(player, stack, enumhand);
						AppleShields.NETWORK_WRAPPER.sendToAll(new ShieldDestroyMessage(player));
						if (enumhand == EnumHand.MAIN_HAND)
							player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, null);
						else
							player.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, null);
						
					}
			}
		}
	}
}
