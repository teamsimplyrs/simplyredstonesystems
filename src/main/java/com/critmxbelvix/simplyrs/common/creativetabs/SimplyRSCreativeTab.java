package com.critmxbelvix.simplyrs.common.creativetabs;

import com.critmxbelvix.simplyrs.common.registers.BlockRegister;
import com.critmxbelvix.simplyrs.common.registers.ItemRegister;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SimplyRSCreativeTab
{
    public static final CreativeModeTab SRS_TAB = new CreativeModeTab("srscreativetab") {
        @Override @NotNull
        public ItemStack makeIcon() {
            return new ItemStack(BlockRegister.LOGICGATE_OR.get());
        }
    };
}
