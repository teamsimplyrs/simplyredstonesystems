package com.critmxbelvix.simplyrs.common.blocks;

import com.critmxbelvix.simplyrs.common.blocks.entities.JKFlipFlop.JKFlipFlopEntity;
import com.critmxbelvix.simplyrs.common.blocks.srsvoxelshapes.JKFlipFlopVoxelShapes;
import com.critmxbelvix.simplyrs.common.blocks.srsvoxelshapes.SRSVoxelShapes;
import com.critmxbelvix.simplyrs.common.creativetabs.SimplyRSCreativeTab;
import com.critmxbelvix.simplyrs.common.registers.BlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
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
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static java.util.Collections.singletonList;

public class JKFlipFlop extends BaseEntityBlock {

    static final String name = "jk_flipflop";
    static final CreativeModeTab tab = SimplyRSCreativeTab.SRS_TAB;
    static final Properties flipflop_properties = Properties.of(Material.STONE).strength(0.3f).dynamicShape();
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty INPUT_1 = BooleanProperty.create("input_1");
    public static final BooleanProperty INPUT_2 = BooleanProperty.create("input_2");
    public static final BooleanProperty INPUT_CLK = BooleanProperty.create("input_clk");
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    private static final Logger LOGGER = LogManager.getLogger();

    public JKFlipFlop(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(
                this.getStateDefinition().any()
                        .setValue(INPUT_1,false)
                        .setValue(INPUT_2, false)
                        .setValue(INPUT_CLK, false)
                        .setValue(POWERED,false)
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
    public static BlockBehaviour.Properties mGetProperties()
    {
        return flipflop_properties;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch (pState.getValue(FACING))
        {
            default -> JKFlipFlopVoxelShapes.NORTH_SHAPE;
            case EAST -> JKFlipFlopVoxelShapes.EAST_SHAPE;
            case WEST -> JKFlipFlopVoxelShapes.WEST_SHAPE;
            case SOUTH -> JKFlipFlopVoxelShapes.SOUTH_SHAPE;
        };
    }
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return canSupportRigidBlock(pLevel, pPos.below());
    }

    // Blockstates:

    public BlockState rotate(BlockState pState, Rotation pRotation){
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    public BlockState mirror(BlockState pState, Mirror pMirror){
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder){
        pBuilder.add(FACING,INPUT_1,INPUT_2,INPUT_CLK,POWERED);
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
        BlockState blockstate = super.getStateForPlacement(pContext);
        Direction direction = pContext.getHorizontalDirection();
        pContext.getLevel().scheduleTick(pContext.getClickedPos(),blockstate.getBlock(),1);
        return this.defaultBlockState()
                .setValue(FACING,direction)
                .setValue(INPUT_1,isInputOne(pContext.getLevel(),pContext.getClickedPos().relative(direction.getCounterClockWise()),blockstate))
                .setValue(INPUT_2,isInputTwo(pContext.getLevel(),pContext.getClickedPos().relative(direction.getClockWise()),blockstate))
                .setValue(INPUT_CLK,isInputCLK(pContext.getLevel(),pContext.getClickedPos().relative(direction.getOpposite()),blockstate))
                .setValue(POWERED,false);
    }

    // Redstone Control:

    public boolean isSignalSource(BlockState pState) {
        return true;
    }

    public boolean isInputOne(LevelReader pLevel, BlockPos pPos, BlockState pState){
        Direction direction = pState.getValue(FACING).getCounterClockWise();
        return getInputSignalAt(pLevel, pPos, direction) > 0;
    }

    public boolean isInputTwo(LevelReader pLevel, BlockPos pPos, BlockState pState){
        Direction direction = pState.getValue(FACING).getClockWise();
        return getInputSignalAt(pLevel, pPos, direction) > 0;
    }

    public boolean isInputCLK(LevelReader pLevel, BlockPos pPos, BlockState pState){
        Direction direction = pState.getValue(FACING).getClockWise();
        return getInputSignalAt(pLevel, pPos, direction) > 0;
    }

    protected int getInputSignalAt(LevelReader pLevel, BlockPos pPos, Direction pSide) {
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

    protected boolean isSideInput(BlockState pState) {
        return pState.isSignalSource();
    }
    protected int getOutputSignal(BlockGetter pLevel, BlockPos pPos, BlockState pState) {
        return 15;
    }

    protected boolean shouldTurnOn(Level pLevel, BlockPos pPos, BlockState pState) {
        return true;
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
            Direction direction1 = direction.getCounterClockWise();
            Direction direction2 = direction.getClockWise();
            Direction direction3 = direction.getOpposite();

            BlockState blockstate = pLevel.getBlockState(pPos)
                    .setValue(INPUT_1, pLevel.getSignal(pPos.relative(direction1), direction1) > 0)
                    .setValue(INPUT_2, pLevel.getSignal(pPos.relative(direction2), direction2) > 0)
                    .setValue(INPUT_CLK, pLevel.getSignal(pPos.relative(direction3), direction3) > 0);
            pLevel.setBlockAndUpdate(pPos, blockstate);
            pLevel.scheduleTick(pPos, this, 1, TickPriority.VERY_HIGH);
        }
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pIsMoving && !pState.is(pNewState.getBlock())) {
            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
            pLevel.neighborChanged(pPos.relative(pState.getValue(FACING)),this, pPos);
        }
    }

    // Block Drops:

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

    @Override
    public int getSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
        if (!pBlockState.getValue(POWERED)) {
            return 0;
        } else {
            return pBlockState.getValue(FACING).getOpposite() == pSide ? this.getOutputSignal(pBlockAccess, pPos, pBlockState) : 0;
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new JKFlipFlopEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, BlockEntityRegister.JK_FLIPFLOP_ENTITY.get(),
                JKFlipFlopEntity::tick);
    }



}
