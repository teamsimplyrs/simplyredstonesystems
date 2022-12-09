package com.critmxbelvix.simplyrs.common.blocks;

import com.critmxbelvix.simplyrs.common.creativetabs.SimplyRSCreativeTab;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class LogicGateNOR extends Block {

    final String name = "logicgate_nor";
    final CreativeModeTab tab = SimplyRSCreativeTab.SRS_TAB;
    public static final Properties gate_nor_properties = BlockBehaviour.Properties.of(Material.STONE).strength(0.1f).dynamicShape();
    public LogicGateNOR(Properties m_properties) {
        super(m_properties);
    }
    public static final LogicGateNOR LOGICGATE_NOR = new LogicGateNOR(gate_nor_properties);


    public String m_getName()
    {
        return name;
    }
    public CreativeModeTab m_getTab()
    {
        return tab;
    }
    public Properties m_getProperties()
    {
        return gate_nor_properties;
    }

}
