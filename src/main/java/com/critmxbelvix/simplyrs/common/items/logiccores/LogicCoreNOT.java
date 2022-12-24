package com.critmxbelvix.simplyrs.common.items.logiccores;

import com.critmxbelvix.simplyrs.common.creativetabs.SimplyRSCreativeTab;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class LogicCoreNOT extends Item{
    final static String name = "logiccore_not";
    final static CreativeModeTab tab = SimplyRSCreativeTab.SRS_CRAFTS_TAB;
    final static Item.Properties core_not_properties = new Item.Properties().tab(tab);

    public LogicCoreNOT(Properties pProperties) {
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
    public static Item.Properties m_getProperties()
    {
        return core_not_properties;
    }
}