package com.critmxbelvix.simplyrs.common.blocks;

import com.critmxbelvix.simplyrs.common.creativetabs.SimplyRSCreativeTab;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LogicGateNAND extends Block {

    final static String name = "logicgate_nand";
    final static CreativeModeTab tab = SimplyRSCreativeTab.SRS_TAB;
    public static final BlockBehaviour.Properties gate_nand_properties = BlockBehaviour.Properties.of(Material.STONE).strength(0.1f).dynamicShape();
    private static final VoxelShape SHAPE = Block.box(1, 0, 1, 15, 1, 15);

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public LogicGateNAND(BlockBehaviour.Properties m_properties) {
        super(m_properties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    public static String m_getName()
    {
        return name;
    }
    public static CreativeModeTab m_getTab()
    {
        return tab;
    }
    public BlockBehaviour.Properties m_getProperties()
    {
        return gate_nand_properties;
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

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder){
        pBuilder.add(FACING);
    }

}
