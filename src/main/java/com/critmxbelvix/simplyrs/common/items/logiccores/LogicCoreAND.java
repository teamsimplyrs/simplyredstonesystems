package com.critmxbelvix.simplyrs.common.items.logiccores;

import com.critmxbelvix.simplyrs.common.creativetabs.SimplyRSCreativeTab;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class LogicCoreAND extends Item {

    final static String name = "logiccore_and";

    final static CreativeModeTab tab = SimplyRSCreativeTab.SRS_CRAFTS_TAB;
    final static Properties core_and_properties = new Properties().tab(tab);

    public LogicCoreAND(Properties pProperties) {
        super(pProperties);
    }

    public static String m_getName()
    {
        return name;
    }

    public static CreativeModeTab m_getTab()
    {
        return tab;
    }
    public static Properties m_getProperties()
    {
        return core_and_properties;
    }
}