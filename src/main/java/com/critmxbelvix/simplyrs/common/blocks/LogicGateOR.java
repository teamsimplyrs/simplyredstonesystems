package com.critmxbelvix.simplyrs.common.blocks;

import com.critmxbelvix.simplyrs.common.creativetabs.SimplyRSCreativeTab;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class LogicGateOR extends GateBlock {
    final static String name = "logicgate_or";
    final static CreativeModeTab tab = SimplyRSCreativeTab.SRS_TAB;
    public static final Properties gate_or_properties = BlockBehaviour.Properties.of(Material.STONE).strength(0.1f).dynamicShape();

    public LogicGateOR(Properties m_properties) {
        super(m_properties);
    }

    public static String m_getName()
    {
        return name;
    }
    public static CreativeModeTab m_getTab()
    {
        return tab;
    }
    public Properties m_getProperties()
    {
        return gate_or_properties;
    }
}
