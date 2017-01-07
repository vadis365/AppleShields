package apple_shields.packets;

import apple_shields.AppleShields;
import apple_shields.items.ItemAppleShield;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ShieldDestroyPacketHandler implements IMessageHandler<ShieldDestroyMessage, IMessage>
{
    @Override
    @SideOnly(Side.CLIENT)
    public IMessage onMessage(ShieldDestroyMessage message, MessageContext ctx)
    {
        EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;
        ItemStack stack = ItemStack.loadItemStackFromNBT(message.stack);
        
        SoundEvent sound = AppleShields.SOUND_APPLE_CRUNCH;
        float volume = 1.8F;
        float pitch = 0.8F + 0.4F * player.worldObj.rand.nextFloat();
        
        if (stack != null && stack.getItem() instanceof ItemAppleShield)
        {
            ItemAppleShield item = (ItemAppleShield) stack.getItem();
            
            sound = item.getShieldType().getBreakSound(item, stack);
        }
        
        final SoundEvent finalSound = sound;
        
        Minecraft.getMinecraft().addScheduledTask(() ->
        {
            Minecraft.getMinecraft().thePlayer.playSound(finalSound, volume, pitch);
            player.renderBrokenItemStack(stack);
        });
        
        return null;
    }
}
