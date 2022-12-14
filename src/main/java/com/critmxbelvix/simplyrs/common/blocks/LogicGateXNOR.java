package com.critmxbelvix.simplyrs.common.blocks;

import com.critmxbelvix.simplyrs.common.creativetabs.SimplyRSCreativeTab;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import java.util.stream.Stream;

public class LogicGateXNOR extends GateBlock {

    final static String name = "logicgate_xnor";
    final static CreativeModeTab tab = SimplyRSCreativeTab.SRS_TAB;
    private static final BlockBehaviour.Properties gate_xnor_properties = BlockBehaviour.Properties.of(Material.STONE).strength(0.1f).dynamicShape();

    public LogicGateXNOR(BlockBehaviour.Properties m_properties) {
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
    public static BlockBehaviour.Properties m_getProperties()
    {
        return gate_xnor_properties;
    }

    /* Redstone */

    protected boolean shouldTurnOn(Level pLevel, BlockPos pPos, BlockState pState) {
        boolean input1 = pState.getValue(INPUT_1);
        boolean input2 = pState.getValue(INPUT_2);
        boolean input3 = pState.getValue(INPUT_3);
        long count = Stream.of(input1, input2, input3).filter(b -> b).count();

        if (count%2==0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getDirectSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
        return pBlockState.getSignal(pBlockAccess, pPos, pSide);
    }

    @Override
    public int getSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
        if (!pBlockState.getValue(POWERED)) {
            return 0;
        } else {
            return pBlockState.getValue(FACING).getOpposite() == pSide ? this.getOutputSignal(pBlockAccess, pPos, pBlockState) : 0;
        }
    }
}
