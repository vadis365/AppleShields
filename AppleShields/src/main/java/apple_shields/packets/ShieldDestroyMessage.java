package apple_shields.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class ShieldDestroyMessage implements IMessage {
	public ItemStack stack;

	public ShieldDestroyMessage() {
	}

	public ShieldDestroyMessage(ItemStack stack) {
		this.stack = stack;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeItemStack(buf, stack);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		stack = ByteBufUtils.readItemStack(buf);
	}
}
