package com.critmxbelvix.simplyrs.common.items.srsarmory;

import com.critmxbelvix.simplyrs.common.creativetabs.SimplyRSCreativeTab;
import com.critmxbelvix.simplyrs.common.registers.ItemRegister;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import java.util.List;

public class ActivatorSword extends SwordItem {

    static final String name = "activator_sword";

    static final CreativeModeTab tab = SimplyRSCreativeTab.SRS_ARMORY_TAB;
    static final Item.Properties properties = new Properties().tab(tab);

    public ActivatorSword() {
        super(new Tier() {
            @Override
            public int getUses() {
                return 500;
            }

            @Override
            public float getSpeed() {
                return 8f;
            }

            @Override
            public float getAttackDamageBonus() {
                return 2f;
            }

            @Override
            public int getLevel() {
                return 2;
            }

            @Override
            public int getEnchantmentValue() {
                return 18;
            }

            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(ItemRegister.PIEZOREDSTONE_INGOT.get()));
            }
        },3,-2f,properties);
    }

    public static String mGetName() {
        return name;
    }

    public static Properties mGetProperties() {
        return properties;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Level pLevel, List<Component> pList, TooltipFlag pFlag) {
        super.appendHoverText(itemStack, pLevel, pList, pFlag);
        pList.add(new TextComponent("Emits a redstone pulse when a mob is hit"));
        pList.add(new TextComponent("Configure traits in the Activation Augmenter"));
    }


    @Override
    public boolean hasContainerItem(ItemStack itemStack)
    {
        return true;
    }

    @Override
    public boolean isRepairable(ItemStack itemStack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        ItemStack newItemStack = new ItemStack(this);
        newItemStack.setDamageValue(itemStack.getDamageValue() + 1);

        return newItemStack.getDamageValue() >= newItemStack.getMaxDamage() ? newItemStack : ItemStack.EMPTY;
    }

}
