package apple_shields.items;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemWhiteAppleShieldRF extends ItemEnergy {

	public ItemWhiteAppleShieldRF(int hitPoints) {
		super(hitPoints);
	}

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return true;
    }
}
