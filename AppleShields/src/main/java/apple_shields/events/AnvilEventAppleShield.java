package apple_shields.events;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import apple_shields.AppleShields;
import apple_shields.confighandler.ConfigHandler;
import apple_shields.items.ItemAppleShield;
import apple_shields.items.ItemWhiteAppleShieldRF;

public class AnvilEventAppleShield {

    @SubscribeEvent
    public void onAnvilUpdate(AnvilUpdateEvent event) {
        if (event.getLeft() != null && event.getLeft().getItem() instanceof ItemAppleShield) {
            if (event.getRight() != null && event.getLeft().getItem().getIsRepairable(event.getLeft(), event.getRight())) {
                if (event.getLeft().getTagCompound() != null && event.getLeft().getTagCompound().hasKey("damage")) {
                    int damage = event.getLeft().getTagCompound().getInteger("damage");
                    int repairPerItem = ((ItemAppleShield) event.getLeft().getItem()).getMaxHitpoints() / 3;
                    ItemStack output = event.getLeft().copy();
                    if (damage == 0) {
                        if (!event.getName().isEmpty())
                            output.setStackDisplayName(event.getName());
                        event.setOutput(output);
                        event.setMaterialCost(0);
                    } else {                    	
                        if (!event.getName().isEmpty())
                            output.setStackDisplayName(event.getName());
                        if (output.getTagCompound() == null)
                            output.setTagCompound(event.getLeft().getTagCompound());
                        if(!(output.getItem() instanceof ItemWhiteAppleShieldRF))
                        	output.getTagCompound().setInteger("damage", damage - repairPerItem < 0 ? 0 : damage - repairPerItem);
                        else
                        	output.getTagCompound().setInteger("damage", damage + repairPerItem > ConfigHandler.RF_WHITE_APPLE_SHIELD_DURABILITY ? ConfigHandler.RF_WHITE_APPLE_SHIELD_DURABILITY : damage + repairPerItem);

                        	event.setOutput(output);
                        	event.setCost(3);
                        	event.setMaterialCost(1);
                    }
                }
            }
        }
    }
}
