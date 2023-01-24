package com.critmxbelvix.simplyrs.common.event;

import com.critmxbelvix.simplyrs.common.registers.BlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class EventEntityHitWithSword {

    private static final Logger LOGGER = LogManager.getLogger();

    public static Block getBlockUnderneathEntity(LivingEntity pTarget)
    {
        Level target_level = pTarget.getLevel();
        BlockPos target_pos = pTarget.getOnPos();
        return target_level.getBlockState(target_pos).getBlock();
    }

    public static void summonSpriteOnPos(LivingEntity pTarget)
    {
        Level target_level = pTarget.getLevel();
        if (pTarget.getFeetBlockState().getBlock()== Blocks.AIR) target_level.setBlockAndUpdate(pTarget.blockPosition(), BlockRegister.REDSTONE_SPRITE.get().defaultBlockState());
    }
}
