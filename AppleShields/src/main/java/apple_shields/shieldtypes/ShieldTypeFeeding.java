package apple_shields.shieldtypes;

import java.util.List;

import apple_shields.AppleShields;
import apple_shields.items.ItemAppleShield;
import apple_shields.packets.ShieldDestroyMessage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class ShieldTypeFeeding extends ShieldTypeBasic
{
    private int hunger;
    private float saturation;
    private int feedTime;
    private int durabilityPerHunger;
    
    public ShieldTypeFeeding(ItemStack shieldItem, ItemStack repairItem, SoundEvent hitSound, SoundEvent breakSound, int durability, int hunger, float saturation, int feedTime, int durabilityPerHunger)
    {
        super(shieldItem, repairItem, hitSound, breakSound, durability);
        
        this.hunger = hunger;
        this.saturation = saturation;
        this.feedTime = feedTime;
        this.durabilityPerHunger = durabilityPerHunger;
    }
    
    @Override
    public void addInformation(ItemAppleShield shield, ItemStack stack, EntityPlayer player, List<String> information, boolean advanced)
    {
        super.addInformation(shield, stack, player, information, advanced);
        
        ITextComponent line1 = new TextComponentString("Happy Darkosto Appreciation Day!");
        ITextComponent line2 = new TextComponentString("Stay, have some food!");
        
        line2.setStyle(line1.getStyle().setColor(TextFormatting.GREEN));
        
        information.add("");
        information.add(line1.getFormattedText());
        information.add(line2.getFormattedText());
    }
    
    @Override
    public void onUpdate(ItemAppleShield shield, ItemStack stack, World world, Entity entity, int slot, boolean selected)
    {
        super.onUpdate(shield, stack, world, entity, slot, selected);
        
        if (world.getTotalWorldTime() % (feedTime) == 0 && entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entity;
            
            int foodLevel = -player.getFoodStats().getFoodLevel();
            float saturationLevel = -player.getFoodStats().getSaturationLevel();
            
            player.getFoodStats().addStats(hunger, saturation);
            
            foodLevel += player.getFoodStats().getFoodLevel();
            saturationLevel += player.getFoodStats().getSaturationLevel();
            
            if (foodLevel != 0 || saturationLevel != 0)
            {
                if (foodLevel != 0 || saturationLevel != 0)
                {
                    shield.damageShield(foodLevel * durabilityPerHunger, stack, player);
                    
                    if (stack.stackSize <= 0)
                    {
                        EnumHand hand = null;
                        
                        if (player.getHeldItem(EnumHand.MAIN_HAND) == stack)
                        {
                            hand = EnumHand.MAIN_HAND;
                        }
                        else if (player.getHeldItem(EnumHand.OFF_HAND) == stack)
                        {
                            hand = EnumHand.OFF_HAND;
                        }
                        
                        if(player instanceof EntityPlayerMP)
                        {
                            AppleShields.NETWORK_WRAPPER.sendTo(new ShieldDestroyMessage(stack), (EntityPlayerMP) player);
                        }
                        
                        ForgeEventFactory.onPlayerDestroyItem(player, stack, hand);
                        player.inventory.setInventorySlotContents(slot, null);
                    }
                }
            }
        }
    }
}
