package com.critmxbelvix.simplyrs.common.blocks.entities;

import com.critmxbelvix.simplyrs.common.registers.BlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SpriteEntity extends BlockEntity {
    int lifetime;
    private static final Logger LOGGER = LogManager.getLogger();

    public SpriteEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityRegister.REDSTONE_SPRITE_ENTITY.get(), pPos, pBlockState);
        lifetime = 80;
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, SpriteEntity pBlockEntity) {

    }
}
