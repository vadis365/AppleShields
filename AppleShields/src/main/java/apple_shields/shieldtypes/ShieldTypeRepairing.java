package apple_shields.shieldtypes;

import apple_shields.items.ItemAppleShield;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class ShieldTypeRepairing extends ShieldTypeBasic
{
    private int repairAmount;
    private int repairTime;
    
    public ShieldTypeRepairing(ItemStack shieldItem, ItemStack repairItem, SoundEvent hitSound, SoundEvent breakSound, int durability, int repairAmount, int repairTime)
    {
        super(shieldItem, repairItem, hitSound, breakSound, durability);
        this.repairAmount = repairAmount;
        this.repairTime = repairTime;
    }
    
    @Override
    public void onUpdate(ItemAppleShield shield, ItemStack stack, World world, Entity entity, int slot, boolean selected)
    {
        super.onUpdate(shield, stack, world, entity, slot, selected);
        
        if (world.getTotalWorldTime() % (20 * repairTime) == 0)
        {
            shield.repairShield(repairAmount, stack);
        }
    }
}
