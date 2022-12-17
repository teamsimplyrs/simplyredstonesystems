package com.critmxbelvix.simplyrs.common.blocks;

import com.critmxbelvix.simplyrs.common.creativetabs.SimplyRSCreativeTab;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import java.util.stream.Stream;

public class LogicGateXOR2 extends Gate2Block{

    final static String name = "logicgate_xor2";
    final static CreativeModeTab tab = SimplyRSCreativeTab.SRS_TAB;
    private final static Properties gate_xor2_properties = Properties.of(Material.STONE).strength(0.1f).dynamicShape();
    public LogicGateXOR2(Properties m_Properties) {
        super(m_Properties);
    }
    public static String m_getName() { return name; }
    public static CreativeModeTab m_getTab() { return tab; }
    public static Properties m_getProperties() { return gate_xor2_properties; }

    /* Redstone */

    protected boolean shouldTurnOn(Level pLevel, BlockPos pPos, BlockState pState) {
        boolean input1 = pState.getValue(INPUT_1);
        boolean input2 = pState.getValue(INPUT_2);
        long count = Stream.of(input1, input2).filter(b -> b).count();

        return count % 2 != 0;
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
