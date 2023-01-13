package com.critmxbelvix.simplyrs.common.items.srsarmory;

import com.critmxbelvix.simplyrs.common.creativetabs.SimplyRSCreativeTab;
import com.critmxbelvix.simplyrs.common.registers.ItemRegister;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import java.util.List;

public abstract class ActivatorArmor extends ArmorItem {

    static final CreativeModeTab tab = SimplyRSCreativeTab.SRS_ARMORY_TAB;
    static final Properties properties = new Properties().tab(tab);

    public ActivatorArmor(EquipmentSlot pSlot, Properties pProperties) {
        super(new ArmorMaterial() {
            @Override
            public int getDurabilityForSlot(EquipmentSlot pSlot) {
                return new int[]{13, 15, 16, 11}[pSlot.getIndex()] * 15;
            }

            @Override
            public int getDefenseForSlot(EquipmentSlot pSlot) {
                return new int[]{2, 5, 6, 2}[pSlot.getIndex()];
            }

            @Override
            public int getEnchantmentValue() {
                return 18;
            }

            @Override
            public SoundEvent getEquipSound() {
                return null;
            }

            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(ItemRegister.PIEZOREDSTONE_INGOT.get()));
            }

            @Override
            public String getName() {
                return "activator_armor";
            }

            @Override
            public float getToughness() {
                return 0;
            }

            @Override
            public float getKnockbackResistance() {
                return 0;
            }
        },pSlot,pProperties);
    }

    public static class ActivatorHelmet extends ActivatorArmor
    {
        static final String name = "activator_helmet";

        public ActivatorHelmet()
        {
            super(EquipmentSlot.HEAD, properties);
        }
        @Override
        public void appendHoverText(ItemStack itemStack, Level pLevel, List<Component> pList, TooltipFlag pFlag) {
            super.appendHoverText(itemStack, pLevel, pList, pFlag);
            pList.add(new TextComponent("Emits redstone pulse when wearer obtains a potion effect"));
            pList.add(new TextComponent("Configure traits in the Activation Augmenter"));
        }

        @Override
        public String getArmorTexture(ItemStack itemStack, Entity pEntity, EquipmentSlot pSlot, String pType) {
            return "xyz_layer_1.png";
        }
        public static String mGetName() { return name; }

    }

    public static class ActivatorChestplate extends ActivatorArmor
    {
        static final String name = "activator_chestplate";
        public ActivatorChestplate()
        {
            super(EquipmentSlot.CHEST,properties);
        }
        @Override
        public void appendHoverText(ItemStack itemStack, Level pLevel, List<Component> pList, TooltipFlag pFlag) {
            super.appendHoverText(itemStack, pLevel, pList, pFlag);
            pList.add(new TextComponent("Emits redstone pulse when wearer takes direct physical or projectile damage"));
            pList.add(new TextComponent("Configure traits in the Activation Augmenter"));
        }

        @Override
        public String getArmorTexture(ItemStack itemStack, Entity pEntity, EquipmentSlot pSlot, String pType) {
            return "xyz_layer_1.png";
        }
        public static String mGetName() { return name; }
    }

    public static class ActivatorLeggings extends ActivatorArmor
    {
        static final String name = "activator_leggings";
        public ActivatorLeggings()
        {
            super(EquipmentSlot.LEGS,properties);
        }
        @Override
        public void appendHoverText(ItemStack itemStack, Level pLevel, List<Component> pList, TooltipFlag pFlag) {
            super.appendHoverText(itemStack, pLevel, pList, pFlag);
            pList.add(new TextComponent("Emits redstone pulse when wearer jumps"));
            pList.add(new TextComponent("Configure traits in the Activation Augmenter"));
        }

        @Override
        public String getArmorTexture(ItemStack itemStack, Entity pEntity, EquipmentSlot pSlot, String pType) {
            return "xyz_layer_2.png";
        }
        public static String mGetName() { return name; }
    }

    public static class ActivatorBoots extends ActivatorArmor
    {
        static final String name = "activator_boots";
        public ActivatorBoots()
        {
            super(EquipmentSlot.FEET,properties);
        }
        @Override
        public void appendHoverText(ItemStack itemStack, Level pLevel, List<Component> pList, TooltipFlag pFlag) {
            super.appendHoverText(itemStack, pLevel, pList, pFlag);
            pList.add(new TextComponent("Emits redstone pulse when wearer takes fall damage"));
            pList.add(new TextComponent("Configure traits in the Activation Augmenter"));
        }

        @Override
        public String getArmorTexture(ItemStack itemStack, Entity pEntity, EquipmentSlot pSlot, String pType) {
            return "xyz_layer_1.png";
        }
        public static String mGetName() { return name; }
    }

}
