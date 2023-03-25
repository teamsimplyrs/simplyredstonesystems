package com.critmxbelvix.simplyrs.common.items.logiccores;

import com.critmxbelvix.simplyrs.common.creativetabs.SimplyRSCreativeTab;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class ArithmeticCore extends Item {
    final static String name = "arithmetic_core";

    final static CreativeModeTab tab = SimplyRSCreativeTab.SRS_CRAFTS_TAB;
    final static Item.Properties core_arithmetic_properties = new Item.Properties().tab(tab);

    public ArithmeticCore(Item.Properties pProperties) {
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
        return core_arithmetic_properties;
    }
}
