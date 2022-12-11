package com.critmxbelvix.simplyrs.common.blocks;

import com.critmxbelvix.simplyrs.common.creativetabs.SimplyRSCreativeTab;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class LogicGateOR extends GateBlock {
    final static String name = "logicgate_or";
    final static CreativeModeTab tab = SimplyRSCreativeTab.SRS_TAB;
    private static final Properties gate_or_properties = BlockBehaviour.Properties.of(Material.STONE).strength(0.1f).dynamicShape();

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
    public static Properties m_getProperties()
    {
        return gate_or_properties;
    }

    /* Redstone */

    @Override
    public int getDirectSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
        return pBlockState.getSignal(pBlockAccess, pPos, pSide);
    }

    @Override
    public int getSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
        boolean input1 = pBlockState.getValue(INPUT_1);
        boolean input2 = pBlockState.getValue(INPUT_2);
        boolean input3 = pBlockState.getValue(INPUT_3);

        if((input1 || input2 || input3) && pSide == pBlockState.getValue(FACING).getOpposite()) {
            return this.getOutputSignal(pBlockAccess, pPos, pBlockState);
        }
        else{
            return 0;
        }
    }
}
