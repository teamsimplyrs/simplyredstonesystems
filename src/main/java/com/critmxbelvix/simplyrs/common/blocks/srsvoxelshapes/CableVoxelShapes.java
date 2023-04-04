package com.critmxbelvix.simplyrs.common.blocks.srsvoxelshapes;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.stream.Stream;

public class CableVoxelShapes {

    // Isolated
    public static final VoxelShape ISO = Block.box(4.5, 0, 4.5, 11.5, 7, 11.5);

    public static VoxelShape getShape(BlockState pState, boolean N, boolean E, boolean W, boolean S, boolean U, boolean D)
    {
        VoxelShape empty_box = Block.box(0,0,0,0,0,0);
        VoxelShape boxN,boxE,boxW,boxS,boxU,boxD, final_shape;

        if (!(N || E || W || S || U || D))
            return ISO;
        else
        {
            boxN = N ? Block.box(6.75, 0, 0, 9.25, 2.5, 9.25) : empty_box;
            boxE = E ? Block.box(6.75, 0, 6.75, 16, 2.5, 9.25) : empty_box;
            boxW = W ? Block.box(0, 0, 6.75, 9.25, 2.5, 9.25) : empty_box;
            boxS = S ? Block.box(6.75, 0, 6.75, 9.25, 2.5, 16) : empty_box;
            boxU = U ? Block.box(6.75, 0, 6.75, 9.25, 9.25, 9.25) : empty_box;
            boxD = D ? Block.box(6.75, -6.75, 6.75, 9.25, 2.5, 9.25) : empty_box;

            final_shape = Stream.of(boxN,boxE,boxW,boxS,boxU,boxD)
                    .reduce((v1,v2) -> Shapes.join(v1,v2,BooleanOp.OR)).get();
        }

        return final_shape;
    }

//    // NU
//    public static final VoxelShape NU = Shapes.join(Block.box(6.75, 0, 0, 9.25, 2.5, 9.25), Block.box(6.75, 2.5, 6.75, 9.25, 9.25, 9.25), BooleanOp.OR);
//
//    // ND
//    public static final VoxelShape ND = Shapes.join(Block.box(6.75, 0, 0, 9.25, 2.5, 9.25), Block.box(6.75, -6.75, 6.75, 9.25, 0, 9.25), BooleanOp.OR);
//
//    // NUD
//    public static final VoxelShape NUD = Stream.of(
//            Block.box(6.75, 0, 0, 9.25, 2.5, 9.25),
//            Block.box(6.75, -6.75, 6.75, 9.25, 0, 9.25),
//            Block.box(6.75, 2.5, 6.75, 9.25, 9.25, 9.25)
//    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
//
//    // NE
//    public static final VoxelShape NE = Shapes.join(Block.box(6.75, 0, 0, 9.25, 2.5, 9.25), Block.box(9.25, 0, 6.75, 16, 2.5, 9.25), BooleanOp.OR);
//
//    // NEU
//    public static final VoxelShape NEU = Stream.of(
//            Block.box(6.75, 0, 0, 9.25, 2.5, 9.25),
//            Block.box(6.75, 2.5, 6.75, 9.25, 9.25, 9.25),
//            Block.box(9.25, 0, 6.75, 16, 2.5, 9.25)
//    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
//
//    // NED
//    public static final VoxelShape NED = Stream.of(
//            Block.box(6.75, 0, 0, 9.25, 2.5, 9.25),
//            Block.box(6.75, -6.75, 6.75, 9.25, 0, 9.25),
//            Block.box(9.25, 0, 6.75, 16, 2.5, 9.25)
//    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
//
//    // NEUD
//    public static final VoxelShape NEUD = Stream.of(
//            Block.box(6.75, 0, 0, 9.25, 2.5, 9.25),
//            Block.box(6.75, -6.75, 6.75, 9.25, 0, 9.25),
//            Block.box(6.75, 2.5, 6.75, 9.25, 9.25, 9.25),
//            Block.box(9.25, 0, 6.75, 16, 2.5, 9.25)
//    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
//
//    // NW
//    public static final VoxelShape NW = Shapes.join(Block.box(6.75, 0, 0, 9.25, 2.5, 9.25), Block.box(0, 0, 6.75, 6.75, 2.5, 9.25), BooleanOp.OR);
//
//    // NWU
//    public static final VoxelShape NWU = null;
//
//    // NWD
//    public static final VoxelShape NWD = null;
//
//    // NWUD
//    public static final VoxelShape NWUD = null;
//
//    // NS
//    public static final VoxelShape NS = Shapes.join(Block.box(6.75, 0, 0, 9.25, 2.5, 9.25), Block.box(6.75, 0, 9.25, 9.25, 2.5, 16), BooleanOp.OR);
//
//    // NSU
//    public static final VoxelShape NSU = Stream.of(
//            Block.box(6.75, 0, 6.75, 9.25, 9.25, 9.25),
//            Block.box(6.75, 0, 0, 9.25, 2.5, 6.75),
//            Block.box(6.75, 0, 9.25, 9.25, 2.5, 16)
//    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
//
//    // NSD
//    public static final VoxelShape NSD = Stream.of(
//            Block.box(6.75, 0, 0, 9.25, 2.5, 9.25),
//            Block.box(6.75, -6.75, 6.75, 9.25, 0, 9.25),
//            Block.box(6.75, 0, 9.25, 9.25, 2.5, 16)
//    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
//
//    // NSUD
//    public static final VoxelShape NSUD = Stream.of(
//            Block.box(6.75, 0, 0, 9.25, 2.5, 9.25),
//            Block.box(6.75, -6.75, 6.75, 9.25, 0, 9.25),
//            Block.box(6.75, 2.5, 6.75, 9.25, 9.25, 9.25),
//            Block.box(6.75, 0, 9.25, 9.25, 2.5, 16)
//    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
//
//    // NWE
//    public static final VoxelShape NWE = Stream.of(
//            Block.box(6.75, 0, 0, 9.25, 2.5, 9.25),
//            Block.box(0, 0, 6.75, 6.75, 2.5, 9.25),
//            Block.box(9.25, 0, 6.75, 16, 2.5, 9.25)
//    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
//
//    // NWEU
//    public static final VoxelShape NWEU = Stream.of(
//            Block.box(0, 0, 6.75, 6.75, 2.5, 9.25),
//            Block.box(9.25, 0, 6.75, 16, 2.5, 9.25),
//            Block.box(6.75, 2.5, 6.75, 9.25, 9.25, 9.25),
//            Block.box(6.75, 0, 0, 9.25, 2.5, 9.25)
//    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
//
//    // NWED
//    public static final VoxelShape NWED = Stream.of(
//            Block.box(6.75, 0, 0, 9.25, 2.5, 9.25),
//            Block.box(6.75, -6.75, 6.75, 9.25, 0, 9.25),
//            Block.box(9.25, 0, 6.75, 16, 2.5, 9.25),
//            Block.box(0, 0, 6.75, 6.75, 2.5, 9.25)
//    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
//
//    // NWEUD
//    public static final VoxelShape NWEUD = null;
//
//    // NWS
//    public static final VoxelShape NWS = Stream.of(
//            Block.box(6.75, 0, 0, 9.25, 2.5, 9.25),
//            Block.box(6.75, 0, 9.25, 9.25, 2.5, 16),
//            Block.box(0, 0, 6.75, 6.75, 2.5, 9.25)
//    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
//
//    // NWSU
//    public static final VoxelShape NWSU = null;
//
//    // NWSD
//    public static final VoxelShape NWSD = null;
//
//    // NWSUD
//    public static final VoxelShape NWSUD = null;
//
//    // NES
//    public static final VoxelShape NES = Stream.of(
//            Block.box(6.75, 0, 0, 9.25, 2.5, 9.25),
//            Block.box(6.75, 0, 9.25, 9.25, 2.5, 16),
//            Block.box(9.25, 0, 6.75, 16, 2.5, 9.25)
//    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
//
//    // NESU
//    public static final VoxelShape NESU = null;
//
//    // NESD
//    public static final VoxelShape NESD = null;
//
//    // NESUD
//    public static final VoxelShape NESUD = null;
//
//    // NEWS
//    public static final VoxelShape NEWS = Stream.of(
//            Block.box(6.75, 0, 0, 9.25, 2.5, 9.25),
//            Block.box(6.75, 0, 9.25, 9.25, 2.5, 16),
//            Block.box(9.25, 0, 6.75, 16, 2.5, 9.25),
//            Block.box(0, 0, 6.75, 6.75, 2.5, 9.25)
//    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
//
//    // NEWSU
//    public static final VoxelShape NEWSU = Stream.of(
//            Block.box(0, 0, 6.75, 6.75, 2.5, 9.25),
//            Block.box(9.25, 0, 6.75, 16, 2.5, 9.25),
//            Block.box(6.75, 2.5, 6.75, 9.25, 9.25, 9.25),
//            Block.box(6.75, 0, 0, 9.25, 2.5, 9.25),
//            Block.box(6.75, 0, 9.25, 9.25, 2.5, 16)
//    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
//
//    // NEWSD
//    public static final VoxelShape NEWSD = Stream.of(
//            Block.box(6.75, 0, 0, 9.25, 2.5, 9.25),
//            Block.box(6.75, -6.75, 6.75, 9.25, 0, 9.25),
//            Block.box(9.25, 0, 6.75, 16, 2.5, 9.25),
//            Block.box(0, 0, 6.75, 6.75, 2.5, 9.25),
//            Block.box(6.75, 0, 9.25, 9.25, 2.5, 16)
//    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
//
//    // NEWSUD
//    public static final VoxelShape NEWSUD = null;
//
//    // E
//    public static final VoxelShape E = Block.box(6.75, 0, 6.75, 16, 2.5, 9.25);
//
//    // EU
//    public static final VoxelShape EU = null;
//
//    // ED
//    public static final VoxelShape ED = null;
//
//    // EUD
//    public static final VoxelShape EUD = null;
//
//    // EW
//    public static final VoxelShape EW = Shapes.join(Block.box(9.25, 0, 6.75, 16, 2.5, 9.25), Block.box(0, 0, 6.75, 9.25, 2.5, 9.25), BooleanOp.OR);
//
//    // EWU
//    public static final VoxelShape EWU = null;
//
//    // EWD
//    public static final VoxelShape EWD = null;
//
//    // EWUD
//    public static final VoxelShape EWUD = null;
//
//    // ES
//    public static final VoxelShape ES = Shapes.join(Block.box(6.75, 0, 9.25, 9.25, 2.5, 16), Block.box(6.75, 0, 6.75, 16, 2.5, 9.25), BooleanOp.OR);
//
//    // ESU
//    public static final VoxelShape ESU = null;
//
//    // ESD
//    public static final VoxelShape ESD = null;
//
//    // ESUD
//    public static final VoxelShape ESUD = null;
//
//    // EWS
//    public static final VoxelShape EWS = Stream.of(
//            Block.box(6.75, 0, 9.25, 9.25, 2.5, 16),
//            Block.box(6.75, 0, 6.75, 16, 2.5, 9.25),
//            Block.box(0, 0, 6.75, 6.75, 2.5, 9.25)
//    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
//
//    // EWSU
//    public static final VoxelShape EWSU = null;
//
//    // EWSD
//    public static final VoxelShape EWSD = null;
//
//    // EWSUD
//    public static final VoxelShape EWSUD = null;
//
//    // W
//    public static final VoxelShape W = Block.box(0, 0, 6.75, 9.25, 2.5, 9.25);
//
//    // WU
//    public static final VoxelShape WU = null;
//
//    // WD
//    public static final VoxelShape WD = null;
//
//    // WUD
//    public static final VoxelShape WUD = null;
//
//    // WS
//    public static final VoxelShape WS = Shapes.join(Block.box(6.75, 0, 9.25, 9.25, 2.5, 16), Block.box(0, 0, 6.75, 9.25, 2.5, 9.25), BooleanOp.OR);
//
//    // WSU
//    public static final VoxelShape WSU = null;
//
//    // WSD
//    public static final VoxelShape WSD = null;
//
//    // WSUD
//    public static final VoxelShape WSUD = null;
//
//    // S
//    public static final VoxelShape S = Block.box(6.75, 0, 6.75, 9.25, 2.5, 16);
//
//    // SU
//    public static final VoxelShape SU = null;
//
//    // SD
//    public static final VoxelShape SD = null;
//
//    // SUD
//    public static final VoxelShape SUD = null;
//
//    // U
//    public static final VoxelShape U = Block.box(6.75, 0, 6.75, 9.25, 9.25, 9.25);
//
//    // D
//    public static final VoxelShape D = null;



}
