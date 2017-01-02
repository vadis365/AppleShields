package apple_shields.shieldtypes;

import java.util.List;

import apple_shields.items.ItemAppleShield;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IShieldType
{
    public ItemStack getShieldItem();
    
    public ItemStack getRepairItem();
    
    public int getDurability();
    
    public void addInformation(ItemAppleShield shield, ItemStack stack, EntityPlayer player, List<String> information, boolean advanced);
    
    public void onUpdate(ItemAppleShield sheild, ItemStack stack, World world, Entity entity, int slot, boolean selected);
}
