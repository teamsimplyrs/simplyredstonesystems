package com.critmxbelvix.simplyrs.common.blocks;

import com.critmxbelvix.simplyrs.common.blocks.srsvoxelshapes.SRSVoxelShapes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
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

public abstract class Gate2Block extends Block
{
    /* Unlike vanilla minecraft's FACING property, this mod's facing property is set in the direction which the player
     is looking.
    */
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public static final BooleanProperty INPUT_1 = BooleanProperty.create("input_1");
    public static final BooleanProperty INPUT_2 = BooleanProperty.create("input_2");
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

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
        return SRSVoxelShapes.GATE_SHAPE;
    }

    //Used to prevent placing the gate blocks on each other
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos)
    {
        return canSupportRigidBlock(pLevel,pPos.below());
    }

    // Placement Blockstates

    public BlockState rotate(BlockState pState, Rotation pRotation){
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    public BlockState mirror(BlockState pState, Mirror pMirror){
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    //Remember to add blockstates here when you create new ones
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder){
        pBuilder.add(FACING,INPUT_1,INPUT_2,POWERED);
    }

    // Redstone Control

    public boolean isSignalSource(BlockState pState) {
        return true;
    }

    // Returns true for each input side relative to the FACING blockstate

    public boolean isInputOne(LevelReader pLevel, BlockPos pPos, BlockState pState)
    {
        Direction faceWest= pState.getValue(FACING).getCounterClockWise();

        LOGGER.info(getInputSignalAt(pLevel,pPos,faceWest));
        return getInputSignalAt(pLevel,pPos,faceWest) > 0;
    }

    public boolean isInputTwo(LevelReader pLevel, BlockPos pPos, BlockState pState)
    {
        Direction faceEast = pState.getValue(FACING).getClockWise();

        LOGGER.info(getInputSignalAt(pLevel,pPos,faceEast));
        return getInputSignalAt(pLevel,pPos,faceEast) > 0;
    }

    //Returns the redstone strength of the gate's neighboring block in the direction specified by pSide
    protected int getInputSignalAt(LevelReader pLevel, BlockPos pPos, Direction pSide) {
        BlockState blockstate = pLevel.getBlockState(pPos);

        if (this.isSideInput(blockstate)) {
            if (blockstate.is(Blocks.REDSTONE_BLOCK)) {
                return 15;
            } else {
                return blockstate.is(Blocks.REDSTONE_WIRE) ? blockstate.getValue(RedStoneWireBlock.POWER) : blockstate.getSignal(pLevel,pPos,pSide);
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

    //Function called whenever the scheduleTick function is used. Used to handle the POWERED blockstate's values. Updates
    //the block in the gate's output direction.
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRand) {
        boolean flag = pState.getValue(POWERED);
        boolean flag1 = this.shouldTurnOn(pLevel, pPos, pState);
        LOGGER.info("two gate "+flag1);
        if (flag && !flag1) {
            pLevel.setBlock(pPos, pState.setValue(POWERED, Boolean.valueOf(false)), 2);
        } else if(flag1){
            pLevel.setBlock(pPos, pState.setValue(POWERED, Boolean.valueOf(true)), 2);
        }
        pLevel.neighborChanged(pPos.relative(pState.getValue(FACING)),this,pPos);
    }

    //Abstract function implemented in the respective gates
    protected abstract boolean shouldTurnOn(Level pLevel, BlockPos pPos, BlockState pState);

    /* neighborChanged is called by a block's neighboring blocks whenever the neighboring block undergoes a blockstate
    change. pPos is the position of the gate block and pFromPos is the position of the block that calls this function.

    Used to update the blockstates of the gate block when nearby blocks update.
     */
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
            Direction direction2 = direction.getClockWise();

            BlockState blockstate = pLevel.getBlockState(pPos)
                    .setValue(INPUT_1, pLevel.getSignal(pPos.relative(direction1), direction1) > 0)
                    .setValue(INPUT_2, pLevel.getSignal(pPos.relative(direction2), direction2) > 0);
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
    public boolean canConnectRedstone(BlockState pState, BlockGetter pLevel, BlockPos pPos, Direction pSide) {
        return pState.getValue(FACING) != pSide;
    }

    @Override
    public List<ItemStack> getDrops(BlockState pState, LootContext.Builder pBuilder) {

        List<ItemStack> drops = super.getDrops(pState, pBuilder);
        if (!drops.isEmpty())
            return drops;
        return singletonList(new ItemStack(this, 1));
    }


}