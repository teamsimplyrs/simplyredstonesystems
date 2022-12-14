package com.critmxbelvix.simplyrs.common.blocks;

import com.critmxbelvix.simplyrs.common.creativetabs.SimplyRSCreativeTab;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.CreativeModeTab;
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
import net.minecraft.world.level.material.Material;
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

public class LogicGateNOT extends Block
{
    private static final VoxelShape SHAPE = Block.box(1,0,1,15,1,15);

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public static final BooleanProperty INPUT = BooleanProperty.create("input");
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    private static final Logger LOGGER = LogManager.getLogger();

    final static String name = "logicgate_not";
    final static CreativeModeTab tab = SimplyRSCreativeTab.SRS_TAB;
    private final static Properties gate_not_properties = Properties.of(Material.STONE).strength(0.1f).dynamicShape();

    public LogicGateNOT(Properties m_Properties) {
        super(m_Properties);
        this.registerDefaultState(
                this.getStateDefinition().any().setValue(INPUT,false)
        );
    }

    public static String m_getName() { return name; }
    public static CreativeModeTab m_getTab() { return tab; }
    public static Properties m_getProperties() { return gate_not_properties; }

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
        Direction south = north.getOpposite();

        return this.defaultBlockState()
                .setValue(FACING,north)
                .setValue(INPUT,isInput(blockstate,pContext.getLevel(),pContext.getClickedPos().relative(south)));
    }
    public BlockState rotate(BlockState pState, Rotation pRotation){
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    public BlockState mirror(BlockState pState, Mirror pMirror){
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder){
        pBuilder.add(FACING,INPUT,POWERED);
    }

    // Redstone Control

    public boolean isSignalSource(BlockState pState) {
        return true;
    }

    public boolean isInput(BlockState pState, LevelReader pLevel, BlockPos pPos)
    {
        Direction faceSouth= pState.getValue(FACING).getOpposite();

        LOGGER.info(getInputSignalAt(pLevel,pPos,faceSouth));
        return getInputSignalAt(pLevel,pPos,faceSouth) > 0;
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
    protected boolean isSideInput(BlockState pState) {
        return pState.isSignalSource();
    }

    protected int getOutputSignal(BlockGetter pLevel, BlockPos pPos, BlockState pState) {
        return 15;
    }

    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRand) {
        boolean flag = pState.getValue(POWERED);
        boolean flag1 = this.shouldTurnOn(pLevel, pPos, pState);
        if (flag && !flag1) {
            pLevel.setBlock(pPos, pState.setValue(POWERED, Boolean.valueOf(false)), 2);
        } else if(flag1){
            pLevel.setBlock(pPos, pState.setValue(POWERED, Boolean.valueOf(true)), 2);
        }
        pLevel.neighborChanged(pPos.relative(pState.getValue(FACING)),this,pPos);
    }


    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        if(!pState.canSurvive(pLevel,pPos)){
            BlockEntity blockentity = pState.hasBlockEntity() ? pLevel.getBlockEntity(pPos) : null;
            dropResources(pState, pLevel, pPos, blockentity);
            pLevel.removeBlock(pPos, false);
        }
        else {
            Direction direction = pLevel.getBlockState(pPos).getValue(FACING);
            Direction direction1 = direction.getOpposite();

            BlockState blockstate = pLevel.getBlockState(pPos)
                    .setValue(INPUT, pLevel.getSignal(pPos.relative(direction1), direction1) > 0);
            pLevel.setBlockAndUpdate(pPos, blockstate);
            pLevel.scheduleTick(pPos, this, 1, TickPriority.VERY_HIGH);
        }
    }

    protected boolean shouldTurnOn(Level pLevel, BlockPos pPos, BlockState pState) {
        boolean input = pState.getValue(INPUT);

        if (!input) {
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

    @Override
    public boolean canConnectRedstone(BlockState pState, BlockGetter pLevel, BlockPos pPos, Direction pSide) {
        return pState.getValue(FACING) == pSide || pState.getValue(FACING).getOpposite() == pSide;
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