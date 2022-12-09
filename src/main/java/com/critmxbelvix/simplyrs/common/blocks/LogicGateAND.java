package com.critmxbelvix.simplyrs.common.blocks;

import com.critmxbelvix.simplyrs.common.creativetabs.SimplyRSCreativeTab;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class LogicGateAND extends Block {

    final String name = "logicgate_and";
    final CreativeModeTab tab = SimplyRSCreativeTab.SRS_TAB;
    public static final BlockBehaviour.Properties gate_and_properties = BlockBehaviour.Properties.of(Material.STONE).strength(0.1f).dynamicShape();
    public LogicGateAND(BlockBehaviour.Properties m_properties) {
        super(m_properties);
    }
    public static final LogicGateAND LOGICGATE_AND = new LogicGateAND(gate_and_properties);


    public String m_getName()
    {
        return name;
    }
    public CreativeModeTab m_getTab()
    {
        return tab;
    }
    public BlockBehaviour.Properties m_getProperties()
    {
        return gate_and_properties;
    }

}
