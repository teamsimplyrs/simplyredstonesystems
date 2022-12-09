package com.critmxbelvix.simplyrs.common.blocks;

import com.critmxbelvix.simplyrs.common.creativetabs.SimplyRSCreativeTab;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class LogicGateNAND extends Block {

    final String name = "logicgate_nand";
    final CreativeModeTab tab = SimplyRSCreativeTab.SRS_TAB;
    public static final BlockBehaviour.Properties gate_nand_properties = BlockBehaviour.Properties.of(Material.STONE).strength(0.1f).dynamicShape();
    public LogicGateNAND(BlockBehaviour.Properties m_properties) {
        super(m_properties);
    }
    public static final LogicGateNAND LOGICGATE_NAND = new LogicGateNAND(gate_nand_properties);


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
        return gate_nand_properties;
    }

}
