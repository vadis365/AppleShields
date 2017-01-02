package apple_shields.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class ShieldDestroyMessage implements IMessage
{
    public int entityId;
    
    public ShieldDestroyMessage()
    {
    }
    
    public ShieldDestroyMessage(EntityPlayer player)
    {
        entityId = player.getEntityId();
    }
    
    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(entityId);
    }
    
    @Override
    public void fromBytes(ByteBuf buf)
    {
        entityId = buf.readInt();
    }
}
