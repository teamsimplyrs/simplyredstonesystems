package com.critmxbelvix.simplyrs.common.items.srsarmory;

import net.minecraft.world.item.Item;

public class ActivatorAxe extends ActivatorToolAbstract {

    static final String name = "activator_axe";
    static final Item.Properties properties = new Properties().tab(tab);

    public ActivatorAxe(Properties pProperties) {
        super(pProperties);
    }

    public static String mGetName() { return name; }
    public static Properties mGetProperties() { return properties; }

}
