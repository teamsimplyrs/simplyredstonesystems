package com.critmxbelvix.simplyrs.common.blocks;

import com.critmxbelvix.simplyrs.common.blocks.entities.SpriteEntity;
import com.critmxbelvix.simplyrs.common.blocks.srsvoxelshapes.SRSVoxelShapes;
import com.critmxbelvix.simplyrs.common.registers.BlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.TickPriority;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class RedstoneSprite extends BaseEntityBlock {

    static final String name = "redstone_sprite";
    static final Properties sprite_properties = Properties.of(Material.STONE).strength(0).dynamicShape().noCollission();
    static final CreativeModeTab tab = null;

    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public RedstoneSprite(Properties pProperties) {
        super(pProperties);
    }

    public static String mGetName() { return name; }
    public static Properties mGetProperties() { return sprite_properties; }
    public static CreativeModeTab mGetTab() { return tab; }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SRSVoxelShapes.SPRITE_SHAPE;
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    // Redstone Control

    public boolean isSignalSource(BlockState pState) {
        return true;
    }

    protected int getOutputSignal(BlockGetter pLevel, BlockPos pPos, BlockState pState) {
        return 15;
    }

    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRand) {
        pLevel.neighborChanged(pPos,this,pPos);
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        if(!pState.canSurvive(pLevel,pPos)){
            BlockEntity blockentity = pState.hasBlockEntity() ? pLevel.getBlockEntity(pPos) : null;
            dropResources(pState, pLevel, pPos, blockentity);
            pLevel.removeBlock(pPos, false);
        }
        else {
            BlockState blockstate = pLevel.getBlockState(pPos);
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
        return this.getOutputSignal(pBlockAccess,pPos,pBlockState);
    }
    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction) {
        return true;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SpriteEntity(pPos,pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, BlockEntityRegister.REDSTONE_SPRITE_ENTITY.get(),
                SpriteEntity::tick);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }
}
