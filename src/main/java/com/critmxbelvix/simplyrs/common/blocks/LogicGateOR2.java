package com.critmxbelvix.simplyrs.common.blocks;

import com.critmxbelvix.simplyrs.common.creativetabs.SimplyRSCreativeTab;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class LogicGateOR2 extends Gate2Block{

    final static String name = "logicgate_or2";
    final static CreativeModeTab tab = SimplyRSCreativeTab.SRS_TAB;
    private final static Properties gate_or2_properties = Properties.of(Material.STONE).strength(0.1f).dynamicShape();
    public LogicGateOR2(Properties m_Properties) {
        super(m_Properties);
    }
    public static String m_getName() { return name; }
    public static CreativeModeTab m_getTab() { return tab; }
    public static Properties m_getProperties() { return gate_or2_properties; }

    public BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
        BlockState blockstate = super.getStateForPlacement(pContext);
        Direction north = pContext.getHorizontalDirection();
        Direction east = north.getClockWise();
        Direction west = north.getCounterClockWise();

        return this.defaultBlockState()
                .setValue(FACING,north)
                .setValue(INPUT_1,isInputOne(pContext.getLevel(),pContext.getClickedPos().relative(west),blockstate))
                .setValue(INPUT_2,isInputTwo(pContext.getLevel(),pContext.getClickedPos().relative(east),blockstate))
                .setValue(POWERED,shouldTurnOn(pContext.getLevel(),pContext.getClickedPos(),blockstate));
    }

    /* Redstone */

    protected boolean shouldTurnOn(Level pLevel, BlockPos pPos, BlockState pState) {
        Direction north = pState.getValue(FACING);
        Direction east = north.getClockWise();
        Direction west = north.getCounterClockWise();
        boolean input1 = isInputOne(pLevel,pPos.relative(west),pState);
        boolean input2 = isInputTwo(pLevel,pPos.relative(east),pState);

        return input1 || input2;
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
