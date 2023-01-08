package com.critmxbelvix.simplyrs.common.blocks;

import com.critmxbelvix.simplyrs.common.creativetabs.SimplyRSCreativeTab;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogicGateOR extends GateBlock {
    private static final Logger LOGGER = LogManager.getLogger();
    final static String name = "logicgate_or";
    final static CreativeModeTab tab = SimplyRSCreativeTab.SRS_TAB;
    private static final Properties gate_or_properties = BlockBehaviour.Properties.of(Material.STONE).strength(0.1f).dynamicShape();

    public LogicGateOR(Properties m_properties) {
        super(m_properties);
    }

    public static String mGetName()
    {
        return name;
    }
    public static CreativeModeTab mGetTab()
    {
        return tab;
    }
    public static Properties mGetProperties()
    {
        return gate_or_properties;
    }

    //Sets blockstates when block is placed
    public BlockState getStateForPlacement(BlockPlaceContext pContext){
        BlockState blockstate = super.getStateForPlacement(pContext);
        Direction direction = pContext.getHorizontalDirection();
        Direction direction1 = direction.getCounterClockWise();
        Direction direction2 = direction.getOpposite();
        Direction direction3 = direction.getClockWise();
        LOGGER.info(shouldTurnOn(pContext.getLevel(),pContext.getClickedPos(),blockstate)+ " " + pContext.getClickedPos());
        return this.defaultBlockState()
                .setValue(FACING,pContext.getHorizontalDirection())
                .setValue(INPUT_1,isInputOne(pContext.getLevel(),pContext.getClickedPos().relative(direction1),blockstate))
                .setValue(INPUT_2,isInputTwo(pContext.getLevel(),pContext.getClickedPos().relative(direction2),blockstate))
                .setValue(INPUT_3,isInputThree(pContext.getLevel(),pContext.getClickedPos().relative(direction3),blockstate))
                .setValue(POWERED,shouldTurnOn(pContext.getLevel(),pContext.getClickedPos(),blockstate));
    }

    /* Redstone */

    protected boolean shouldTurnOn(Level pLevel, BlockPos pPos, BlockState pState) {
        Direction direction = pState.getValue(FACING);
        Direction direction1 = direction.getCounterClockWise();
        Direction direction2 = direction.getOpposite();
        Direction direction3 = direction.getClockWise();
        boolean input1 = isInputOne(pLevel,pPos.relative(direction1),pState);
        boolean input2 = isInputTwo(pLevel,pPos.relative(direction2),pState);
        boolean input3 = isInputThree(pLevel,pPos.relative(direction3),pState);
        LOGGER.info(input1 || input2 || input3);
        return input1 || input2 || input3;
    }

    @Override
    public int getDirectSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
        return pBlockState.getSignal(pBlockAccess, pPos, pSide);
    }

    /* getSignal is called by a neighboring block(usually redstone wire) to see what it's power value should be set
    as. For gate value, it's set to be the highest possible value of 15.
     */
    @Override
    public int getSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
        if (!pBlockState.getValue(POWERED)) {
            return 0;
        } else {
            return pBlockState.getValue(FACING).getOpposite() == pSide ? this.getOutputSignal(pBlockAccess, pPos, pBlockState) : 0;
        }
    }
}
