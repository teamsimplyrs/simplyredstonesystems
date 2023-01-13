package com.critmxbelvix.simplyrs.common.items.ingots;

import com.critmxbelvix.simplyrs.common.creativetabs.SimplyRSCreativeTab;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class PiezoRedstoneIngot extends Item {

    static final String name = "piezoredstone_ingot";
    static final CreativeModeTab tab = SimplyRSCreativeTab.SRS_CRAFTS_TAB;
    static final Properties properties = new Properties().tab(tab);

    public PiezoRedstoneIngot(Properties pProperties) {
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
    public static Properties mGetProperties()
    {
        return properties;
    }
}
