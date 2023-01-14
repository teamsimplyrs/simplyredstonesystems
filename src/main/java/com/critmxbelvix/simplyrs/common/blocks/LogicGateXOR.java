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

import java.util.stream.Stream;

public class LogicGateXOR extends GateBlock {

    final static String name = "logicgate_xor";
    final static CreativeModeTab tab = SimplyRSCreativeTab.SRS_TAB;
    private static final Properties gate_xor_properties = BlockBehaviour.Properties.of(Material.STONE).strength(0.1f).dynamicShape();
    private static final Logger LOGGER = LogManager.getLogger();

    public LogicGateXOR(Properties m_properties) {
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
        return gate_xor_properties;
    }

    //Sets blockstates when block is placed
    public BlockState getStateForPlacement(BlockPlaceContext pContext){
        BlockState blockstate = super.getStateForPlacement(pContext);
        Direction direction = pContext.getHorizontalDirection();
        Direction direction1 = direction.getCounterClockWise();
        Direction direction2 = direction.getOpposite();
        Direction direction3 = direction.getClockWise();
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
        long count = Stream.of(input1, input2, input3).filter(b -> b).count();

        return count % 2 != 0;
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
