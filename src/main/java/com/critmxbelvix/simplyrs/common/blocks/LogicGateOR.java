package com.critmxbelvix.simplyrs.common.blocks;

import com.critmxbelvix.simplyrs.common.creativetabs.SimplyRSCreativeTab;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;

public class LogicGateOR extends Block {
    final String name = "logicgate_or";
    final CreativeModeTab tab = SimplyRSCreativeTab.SRS_TAB;
    public static final Properties gate_or_properties = BlockBehaviour.Properties.of(Material.STONE).strength(0.1f).dynamicShape();
    public LogicGateOR(Properties m_properties) {
        super(m_properties);
    }
    public static final LogicGateOR LOGICGATE_OR = new LogicGateOR(gate_or_properties);


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
        return gate_or_properties;
    }
}
