package com.critmxbelvix.simplyrs.common.items.srsarmory;

import com.critmxbelvix.simplyrs.common.creativetabs.SimplyRSCreativeTab;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public abstract class ActivatorToolAbstract extends Item {

    static final CreativeModeTab tab = SimplyRSCreativeTab.SRS_ARMORY_TAB;

    public ActivatorToolAbstract(Properties pProperties) {
        super(pProperties);
    }

    public static CreativeModeTab mGetTab() { return tab; }

}
