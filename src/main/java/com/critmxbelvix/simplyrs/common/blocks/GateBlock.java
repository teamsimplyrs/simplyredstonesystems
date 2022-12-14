package com.critmxbelvix.simplyrs.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.TickPriority;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Random;

import static java.util.Collections.singletonList;

public abstract class GateBlock extends Block{
    private static final VoxelShape SHAPE = Block.box(1, 0, 1, 15, 1, 15);

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty INPUT_1 = BooleanProperty.create("input_1");
    public static final BooleanProperty INPUT_2 = BooleanProperty.create("input_2");
    public static final BooleanProperty INPUT_3 = BooleanProperty.create("input_3");
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
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
        pBuilder.add(FACING,INPUT_1,INPUT_2,INPUT_3,POWERED);
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
                return blockstate.is(Blocks.REDSTONE_WIRE) ? blockstate.getValue(RedStoneWireBlock.POWER) : blockstate.getSignal(pLevel, pPos, pSide);
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
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pIsMoving && !pState.is(pNewState.getBlock())) {
            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
            pLevel.neighborChanged(pPos.relative(pState.getValue(FACING)),this, pPos);
        }
    }

    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRand) {
        LOGGER.info("tick");
        boolean flag = pState.getValue(POWERED);
        boolean flag1 = this.shouldTurnOn(pLevel, pPos, pState);
        if (flag && !flag1) {
            pLevel.setBlock(pPos, pState.setValue(POWERED, Boolean.valueOf(false)), 2);
        } else if(flag1){
            pLevel.setBlock(pPos, pState.setValue(POWERED, Boolean.valueOf(true)), 2);
        }
        pLevel.neighborChanged(pPos.relative(pState.getValue(FACING)),this,pPos);
    }

    protected abstract boolean shouldTurnOn(Level pLevel, BlockPos pPos, BlockState pState);

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        if(!pState.canSurvive(pLevel,pPos)){
            BlockEntity blockentity = pState.hasBlockEntity() ? pLevel.getBlockEntity(pPos) : null;
            dropResources(pState, pLevel, pPos, blockentity);
            pLevel.removeBlock(pPos, false);
        }
        else {
            Direction direction = pLevel.getBlockState(pPos).getValue(FACING);
            Direction direction1 = direction.getCounterClockWise();
            Direction direction2 = direction.getOpposite();
            Direction direction3 = direction.getClockWise();

            BlockState blockstate = pLevel.getBlockState(pPos)
                    .setValue(INPUT_1, pLevel.getSignal(pPos.relative(direction1), direction1) > 0)
                    .setValue(INPUT_2, pLevel.getSignal(pPos.relative(direction2), direction2) > 0)
                    .setValue(INPUT_3, pLevel.getSignal(pPos.relative(direction3), direction3) > 0);
            pLevel.setBlockAndUpdate(pPos, blockstate);
            pLevel.scheduleTick(pPos, this, 1, TickPriority.VERY_HIGH);
        }
    }

    //Block Drops

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
    public List<ItemStack> getDrops(BlockState pState, LootContext.Builder pBuilder) {

        List<ItemStack> drops = super.getDrops(pState, pBuilder);
        if (!drops.isEmpty())
            return drops;
        return singletonList(new ItemStack(this, 1));
    }
}
