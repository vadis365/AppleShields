package apple_shields.items;

import java.util.List;

import apple_shields.AppleShields;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemAppleShield extends ItemShield {

    public int hitPoints;

    public ItemAppleShield(int hitPoints) {
        this.hitPoints = hitPoints;
        this.addPropertyOverride(new ResourceLocation("blocking"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, World worldIn, EntityLivingBase entityIn) {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });

    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        if (hasTag(stack) && stack.getTagCompound().hasKey("damage")) {
            return ((double) stack.getTagCompound().getInteger("damage") / (double) getMaxHitpoints());
        } else
            return 1;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return hasTag(stack) && stack.getTagCompound().hasKey("damage") && stack.getTagCompound().getInteger("damage") > 0;
    }

    public String getItemStackDisplayName(ItemStack stack) {
        return ("" + I18n.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name")).trim();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> list, boolean advanced) {
    	list.add("Base Durability: " + getMaxHitpoints());
    	list.add("Anvil Repair: Apple");
	}

    @Override
    public CreativeTabs getCreativeTab() {
        return AppleShields.TAB;
    }

    @Override
    public boolean isRepairable() {
        return true;
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return repair.getItem() == Items.APPLE? true : super.getIsRepairable(toRepair, repair);
    }

    public int getMaxHitpoints() {
        return hitPoints;
    }

    private static boolean hasTag(ItemStack stack) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
			return false;
		}
		return true;
	}

    @Override
    public boolean updateItemStackNBT(NBTTagCompound nbt) {
        return super.updateItemStackNBT(nbt);
    }

    public boolean damageShield(int damageIn, ItemStack stack, EntityLivingBase entityIn) {
        if (hasTag(stack))
        	if(!stack.getTagCompound().hasKey("damage"))
        		stack.getTagCompound().setInteger("damage", 0);

        int damage = stack.getTagCompound().getInteger("damage") + damageIn;
        if (damage >= getMaxHitpoints()) {
            --stack.stackSize;

            if (entityIn instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entityIn;
                player.addStat(StatList.getObjectBreakStats(stack.getItem()));
            }

            if (stack.stackSize < 0)
                stack.stackSize = 0;

        } else
            stack.getTagCompound().setInteger("damage", damage);

        return true;
    }
}
