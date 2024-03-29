package com.critmxbelvix.simplyrs.common.items;

import com.critmxbelvix.simplyrs.common.creativetabs.SimplyRSCreativeTab;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RedstoneWrench extends Item{
    private static final Logger LOGGER = LogManager.getLogger();

    final static String name = "redstone_wrench";
    final static CreativeModeTab tab = SimplyRSCreativeTab.SRS_TAB;
    final static Item.Properties properties = new Properties().tab(tab);

    public RedstoneWrench(Properties pProperties) {
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
