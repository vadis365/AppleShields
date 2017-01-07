package apple_shields.shieldtypes;

import java.util.List;

import apple_shields.items.ItemAppleShield;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class ShieldTypeBasic implements IShieldType
{
    private ItemStack shieldItem;
    private ItemStack repairItem;
    private SoundEvent hitSound;
    private SoundEvent breakSound;
    private int durability;
    
    public ShieldTypeBasic(ItemStack shieldItem, ItemStack repairItem, SoundEvent hitSound, SoundEvent breakSound, int durability)
    {
        this.shieldItem = shieldItem;
        this.repairItem = repairItem;
        this.hitSound = hitSound;
        this.breakSound = breakSound;
        this.durability = durability;
    }
    
    @Override
    public ItemStack getShieldItem()
    {
        return shieldItem;
    }
    
    @Override
    public ItemStack getRepairItem()
    {
        return repairItem;
    }
    
    @Override
    public SoundEvent getHitSound(ItemAppleShield shield, ItemStack stack)
    {
        return hitSound;
    }
    
    @Override
    public SoundEvent getBreakSound(ItemAppleShield shield, ItemStack stack)
    {
        return breakSound;
    }
    
    @Override
    public int getDurability()
    {
        return durability;
    }
    
    @Override
    public void addInformation(ItemAppleShield shield, ItemStack stack, EntityPlayer player, List<String> information, boolean advanced)
    {
        information.add("Base Durability: " + shield.getMaxDamage(stack));
        information.add("Anvil Repair: " + repairItem.getDisplayName());
    }
    
    @Override
    public void onUpdate(ItemAppleShield shield, ItemStack stack, World world, Entity entity, int slot, boolean selected)
    {
        
    }
}
