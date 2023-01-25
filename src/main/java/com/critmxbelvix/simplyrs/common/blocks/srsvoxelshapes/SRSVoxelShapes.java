package com.critmxbelvix.simplyrs.common.blocks.srsvoxelshapes;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public class SRSVoxelShapes {

    // Arithmetic Unit North VoxelShape
    public static final VoxelShape ARITHMETIC_UNIT_SHAPE = Stream.of(
            Block.box(1, 1, 7, 7, 2, 9),
            Block.box(9, 1, 7, 15, 2, 9),
            Block.box(7, 1, 9, 9, 2, 15),
            Block.box(7, 1, 1, 9, 2, 7),
            Block.box(0.25, 1, 7, 1, 2, 9),
            Block.box(15, 1, 7, 15.75, 2, 9),
            Block.box(7, 1, 15, 9, 2, 15.75),
            Block.box(7, 1, 0.25, 9, 2, 1),
            Block.box(0.25, 0, 0.25, 15.75, 1, 15.75),
            Block.box(0.5, 1, 0.5, 7, 3, 7),
            Block.box(9, 1, 0.5, 15.5, 3, 7),
            Block.box(9, 1, 9, 15.5, 3, 15.5),
            Block.box(0.5, 1, 9, 7, 3, 15.5),
            Block.box(7, 0, 0, 9, 2, 0.25),
            Block.box(7, 0, 15.75, 9, 2, 16),
            Block.box(0, 0, 7, 0.25, 2, 9),
            Block.box(15.75, 0, 7, 16, 2, 9),
            Block.box(7, 1, 7, 9, 2.5, 9)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    // Clock VoxelShape
    public static final VoxelShape CLOCK_SHAPE = Stream.of(
            Block.box(1, 0, 9, 7, 2, 15),
            Block.box(1, 0, 1, 7, 2, 7),
            Block.box(9, 0, 1, 15, 2, 7),
            Block.box(9, 0, 9, 15, 2, 15),
            Block.box(7, 0, 7, 9, 2, 9),
            Block.box(7, 0, 1, 9, 1, 15),
            Block.box(1, 0, 7, 15, 1, 9),
            Block.box(7, 0, 15, 9, 0.5, 16),
            Block.box(7, 0, 0, 9, 0.5, 1),
            Block.box(15, 0, 7, 16, 0.5, 9),
            Block.box(0, 0, 7, 1, 0.5, 9),
            Block.box(7.25, 2, 7.25, 8.75, 3, 8.75),
            Block.box(7.5, 3, 7.5, 8.5, 4, 8.5),
            Block.box(5, 5, 5, 11, 11, 11)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    // Crossbridge VoxelShape
    public static final VoxelShape CROSSBRIDGE_SHAPE = Stream.of(
            Block.box(0.25, 0, 0.25, 15.75, 1, 15.75),
            Block.box(0.5, 1, 0.5, 15.5, 3, 15.5),
            Block.box(7, 2, 0, 7.25, 4, 7),
            Block.box(8.75, 2, 0, 9, 4, 7),
            Block.box(0, 2, 7, 7.25, 4, 7.25),
            Block.box(0, 2, 8.75, 7.25, 4, 9),
            Block.box(7, 2, 9, 7.25, 4, 16),
            Block.box(8.75, 2, 9, 9, 4, 16),
            Block.box(8.75, 2, 8.75, 16, 4, 9),
            Block.box(8.75, 2, 7, 16, 4, 7.25),
            Block.box(0, 0, 7, 0.25, 2, 9),
            Block.box(7, 0, 0, 9, 2, 0.25),
            Block.box(7, 0, 15.75, 9, 2, 16),
            Block.box(15.75, 0, 7, 16, 2, 9)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    // Gates VoxelShape
    public static final VoxelShape GATE_SHAPE = Block.box(1, 0, 1, 15, 1, 15);

    // Valve VoxelShape
    public static final VoxelShape VALVE_SHAPE = Stream.of(
            Block.box(7, 1, 7, 9, 2.5, 9),
            Block.box(7, 2.5, 7, 9, 3, 9),
            Block.box(0.25, 0, 0.25, 15.75, 1, 15.75),
            Block.box(0.25, 1, 0.25, 7, 2.5, 7),
            Block.box(9, 1, 0.25, 15.75, 2.5, 7),
            Block.box(9, 1, 9, 15.75, 2.5, 15.75),
            Block.box(0.25, 1, 9, 7, 2.5, 15.75),
            Block.box(7, 0, 0, 9, 2, 0.25),
            Block.box(7, 0, 15.75, 9, 2, 16),
            Block.box(15.75, 0, 7, 16, 2, 9),
            Block.box(0, 0, 7, 0.25, 2, 9),
            Block.box(7, 1, 0.25, 9, 2, 7),
            Block.box(7, 1, 9, 9, 2, 15.75),
            Block.box(9, 1, 7, 15.75, 2, 9),
            Block.box(0.25, 1, 7, 7, 2, 9),
            Block.box(6.5, 9, 6.5, 9.5, 9.25, 9.5),
            Block.box(6.5, 8, 6.5, 7.5, 9, 9.5),
            Block.box(6.5, 8, 6.5, 9.5, 9, 9.5),
            Block.box(7.5, 3, 7.5, 8.5, 9, 8.5),
            Block.box(1, 7.75, 1, 15, 9.25, 15)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    // Redstone Sprite VoxelShape
    public static final VoxelShape SPRITE_SHAPE = Block.box(6, 4, 6, 10, 8, 10);

    // Redstone Flip-Flop VoxelShape
    public static final VoxelShape FLIPFLOP_SHAPE = Stream.of(
            Block.box(0.25, 0, 0.25, 15.75, 1, 15.75),
            Block.box(0.5, 1, 9, 7, 3, 15.5),
            Block.box(7, 2, 14.75, 9, 3, 15.5),
            Block.box(9, 1, 9, 15.5, 3, 15.5),
            Block.box(9, 1, 0.5, 15.5, 3, 7),
            Block.box(0.5, 1, 0.5, 7, 3, 7),
            Block.box(7, 1, 9, 9, 2, 12.75),
            Block.box(7, 1, 7, 9, 3, 9),
            Block.box(7, 1, 3.25, 9, 2, 7),
            Block.box(9, 1, 7, 12.75, 2, 9),
            Block.box(3.25, 1, 7, 7, 2, 9),
            Block.box(14.75, 3, 7, 15.5, 3.5, 9),
            Block.box(14.75, 1, 7, 15.75, 2, 9),
            Block.box(15.75, 0, 7, 16, 2, 9),
            Block.box(7, 2, 0.5, 9, 3, 1.25),
            Block.box(7, 1, 0.25, 9, 2, 1.25),
            Block.box(7, 0, 0, 9, 2, 0.25),
            Block.box(0.5, 3, 7, 1.25, 3.5, 9),
            Block.box(0.25, 1, 7, 1.25, 2, 9),
            Block.box(0, 0, 7, 0.25, 2, 9),
            Block.box(7, 1, 14.75, 9, 2, 15.75),
            Block.box(7, 0, 15.75, 9, 2, 16),
            Block.box(9, 3, 9, 9.25, 3.5, 15.5),
            Block.box(7, 3, 14.75, 9, 3.5, 15.5),
            Block.box(6.75, 3, 9, 7, 3.5, 15.5),
            Block.box(0.5, 3, 9, 7, 3.5, 9.25),
            Block.box(0.5, 3, 6.75, 7, 3.5, 7),
            Block.box(0.5, 2, 7, 1.25, 3, 9),
            Block.box(6.75, 3, 0.5, 7, 3.5, 7),
            Block.box(9, 3, 0.5, 9.25, 3.5, 7),
            Block.box(9, 3, 6.75, 15.5, 3.5, 7),
            Block.box(9, 3, 9, 15.5, 3.5, 9.25),
            Block.box(14.75, 2, 7, 15.5, 3, 9),
            Block.box(7,3,0.5,9,3.5,1.25)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

}
