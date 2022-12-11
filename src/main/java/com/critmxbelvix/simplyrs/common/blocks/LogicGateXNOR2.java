package com.critmxbelvix.simplyrs.common.blocks;

import com.critmxbelvix.simplyrs.common.creativetabs.SimplyRSCreativeTab;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class LogicGateXNOR2 extends Gate2Block{

    final static String name = "logicgate_xnor2";
    final static CreativeModeTab tab = SimplyRSCreativeTab.SRS_TAB;
    private final static Properties gate_xnor2_properties = Properties.of(Material.STONE).strength(0.1f).dynamicShape();
    public LogicGateXNOR2(Properties m_Properties) {
        super(m_Properties);
    }
    public static String m_getName() { return name; }
    public static CreativeModeTab m_getTab() { return tab; }
    public static Properties m_getProperties() { return gate_xnor2_properties; }

    @Override
    public int getDirectSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
        return pBlockState.getSignal(pBlockAccess, pPos, pSide);
    }

    @Override
    public int getSignal(BlockState pState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide)
    {
        boolean input1 = pState.getValue(INPUT_1);
        boolean input2 = pState.getValue(INPUT_2);

        if ((input1 == input2) && pSide == pState.getValue(FACING).getOpposite())
        {
            return this.getOutputSignal(pBlockAccess,pState,pPos);
        } else {
            return 0;
        }

    }
}
