package com.critmxbelvix.simplyrs.common.blocks;

import com.critmxbelvix.simplyrs.common.blocks.srsvoxelshapes.CableVoxelShapes;
import com.critmxbelvix.simplyrs.common.creativetabs.SimplyRSCreativeTab;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

//    public static final IntegerProperty I_N = IntegerProperty.create("input_north",0,15);
//    public static final IntegerProperty I_E = IntegerProperty.create("input_east",0,15);
//    public static final IntegerProperty I_W = IntegerProperty.create("input_west",0,15);
//    public static final IntegerProperty I_S = IntegerProperty.create("input_south",0,15);
//    public static final IntegerProperty I_U = IntegerProperty.create("input_up",0,15);
//    public static final IntegerProperty I_D = IntegerProperty.create("input_down",0,15);

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
        );
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return CableVoxelShapes.getShape(pState);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder){
        pBuilder.add(SIDE_NORTH,SIDE_EAST,SIDE_WEST,SIDE_SOUTH,SIDE_UP,SIDE_DOWN,POWERED);
//        pBuilder.add(SIDE_NORTH,SIDE_EAST,SIDE_WEST,SIDE_SOUTH,SIDE_UP,SIDE_DOWN,I_N,I_E,I_W,I_S,I_U,I_D,POWERED);
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
        BlockState blockstate = super.getStateForPlacement(pContext);
        Direction direction = pContext.getHorizontalDirection();
        pContext.getLevel().scheduleTick(pContext.getClickedPos(),blockstate.getBlock(),1);
        return this.defaultBlockState();
    }

}
