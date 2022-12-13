package com.critmxbelvix.simplyrs.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.TickPriority;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Random;

import static java.util.Collections.singletonList;

public abstract class Gate2Block extends Block
{
    private static final VoxelShape SHAPE = Block.box(1,0,1,15,1,15);

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public static final BooleanProperty INPUT_1 = BooleanProperty.create("input_1");
    public static final BooleanProperty INPUT_2 = BooleanProperty.create("input_2");

    private static final Logger LOGGER = LogManager.getLogger();

    public Gate2Block(Properties m_Properties) {
        super(m_Properties);
        this.registerDefaultState(
                this.getStateDefinition().any()
                        .setValue(INPUT_1,false)
                        .setValue(INPUT_2,false)
        );
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
    {
        return SHAPE;
    }

    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos)
    {
        return canSupportRigidBlock(pLevel,pPos.below());
    }

    // Placement Blockstates

    public BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
        BlockState blockstate = super.getStateForPlacement(pContext);
        Direction north = pContext.getHorizontalDirection();
        Direction east = north.getClockWise();
        Direction west = north.getCounterClockWise();

        return this.defaultBlockState()
                .setValue(FACING,north)
                .setValue(INPUT_1,isInputOne(blockstate,pContext.getLevel(),pContext.getClickedPos().relative(west)))
                .setValue(INPUT_2,isInputTwo(blockstate,pContext.getLevel(),pContext.getClickedPos().relative(east)));
    }
    public BlockState rotate(BlockState pState, Rotation pRotation){
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    public BlockState mirror(BlockState pState, Mirror pMirror){
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder){
        pBuilder.add(FACING,INPUT_1,INPUT_2);
    }

    // Redstone Control

    public boolean isSignalSource(BlockState pState) {
        return true;
    }

    public boolean isInputOne(BlockState pState, LevelReader pLevel, BlockPos pPos)
    {
        Direction faceWest= pState.getValue(FACING).getClockWise();

        LOGGER.info(getInputSignalAt(pLevel,pPos,faceWest));
        return getInputSignalAt(pLevel,pPos,faceWest) > 0;
    }

    public boolean isInputTwo(BlockState pState, LevelReader pLevel, BlockPos pPos)
    {
        Direction faceEast = pState.getValue(FACING).getCounterClockWise();

        LOGGER.info(getInputSignalAt(pLevel,pPos,faceEast));
        return getInputSignalAt(pLevel,pPos,faceEast) > 0;
    }

    protected int getInputSignalAt(LevelReader pLevel, BlockPos pPos, Direction pSide) {
        BlockState blockstate = pLevel.getBlockState(pPos);

        if (this.isSideInput(blockstate)) {
            if (blockstate.is(Blocks.REDSTONE_BLOCK)) {
                LOGGER.info("BLOCK");
                return 15;
            } else {
                return blockstate.is(Blocks.REDSTONE_WIRE) ? blockstate.getValue(RedStoneWireBlock.POWER) : blockstate.getSignal(pLevel,pPos,pSide);
            }
        } else {
            return 0;
        }
    }
    protected boolean isSideInput(BlockState pState) { return pState.isSignalSource();}
    protected int getOutputSignal(BlockGetter pLevel, BlockState pState, BlockPos pPos) { return 15; }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos)
    {
        if (!pLevel.isClientSide())
        {
            if (pFacing == pState.getValue(FACING).getCounterClockWise())
            {
                return pState.setValue(INPUT_1, this.isInputOne(pState, pLevel, pFacingPos));
            } else if (pFacing == pState.getValue(FACING).getClockWise())
            {
                return pState.setValue(INPUT_2, this.isInputTwo(pState,pLevel,pFacingPos));
            }
            else{
                return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
            }
        }
        else{
            return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
        }
    }
    @Override
    public List<ItemStack> getDrops(BlockState pState, LootContext.Builder pBuilder) {

        List<ItemStack> drops = super.getDrops(pState, pBuilder);
        if (!drops.isEmpty())
            return drops;
        return singletonList(new ItemStack(this, 1));
    }

    @Override
    public boolean canConnectRedstone(BlockState pState, BlockGetter pLevel, BlockPos pPos, Direction pSide) {
        return pState.getValue(FACING) != pSide;
    }
}