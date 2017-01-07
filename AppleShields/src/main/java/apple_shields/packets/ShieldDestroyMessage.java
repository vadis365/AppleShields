package apple_shields.packets;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class ShieldDestroyMessage implements IMessage
{
    public NBTTagCompound stack;
    
    public ShieldDestroyMessage()
    {
    }
    
    public ShieldDestroyMessage(ItemStack stack)
    {
        this.stack = new NBTTagCompound();
        stack.writeToNBT(this.stack);
    }
    
    @Override
    public void toBytes(ByteBuf buf)
    {
        if(stack != null)
        {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            
            try
            {
                CompressedStreamTools.writeCompressed(stack, stream);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            
            buf.writeInt(stream.size());
            buf.writeBytes(stream.toByteArray());
        }
        else
        {
            buf.writeInt(0);
        }
    }
    
    @Override
    public void fromBytes(ByteBuf buf)
    {
        int size = buf.readInt();
        
        if(size > 0)
        {
            byte[] data = new byte[size];
            buf.readBytes(data);
            
            ByteArrayInputStream stream = new ByteArrayInputStream(data);
            
            try
            {
                stack = CompressedStreamTools.readCompressed(stream);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
