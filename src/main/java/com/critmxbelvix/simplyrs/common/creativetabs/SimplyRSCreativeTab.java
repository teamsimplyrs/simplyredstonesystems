package com.critmxbelvix.simplyrs.common.creativetabs;

import com.critmxbelvix.simplyrs.common.registers.BlockRegister;
import com.critmxbelvix.simplyrs.common.registers.ItemRegister;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SimplyRSCreativeTab
{
    public static final CreativeModeTab SRS_TAB = new CreativeModeTab("srscreativetab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(BlockRegister.LOGICGATEOR.get());
        }
    };
}
