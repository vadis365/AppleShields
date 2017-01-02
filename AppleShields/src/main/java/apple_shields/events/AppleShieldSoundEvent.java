package apple_shields.events;

import apple_shields.AppleShields;
import apple_shields.items.ItemAppleShield;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AppleShieldSoundEvent
{
    @SubscribeEvent
    public void onShieldSoundTriggered(PlaySoundAtEntityEvent event)
    {
        if (event.getSound() == SoundEvents.ITEM_SHIELD_BLOCK && event.getEntity() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            ItemStack stack = player.getActiveItemStack();
            
            if (stack != null && stack.getItem() instanceof ItemAppleShield)
            {
                event.setSound(AppleShields.soundAppleCrunch);
            }
        }
    }
}
