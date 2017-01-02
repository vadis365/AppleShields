package apple_shields.events;

import apple_shields.AppleShields;
import apple_shields.items.ItemAppleShield;
import apple_shields.packets.ShieldDestroyMessage;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityShieldDamageEvent {
	@SubscribeEvent
	public void onEntityShielded(LivingHurtEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			ItemStack stack = player.getActiveItemStack();

			if ((stack != null) && (stack.getItem() instanceof ItemAppleShield)) {
				if (canBlockDamageSource(player, event.getSource()) && !player.isEntityInvulnerable(event.getSource())) {
					int damageInflicted = 1 + MathHelper.floor_float(event.getAmount());

					((ItemAppleShield) stack.getItem()).damageShield(damageInflicted, stack, player);

					if (player.getActiveItemStack().stackSize <= 0) {
						EnumHand hand = player.getActiveHand();
						ForgeEventFactory.onPlayerDestroyItem(player, stack, hand);
						AppleShields.NETWORK_WRAPPER.sendToAll(new ShieldDestroyMessage(player));
						player.setHeldItem(hand, null);
					}
				}
			}
		}
	}

	public boolean canBlockDamageSource(EntityLivingBase player, DamageSource source) {
		if (!source.isUnblockable() && player.isActiveItemStackBlocking()) {
			Vec3d sourceLocation = source.getDamageLocation();

			if (sourceLocation != null) {
				Vec3d playerLook = player.getLook(1.0F);
				Vec3d sourceToPlayer = sourceLocation.subtractReverse(new Vec3d(player.posX, player.posY, player.posZ)).normalize();
				sourceToPlayer = new Vec3d(sourceToPlayer.xCoord, 0.0, sourceToPlayer.zCoord);

				if (sourceToPlayer.dotProduct(playerLook) < 0.0D) {
					return true;
				}
			}
		}

		return false;
	}
}
