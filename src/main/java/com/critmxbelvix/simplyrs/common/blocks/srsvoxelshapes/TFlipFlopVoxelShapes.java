package com.critmxbelvix.simplyrs.common.blocks.srsvoxelshapes;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public class TFlipFlopVoxelShapes {

    // North Shape
    public static final VoxelShape NORTH_SHAPE = Stream.of(
            Block.box(0.25, 0, 0.25, 15.75, 1, 15.75),
            Block.box(7, 0, 0, 9, 2, 0.25),
            Block.box(7, 0, 15.75, 9, 2, 16),
            Block.box(0, 0, 7, 0.25, 2, 9),
            Block.box(7, 1, 7, 9, 3, 9),
            Block.box(3.25, 1, 3.25, 7, 3, 7),
            Block.box(0.5, 1, 0.5, 1.25, 3, 7),
            Block.box(3.25, 1, 7, 7, 3, 9),
            Block.box(0.5, 1, 9, 7, 3, 15.5),
            Block.box(9, 1, 0.5, 15.5, 3, 15.5),
            Block.box(7, 1, 0.25, 9, 2, 1.25),
            Block.box(7, 1, 14.75, 9, 2, 15.75),
            Block.box(0.25, 1, 7, 1.25, 2, 9),
            Block.box(7, 1, 3.25, 9, 2, 7),
            Block.box(7, 1, 9, 9, 2, 12.75),
            Block.box(1.25, 1, 3.25, 3.25, 2, 7),
            Block.box(1.25, 1, 1.25, 7, 2, 3.25),
            Block.box(1, 3, 9, 3.5, 3.5, 9.25),
            Block.box(0.5, 2, 7, 1.25, 3, 9),
            Block.box(7, 2, 14.75, 9, 3, 15.5),
            Block.box(7, 2, 0.5, 9, 3, 1.25),
            Block.box(7, 2, 9, 9, 3, 11),
            Block.box(1.25, 1, 0.5, 7, 3, 1.25),
            Block.box(6.75, 3, 14.75, 9.25, 3.5, 15),
            Block.box(6.75, 3, 3.25, 7, 3.5, 14.75),
            Block.box(9, 3, 1.25, 9.25, 3.5, 14.75),
            Block.box(3.5, 3, 3.25, 6.75, 3.5, 3.5),
            Block.box(1.25, 3, 1, 9.25, 3.5, 1.25),
            Block.box(1, 3, 1, 1.25, 3.5, 9),
            Block.box(3.25, 3, 3.25, 3.5, 3.5, 9)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    // East Shape
    public static final VoxelShape EAST_SHAPE = Stream.of(
            Block.box(0.25, 0, 0.25, 15.75, 1, 15.75),
            Block.box(15.75, 0, 7, 16, 2, 9),
            Block.box(0, 0, 7, 0.25, 2, 9),
            Block.box(7, 0, 0, 9, 2, 0.25),
            Block.box(7, 1, 7, 9, 3, 9),
            Block.box(9, 1, 3.25, 12.75, 3, 7),
            Block.box(9, 1, 0.5, 15.5, 3, 1.25),
            Block.box(7, 1, 3.25, 9, 3, 7),
            Block.box(0.5, 1, 0.5, 7, 3, 7),
            Block.box(0.5, 1, 9, 15.5, 3, 15.5),
            Block.box(14.75, 1, 7, 15.75, 2, 9),
            Block.box(0.25, 1, 7, 1.25, 2, 9),
            Block.box(7, 1, 0.25, 9, 2, 1.25),
            Block.box(9, 1, 7, 12.75, 2, 9),
            Block.box(3.25, 1, 7, 7, 2, 9),
            Block.box(9, 1, 1.25, 12.75, 2, 3.25),
            Block.box(12.75, 1, 1.25, 14.75, 2, 7),
            Block.box(6.75, 3, 1, 7, 3.5, 3.5),
            Block.box(7, 2, 0.5, 9, 3, 1.25),
            Block.box(0.5, 2, 7, 1.25, 3, 9),
            Block.box(14.75, 2, 7, 15.5, 3, 9),
            Block.box(5, 2, 7, 7, 3, 9),
            Block.box(14.75, 1, 1.25, 15.5, 3, 7),
            Block.box(1, 3, 6.75, 1.25, 3.5, 9.25),
            Block.box(1.25, 3, 6.75, 12.75, 3.5, 7),
            Block.box(1.25, 3, 9, 14.75, 3.5, 9.25),
            Block.box(12.5, 3, 3.5, 12.75, 3.5, 6.75),
            Block.box(14.75, 3, 1.25, 15, 3.5, 9.25),
            Block.box(7, 3, 1, 15, 3.5, 1.25),
            Block.box(7, 3, 3.25, 12.75, 3.5, 3.5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    // South Shape
    public static final VoxelShape SOUTH_SHAPE = Stream.of(
            Block.box(0.25, 0, 0.25, 15.75, 1, 15.75),
            Block.box(7, 0, 15.75, 9, 2, 16),
            Block.box(7, 0, 0, 9, 2, 0.25),
            Block.box(15.75, 0, 7, 16, 2, 9),
            Block.box(7, 1, 7, 9, 3, 9),
            Block.box(9, 1, 9, 12.75, 3, 12.75),
            Block.box(14.75, 1, 9, 15.5, 3, 15.5),
            Block.box(9, 1, 7, 12.75, 3, 9),
            Block.box(9, 1, 0.5, 15.5, 3, 7),
            Block.box(0.5, 1, 0.5, 7, 3, 15.5),
            Block.box(7, 1, 14.75, 9, 2, 15.75),
            Block.box(7, 1, 0.25, 9, 2, 1.25),
            Block.box(14.75, 1, 7, 15.75, 2, 9),
            Block.box(7, 1, 9, 9, 2, 12.75),
            Block.box(7, 1, 3.25, 9, 2, 7),
            Block.box(12.75, 1, 9, 14.75, 2, 12.75),
            Block.box(9, 1, 12.75, 14.75, 2, 14.75),
            Block.box(12.5, 3, 6.75, 15, 3.5, 7),
            Block.box(14.75, 2, 7, 15.5, 3, 9),
            Block.box(7, 2, 0.5, 9, 3, 1.25),
            Block.box(7, 2, 14.75, 9, 3, 15.5),
            Block.box(7, 2, 5, 9, 3, 7),
            Block.box(9, 1, 14.75, 14.75, 3, 15.5),
            Block.box(6.75, 3, 1, 9.25, 3.5, 1.25),
            Block.box(9, 3, 1.25, 9.25, 3.5, 12.75),
            Block.box(6.75, 3, 1.25, 7, 3.5, 14.75),
            Block.box(9.25, 3, 12.5, 12.5, 3.5, 12.75),
            Block.box(6.75, 3, 14.75, 14.75, 3.5, 15),
            Block.box(14.75, 3, 7, 15, 3.5, 15),
            Block.box(12.5, 3, 7, 12.75, 3.5, 12.75)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    // West Shape
    public static final VoxelShape WEST_SHAPE = Stream.of(
            Block.box(0.25, 0, 0.25, 15.75, 1, 15.75),
            Block.box(0, 0, 7, 0.25, 2, 9),
            Block.box(15.75, 0, 7, 16, 2, 9),
            Block.box(7, 0, 15.75, 9, 2, 16),
            Block.box(7, 1, 7, 9, 3, 9),
            Block.box(3.25, 1, 9, 7, 3, 12.75),
            Block.box(0.5, 1, 14.75, 7, 3, 15.5),
            Block.box(7, 1, 9, 9, 3, 12.75),
            Block.box(9, 1, 9, 15.5, 3, 15.5),
            Block.box(0.5, 1, 0.5, 15.5, 3, 7),
            Block.box(0.25, 1, 7, 1.25, 2, 9),
            Block.box(14.75, 1, 7, 15.75, 2, 9),
            Block.box(7, 1, 14.75, 9, 2, 15.75),
            Block.box(3.25, 1, 7, 7, 2, 9),
            Block.box(9, 1, 7, 12.75, 2, 9),
            Block.box(3.25, 1, 12.75, 7, 2, 14.75),
            Block.box(1.25, 1, 9, 3.25, 2, 14.75),
            Block.box(9, 3, 12.5, 9.25, 3.5, 15),
            Block.box(7, 2, 14.75, 9, 3, 15.5),
            Block.box(14.75, 2, 7, 15.5, 3, 9),
            Block.box(0.5, 2, 7, 1.25, 3, 9),
            Block.box(9, 2, 7, 11, 3, 9),
            Block.box(0.5, 1, 9, 1.25, 3, 14.75),
            Block.box(14.75, 3, 6.75, 15, 3.5, 9.25),
            Block.box(3.25, 3, 9, 14.75, 3.5, 9.25),
            Block.box(1.25, 3, 6.75, 14.75, 3.5, 7),
            Block.box(3.25, 3, 9.25, 3.5, 3.5, 12.5),
            Block.box(1, 3, 6.75, 1.25, 3.5, 14.75),
            Block.box(1, 3, 14.75, 9, 3.5, 15),
            Block.box(3.25, 3, 12.5, 9, 3.5, 12.75)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

}
