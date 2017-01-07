package apple_shields.packets;

import apple_shields.AppleShields;
import apple_shields.items.ItemAppleShield;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
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
        
        ItemStack stack = player.getActiveItemStack();
        
        if (player.getEntityId() == message.entityId)
        {
            if (stack.getItem() instanceof ItemAppleShield)
            {
                ItemAppleShield item = (ItemAppleShield) stack.getItem();
                
                player.playSound(item.getShieldType().getBreakSound(item, stack), 1.8F, 0.8F + (player.worldObj.rand.nextFloat() * 0.4F));
            }
            else
            {
                player.playSound(AppleShields.SOUND_APPLE_CRUNCH, 1.8F, 0.8F + (player.worldObj.rand.nextFloat() * 0.4F));
            }
            
            player.renderBrokenItemStack(stack);
        }
        
        return null;
    }
}
