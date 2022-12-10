package com.critmxbelvix.simplyrs.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class GateBlock extends Block{
    private static final VoxelShape SHAPE = Block.box(1, 0, 1, 15, 1, 15);

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public GateBlock(Properties m_properties) {
        super(m_properties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    /* FACING */

    public BlockState getStateForPlacement(BlockPlaceContext pContext){
        return this.defaultBlockState().setValue(FACING,pContext.getHorizontalDirection());
    }

    public BlockState rotate(BlockState pState, Rotation pRotation){
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    public BlockState mirror(BlockState pState, Mirror pMirror){
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    /* Blockstates */

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder){
        pBuilder.add(FACING);
    }

}
