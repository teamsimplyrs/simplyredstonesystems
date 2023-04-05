package com.critmxbelvix.simplyrs.common.blocks;

import com.critmxbelvix.simplyrs.common.blocks.srsvoxelshapes.CableVoxelShapes;
import com.critmxbelvix.simplyrs.common.creativetabs.SimplyRSCreativeTab;
import com.critmxbelvix.simplyrs.common.registers.BlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.TickPriority;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static java.util.Collections.singletonList;

public class RedstoneCable extends Block {
    private static final Logger LOGGER = LogManager.getLogger();
    final static String name = "cable";
    final static CreativeModeTab tab = SimplyRSCreativeTab.SRS_TAB;
    final static Properties cable_properties = Properties.of(Material.METAL).strength(0.6f).dynamicShape();

    public static final BooleanProperty SIDE_NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SIDE_EAST = BooleanProperty.create("east");
    public static final BooleanProperty SIDE_WEST = BooleanProperty.create("west");
    public static final BooleanProperty SIDE_SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty SIDE_UP = BooleanProperty.create("up");
    public static final BooleanProperty SIDE_DOWN = BooleanProperty.create("down");
    public static final IntegerProperty SIGNAL = IntegerProperty.create("signal",0,15);
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

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
        return cable_properties;
    }

    public RedstoneCable(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(
                this.getStateDefinition().any()
                        .setValue(SIDE_NORTH,false)
                        .setValue(SIDE_EAST, false)
                        .setValue(SIDE_WEST, false)
                        .setValue(SIDE_SOUTH,false)
                        .setValue(SIDE_UP, false)
                        .setValue(SIDE_DOWN, false)
                        .setValue(POWERED,false)
                        .setValue(SIGNAL,0)
        );
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        boolean N = pState.getValue(SIDE_NORTH);
        boolean E = pState.getValue(SIDE_EAST);
        boolean W = pState.getValue(SIDE_WEST);
        boolean S = pState.getValue(SIDE_SOUTH);
        boolean U = pState.getValue(SIDE_UP);
        boolean D = pState.getValue(SIDE_DOWN);
        return CableVoxelShapes.getShape(pState,N,E,W,S,U,D);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder){
        pBuilder.add(SIDE_NORTH,SIDE_EAST,SIDE_WEST,SIDE_SOUTH,SIDE_UP,SIDE_DOWN,POWERED,SIGNAL);
//        pBuilder.add(SIDE_NORTH,SIDE_EAST,SIDE_WEST,SIDE_SOUTH,SIDE_UP,SIDE_DOWN,I_N,I_E,I_W,I_S,I_U,I_D,POWERED);
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
        BlockState blockstate = super.getStateForPlacement(pContext);
        pContext.getLevel().scheduleTick(pContext.getClickedPos(),blockstate.getBlock(),1);
        return this.defaultBlockState()
                .setValue(POWERED,shouldTurnOn(pContext.getLevel(),pContext.getClickedPos(),blockstate))
                .setValue(SIGNAL,getSignal(blockstate,pContext.getLevel(),pContext.getClickedPos(),Direction.NORTH)); // Direction.NORTH is a dummy data since final function call does not use pSide value
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        //LOGGER.info(pNeighborState+" "+pDirection);
        BooleanProperty direction = switch(pDirection){
            case UP -> SIDE_UP;
            case DOWN -> SIDE_DOWN;
            case EAST -> SIDE_EAST;
            case WEST -> SIDE_WEST;
            case NORTH -> SIDE_NORTH;
            case SOUTH -> SIDE_SOUTH;
        };
        if(pNeighborState.is(BlockRegister.REDSTONE_CABLE.get()) || pNeighborState.canRedstoneConnectTo(pLevel,pNeighborPos,pDirection.getOpposite())){
            return pState.setValue(direction,true);
        }
        else{
            return pState.setValue(direction,false);
        }
    }

    protected int getInputSignalAt(BlockGetter pLevel, BlockPos pPos, Direction pSide) {

        BlockState blockstate = pLevel.getBlockState(pPos);
        if (blockstate.is(Blocks.REDSTONE_BLOCK)) {
            return 15;
        } else {
            return blockstate.is(Blocks.REDSTONE_WIRE) ? blockstate.getValue(RedStoneWireBlock.POWER) : blockstate.getSignal(pLevel, pPos, pSide);
        }
    }

    protected int getOutputSignal(Level pLevel, BlockPos pPos) {
        //return 15;
        return Math.max(
                    Math.max(
                        Math.max(getInputSignalAt(pLevel,pPos.relative(Direction.NORTH),Direction.NORTH), getInputSignalAt(pLevel,pPos.relative(Direction.EAST),Direction.EAST)),
                        Math.max(getInputSignalAt(pLevel,pPos.relative(Direction.WEST),Direction.WEST), getInputSignalAt(pLevel,pPos.relative(Direction.SOUTH),Direction.SOUTH))
                    ),
                    Math.max(getInputSignalAt(pLevel,pPos.relative(Direction.UP),Direction.UP),getInputSignalAt(pLevel,pPos.relative(Direction.DOWN),Direction.DOWN))
            );
    }

    public boolean isInputN(LevelReader pLevel, BlockPos pPos, BlockState pState){
        return getInputSignalAt(pLevel, pPos.relative(Direction.NORTH), Direction.NORTH) > 0;
    }
    public boolean isInputE(LevelReader pLevel, BlockPos pPos, BlockState pState){
        return getInputSignalAt(pLevel, pPos.relative(Direction.EAST), Direction.EAST) > 0;
    }
    public boolean isInputW(LevelReader pLevel, BlockPos pPos, BlockState pState){
        return getInputSignalAt(pLevel, pPos.relative(Direction.WEST), Direction.WEST) > 0;
    }
    public boolean isInputS(LevelReader pLevel, BlockPos pPos, BlockState pState){
        return getInputSignalAt(pLevel, pPos.relative(Direction.SOUTH), Direction.SOUTH) > 0;
    }
    public boolean isInputU(LevelReader pLevel, BlockPos pPos, BlockState pState){
        return getInputSignalAt(pLevel, pPos.relative(Direction.UP), Direction.UP) > 0;
    }
    public boolean isInputD(LevelReader pLevel, BlockPos pPos, BlockState pState){
        return getInputSignalAt(pLevel, pPos.relative(Direction.DOWN), Direction.DOWN) > 0;
    }

    public boolean isSignalSource(BlockState pState) {
        return true;
    }

    protected boolean shouldTurnOn(Level pLevel, BlockPos pPos, BlockState pState) {
        return isInputN(pLevel,pPos,pState) || isInputE(pLevel,pPos,pState)
                || isInputW(pLevel,pPos,pState) || isInputS(pLevel,pPos,pState)
                || isInputU(pLevel,pPos,pState) || isInputD(pLevel,pPos,pState);
    }

    @Override
    public int getSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
        return pBlockState.getValue(SIGNAL);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pIsMoving && !pState.is(pNewState.getBlock())) {
            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
            pLevel.neighborChanged(pPos.relative(Direction.NORTH),this, pPos);
            pLevel.neighborChanged(pPos.relative(Direction.EAST),this, pPos);
            pLevel.neighborChanged(pPos.relative(Direction.WEST),this, pPos);
            pLevel.neighborChanged(pPos.relative(Direction.SOUTH),this, pPos);
            pLevel.neighborChanged(pPos.relative(Direction.UP),this, pPos);
            pLevel.neighborChanged(pPos.relative(Direction.DOWN),this, pPos);
        }
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        if(!pState.canSurvive(pLevel,pPos)){
            BlockEntity blockentity = pState.hasBlockEntity() ? pLevel.getBlockEntity(pPos) : null;
            dropResources(pState, pLevel, pPos, blockentity);
            pLevel.removeBlock(pPos, false);
        }
        else {
            LOGGER.info(pLevel.getBlockState(pPos));
            BlockState blockstate = pLevel.getBlockState(pPos)
                    .setValue(POWERED, shouldTurnOn(pLevel,pPos,pState))
                    .setValue(SIGNAL, getOutputSignal(pLevel,pPos)); // Direction.NORTH is a dummy entry, the final function call does not use pSide value
            pLevel.setBlockAndUpdate(pPos, blockstate);
            pLevel.scheduleTick(pPos, this, 1, TickPriority.VERY_HIGH);
        }
    }

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




