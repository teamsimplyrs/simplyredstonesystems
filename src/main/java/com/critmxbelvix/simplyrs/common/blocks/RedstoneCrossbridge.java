package com.critmxbelvix.simplyrs.common.blocks;

import com.critmxbelvix.simplyrs.common.blocks.srsvoxelshapes.SRSVoxelShapes;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.TickPriority;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;

public class RedstoneCrossbridge extends Block {
    private static final Logger LOGGER = LogManager.getLogger();
    final static String name = "redstone_crossbridge";
    final static CreativeModeTab tab = SimplyRSCreativeTab.SRS_TAB;
    final static Properties crossbridge_properties = Properties.of(Material.STONE).strength(0.3f).dynamicShape();
    public static final BooleanProperty X_INPUT = BooleanProperty.create("x_input");
    public static final BooleanProperty Z_INPUT = BooleanProperty.create("z_input");
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final EnumProperty<Direction> X_AXIS = EnumProperty.create("x_axis",Direction.class, Direction.EAST,Direction.WEST);
    public static final EnumProperty<Direction> Z_AXIS = EnumProperty.create("z_axis",Direction.class, Direction.NORTH,Direction.SOUTH);
    public static final IntegerProperty X_STRENGTH = IntegerProperty.create("x_power", 0, 15);
    public static final IntegerProperty Z_STRENGTH = IntegerProperty.create("z_power", 0, 15);
    public RedstoneCrossbridge(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(
                this.getStateDefinition().any()
                        .setValue(X_INPUT,false)
                        .setValue(Z_INPUT, false)
        );
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
        return crossbridge_properties;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SRSVoxelShapes.CROSSBRIDGE_SHAPE;
    }
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return canSupportRigidBlock(pLevel, pPos.below());
    }

    // Blockstates

    public BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
        Level pLevel = pContext.getLevel();
        BlockPos pPos = pContext.getClickedPos();
        int X = setInputDirectionX(pLevel,pPos);
        int Z = setInputDirectionZ(pLevel,pPos);
        int signalStrengthNorth = getInputSignalAt(pLevel, pPos.relative(Direction.NORTH),Direction.NORTH);
        int signalStrengthSouth = getInputSignalAt(pLevel, pPos.relative(Direction.SOUTH),Direction.SOUTH);
        int signalStrengthEast = getInputSignalAt(pLevel, pPos.relative(Direction.EAST),Direction.EAST);
        int signalStrengthWest = getInputSignalAt(pLevel, pPos.relative(Direction.WEST),Direction.WEST);

        if(X==2 && Z==2){
            return this.defaultBlockState()
                    .setValue(X_INPUT,false).setValue(Z_INPUT,false).setValue(POWERED,false).setValue(X_STRENGTH,0).setValue(Z_STRENGTH,0);
        }
        else if(X==0 && Z==2){
            return this.defaultBlockState()
                    .setValue(X_INPUT,true).setValue(X_AXIS,Direction.EAST).setValue(Z_INPUT,false).setValue(POWERED,true).setValue(X_STRENGTH,signalStrengthEast).setValue(Z_STRENGTH,0);
        }
        else if(X==1 && Z==2){
            return this.defaultBlockState()
                    .setValue(X_INPUT,true).setValue(X_AXIS,Direction.WEST).setValue(Z_INPUT,false).setValue(POWERED,true).setValue(X_STRENGTH,signalStrengthWest).setValue(Z_STRENGTH,0);
        }
        else if(X==2 && Z==0){
            return this.defaultBlockState()
                    .setValue(Z_INPUT,true).setValue(Z_AXIS,Direction.NORTH).setValue(X_INPUT,false).setValue(POWERED,true).setValue(X_STRENGTH,0).setValue(Z_STRENGTH,signalStrengthNorth);
        }
        else if(X==2 && Z==1){
            return this.defaultBlockState()
                    .setValue(Z_INPUT,true).setValue(Z_AXIS,Direction.SOUTH).setValue(X_INPUT,false).setValue(POWERED,true).setValue(X_STRENGTH,0).setValue(Z_STRENGTH,signalStrengthSouth);
        }
        else if(X==0 && Z==0){
            return this.defaultBlockState()
                    .setValue(X_INPUT,true).setValue(Z_INPUT,true).setValue(X_AXIS,Direction.EAST).setValue(Z_AXIS,Direction.NORTH).setValue(POWERED,true).setValue(X_STRENGTH,signalStrengthEast).setValue(Z_STRENGTH,signalStrengthNorth);
        }
        else if(X==0 && Z==1){
            return this.defaultBlockState()
                    .setValue(X_INPUT,true).setValue(Z_INPUT,true).setValue(X_AXIS,Direction.EAST).setValue(Z_AXIS,Direction.SOUTH).setValue(POWERED,true).setValue(X_STRENGTH,signalStrengthEast).setValue(Z_STRENGTH,signalStrengthSouth);
        }
        else if(X==1 && Z==0){
            return this.defaultBlockState()
                    .setValue(X_INPUT,true).setValue(Z_INPUT,true).setValue(X_AXIS,Direction.WEST).setValue(Z_AXIS,Direction.NORTH).setValue(POWERED,true).setValue(X_STRENGTH,signalStrengthWest).setValue(Z_STRENGTH,signalStrengthNorth);
        }
        else if(X==1 && Z==1){
            return this.defaultBlockState()
                    .setValue(X_INPUT,true).setValue(Z_INPUT,true).setValue(X_AXIS,Direction.WEST).setValue(Z_AXIS,Direction.SOUTH).setValue(POWERED,true).setValue(X_STRENGTH,signalStrengthWest).setValue(Z_STRENGTH,signalStrengthSouth);
        }else{
            return this.defaultBlockState()
                    .setValue(X_INPUT,false).setValue(Z_INPUT,false);
        }
    }


    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder){
        pBuilder.add(X_INPUT,Z_INPUT,X_AXIS,Z_AXIS,X_STRENGTH,Z_STRENGTH,POWERED);
    }

    // Redstone

    private int setInputDirectionX(Level pLevel, BlockPos pPos){
        int signalStrengthEast = getInputSignalAt(pLevel, pPos.relative(Direction.EAST),Direction.EAST);
        int signalStrengthWest = getInputSignalAt(pLevel, pPos.relative(Direction.WEST),Direction.WEST);
        if(signalStrengthEast==0 && signalStrengthWest==0){
            return 2;
        }
        else {
            if (signalStrengthEast >= signalStrengthWest) {
                return 0;
            } else if (signalStrengthEast < signalStrengthWest) {
                return 1;
            }
        }
        return 2;
    }

    private int setInputDirectionZ(Level pLevel, BlockPos pPos){
        int signalStrengthNorth = getInputSignalAt(pLevel, pPos.relative(Direction.NORTH),Direction.NORTH);
        int signalStrengthSouth = getInputSignalAt(pLevel, pPos.relative(Direction.SOUTH),Direction.SOUTH);
        if(signalStrengthNorth==0 && signalStrengthSouth==0){
            return 2;
        }
        else {
            if (signalStrengthNorth >= signalStrengthSouth) {
                return 0;
            } else if (signalStrengthSouth > signalStrengthNorth) {
                return 1;
            }
        }
        return 2;
    }

    protected int getInputSignalAt(BlockGetter pLevel, BlockPos pPos, Direction pSide) {
        BlockState blockstate = pLevel.getBlockState(pPos);

        if (this.isSideInput(blockstate)) {
            if (blockstate.is(Blocks.REDSTONE_BLOCK)) {
                return 15;
            } else {
                return blockstate.is(Blocks.REDSTONE_WIRE) ? blockstate.getValue(RedStoneWireBlock.POWER) : blockstate.getSignal(pLevel, pPos, pSide);
            }
        } else {
            return 0;
        }
    }

    protected int getOutputSignal(BlockGetter pLevel, BlockPos pPos, BlockState pState, Direction pSide) {
        //return 15;
        return getInputSignalAt(pLevel,pPos,pSide.getOpposite()) - 1;
    }


    protected boolean isSideInput(BlockState pState) {
        return pState.isSignalSource();
    }
    public boolean isSignalSource(BlockState pState) {
        return true;
    }



    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pIsMoving && !pState.is(pNewState.getBlock())) {
            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        }
    }

    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRand) {
        boolean flag = pState.getValue(POWERED);
        boolean flag1 = this.shouldTurnOn(pLevel, pPos, pState);
        if (flag && !flag1) {
            pLevel.setBlock(pPos, pState.setValue(POWERED, Boolean.valueOf(false)), 2);
        } else if(flag1){
            pLevel.setBlock(pPos, pState.setValue(POWERED, Boolean.valueOf(true)), 2);

        }
    }

    protected boolean shouldTurnOn(Level pLevel, BlockPos pPos, BlockState pState)
    {
        return pState.getValue(X_INPUT) || pState.getValue(Z_INPUT);

    }
    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        if(!pState.canSurvive(pLevel,pPos)){
            BlockEntity blockentity = pState.hasBlockEntity() ? pLevel.getBlockEntity(pPos) : null;
            dropResources(pState, pLevel, pPos, blockentity);
            pLevel.removeBlock(pPos, false);
        }
        else {
            BlockState blockstate;
            int X = setInputDirectionX(pLevel,pPos);
            int Z = setInputDirectionZ(pLevel,pPos);
            int signalStrengthNorth = getInputSignalAt(pLevel, pPos.relative(Direction.NORTH),Direction.NORTH);
            int signalStrengthSouth = getInputSignalAt(pLevel, pPos.relative(Direction.SOUTH),Direction.SOUTH);
            int signalStrengthEast = getInputSignalAt(pLevel, pPos.relative(Direction.EAST),Direction.EAST);
            int signalStrengthWest = getInputSignalAt(pLevel, pPos.relative(Direction.WEST),Direction.WEST);

            if(X==2 && Z==2){
                blockstate = pLevel.getBlockState(pPos)
                        .setValue(X_INPUT,false).setValue(Z_INPUT,false).setValue(POWERED,false).setValue(X_STRENGTH,0).setValue(Z_STRENGTH,0);
            }
            else if(X==0 && Z==2){
                blockstate = pLevel.getBlockState(pPos)
                        .setValue(X_INPUT,true).setValue(X_AXIS,Direction.EAST).setValue(Z_INPUT,false).setValue(POWERED,true).setValue(X_STRENGTH,signalStrengthEast).setValue(Z_STRENGTH,0);
            }
            else if(X==1 && Z==2){
                blockstate = pLevel.getBlockState(pPos)
                        .setValue(X_INPUT,true).setValue(X_AXIS,Direction.WEST).setValue(Z_INPUT,false).setValue(POWERED,true).setValue(X_STRENGTH,signalStrengthWest).setValue(Z_STRENGTH,0);
            }
            else if(X==2 && Z==0){
                blockstate = pLevel.getBlockState(pPos)
                        .setValue(Z_INPUT,true).setValue(Z_AXIS,Direction.NORTH).setValue(X_INPUT,false).setValue(POWERED,true).setValue(X_STRENGTH,0).setValue(Z_STRENGTH,signalStrengthNorth);
            }
            else if(X==2 && Z==1){
                blockstate = pLevel.getBlockState(pPos)
                        .setValue(Z_INPUT,true).setValue(Z_AXIS,Direction.SOUTH).setValue(X_INPUT,false).setValue(POWERED,true).setValue(X_STRENGTH,0).setValue(Z_STRENGTH,signalStrengthSouth);
            }
            else if(X==0 && Z==0){
                blockstate = pLevel.getBlockState(pPos)
                        .setValue(X_INPUT,true).setValue(Z_INPUT,true).setValue(X_AXIS,Direction.EAST).setValue(Z_AXIS,Direction.NORTH).setValue(POWERED,true).setValue(X_STRENGTH,signalStrengthEast).setValue(Z_STRENGTH,signalStrengthNorth);
            }
            else if(X==0 && Z==1){
                blockstate = pLevel.getBlockState(pPos)
                        .setValue(X_INPUT,true).setValue(Z_INPUT,true).setValue(X_AXIS,Direction.EAST).setValue(Z_AXIS,Direction.SOUTH).setValue(POWERED,true).setValue(X_STRENGTH,signalStrengthEast).setValue(Z_STRENGTH,signalStrengthSouth);
            }
            else if(X==1 && Z==0){
                blockstate = pLevel.getBlockState(pPos)
                        .setValue(X_INPUT,true).setValue(Z_INPUT,true).setValue(X_AXIS,Direction.WEST).setValue(Z_AXIS,Direction.NORTH).setValue(POWERED,true).setValue(X_STRENGTH,signalStrengthWest).setValue(Z_STRENGTH,signalStrengthNorth);
            }
            else if(X==1 && Z==1){
                blockstate = pLevel.getBlockState(pPos)
                        .setValue(X_INPUT,true).setValue(Z_INPUT,true).setValue(X_AXIS,Direction.WEST).setValue(Z_AXIS,Direction.SOUTH).setValue(POWERED,true).setValue(X_STRENGTH,signalStrengthWest).setValue(Z_STRENGTH,signalStrengthSouth);
            }else{
                blockstate = pLevel.getBlockState(pPos)
                        .setValue(X_INPUT,false).setValue(Z_INPUT,false);
            }
            pLevel.setBlockAndUpdate(pPos, blockstate);
            pLevel.scheduleTick(pPos, this, 1, TickPriority.VERY_HIGH);
        }
    }

    @Override
    public int getDirectSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
        return pBlockState.getSignal(pBlockAccess, pPos, pSide);
    }

    @Override
    public int getSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
        boolean x_input = pBlockState.getValue(X_INPUT);
        boolean z_input = pBlockState.getValue(Z_INPUT);
        Direction x_dir = pBlockState.getValue(X_AXIS);
        Direction z_dir = pBlockState.getValue(Z_AXIS);
        if(!x_input && !z_input){
            return 0;
        }
        else{
            if(x_dir==pSide && pBlockState.getValue(X_STRENGTH)!=0){
                return pBlockState.getValue(X_STRENGTH)-1;
            }
            else if(z_dir==pSide && pBlockState.getValue(Z_STRENGTH)!=0){
                return pBlockState.getValue(Z_STRENGTH)-1;
            }
        }
        return 0;
    }

    // Block Drops

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
