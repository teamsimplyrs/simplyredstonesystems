package com.critmxbelvix.simplyrs.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class GateBlock extends Block{
    private static final VoxelShape SHAPE = Block.box(1, 0, 1, 15, 1, 15);

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty INPUT_1 = BooleanProperty.create("input_1");
    public static final BooleanProperty INPUT_2 = BooleanProperty.create("input_2");
    public static final BooleanProperty INPUT_3 = BooleanProperty.create("input_3");
    private static final Logger LOGGER = LogManager.getLogger();

    public GateBlock(Properties m_properties) {
        super(m_properties);
        this.registerDefaultState(
                this.getStateDefinition().any()
                        .setValue(INPUT_1,false)
                        .setValue(INPUT_2, false)
                        .setValue(INPUT_3, false)
        );
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return canSupportRigidBlock(pLevel, pPos.below());
    }

    /* FACING */

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
                .setValue(INPUT_3,isInputThree(pContext.getLevel(),pContext.getClickedPos().relative(direction3),blockstate));
    }

    public BlockState rotate(BlockState pState, Rotation pRotation){
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    public BlockState mirror(BlockState pState, Mirror pMirror){
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    /* Blockstates */

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder){
        pBuilder.add(FACING,INPUT_1,INPUT_2,INPUT_3);
    }

    /* Redstone */

    public boolean isSignalSource(BlockState pState) {
        return true;
    }

    public boolean isInputOne(LevelReader pLevel, BlockPos pPos, BlockState pState){
        Direction direction = pState.getValue(FACING);
        Direction direction1 = direction.getCounterClockWise();
        LOGGER.info(getInputSignalAt(pLevel, pPos, direction1));
        return getInputSignalAt(pLevel, pPos, direction1) > 0;
    }

    public boolean isInputTwo(LevelReader pLevel, BlockPos pPos, BlockState pState){
        Direction direction = pState.getValue(FACING).getOpposite();

        LOGGER.info(getInputSignalAt(pLevel, pPos, direction));
        return getInputSignalAt(pLevel, pPos, direction) > 0;
    }

    public boolean isInputThree(LevelReader pLevel, BlockPos pPos, BlockState pState){
        Direction direction = pState.getValue(FACING);
        Direction direction1 = direction.getClockWise();
        LOGGER.info(getInputSignalAt(pLevel, pPos, direction1));
        return getInputSignalAt(pLevel, pPos, direction1) > 0;
    }

    protected int getInputSignalAt(LevelReader pLevel, BlockPos pPos, Direction pSide) {
        BlockState blockstate = pLevel.getBlockState(pPos);
        if (this.isSideInput(blockstate)) {
            if (blockstate.is(Blocks.REDSTONE_BLOCK)) {
                LOGGER.info("BLOCK");
                return 15;
            } else {
                LOGGER.info("wire");
                return blockstate.is(Blocks.REDSTONE_WIRE) ? blockstate.getValue(RedStoneWireBlock.POWER) : pLevel.getDirectSignal(pPos, pSide);
            }
        } else {
            return 0;
        }
    }

    protected boolean isSideInput(BlockState pState) {
        return pState.isSignalSource();
    }

    protected int getOutputSignal(BlockGetter pLevel, BlockPos pPos, BlockState pState) {
        return 15;
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if(!pLevel.isClientSide()){
            if(pFacing == pState.getValue(FACING).getCounterClockWise()){
                return pState.setValue(INPUT_1, Boolean.valueOf(this.isInputOne(pLevel, pFacingPos, pState)));
            }
            else if(pFacing == pState.getValue(FACING).getOpposite()){
                return pState.setValue(INPUT_2, Boolean.valueOf(this.isInputTwo(pLevel, pFacingPos, pState)));
            }
            else if(pFacing == pState.getValue(FACING).getClockWise()){
                return pState.setValue(INPUT_3, Boolean.valueOf(this.isInputThree(pLevel, pFacingPos, pState)));
            }
            else{
                return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
            }
        }
        else{
            return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
        }
    }

}
