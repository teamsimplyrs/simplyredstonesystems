package com.critmxbelvix.simplyrs.common.items.logiccores;

import com.critmxbelvix.simplyrs.common.creativetabs.SimplyRSCreativeTab;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class LogicCoreLatch extends Item {

    final static String name = "logiccore_latch";

    final static CreativeModeTab tab = SimplyRSCreativeTab.SRS_CRAFTS_TAB;
    final static Item.Properties core_latch_properties = new Item.Properties().tab(tab);

    public LogicCoreLatch(Item.Properties pProperties) {
        super(pProperties);
    }

    public static String mGetName()
    {
        return name;
    }

    public static CreativeModeTab mGetTab()
    {
        return tab;
    }
    public static Item.Properties mGetProperties()
    {
        return core_latch_properties;
    }
}

