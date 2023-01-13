package com.critmxbelvix.simplyrs.common.items.srsarmory;

import net.minecraft.world.item.Item;

public class ActivatorPickaxe extends ActivatorToolAbstract{
    static final String name = "activator_pickaxe";
    static final Item.Properties properties = new Item.Properties().tab(tab);

    public ActivatorPickaxe(Item.Properties pProperties) {
        super(pProperties);
    }

    public static String mGetName() {
        return name;
    }

    public static Item.Properties mGetProperties() {
        return properties;
    }
}

