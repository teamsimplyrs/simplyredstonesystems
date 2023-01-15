package com.critmxbelvix.simplyrs.common.event;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
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

    public static BlockState getBlockStateUnderneathEntity(LivingEntity pTarget)
    {
        Level target_level = pTarget.getLevel();
        BlockPos target_pos = pTarget.getOnPos();
        return target_level.getBlockState(target_pos);
    }

    public static boolean blockIsPowerable(BlockState pBlockState) {

        return pBlockState.getProperties().contains(BlockStateProperties.POWERED);
    }

    public static boolean blockIsLightable(BlockState pBlockState)
    {
        return pBlockState.getProperties().contains(BlockStateProperties.LIT);
    }

    public static boolean blockIsExtendable(BlockState pBlockState)
    {
        return pBlockState.getProperties().contains(BlockStateProperties.EXTENDED);
    }

    public static void powerBlockUnderneath(LivingEntity pTarget)
    {
        BlockState blockstate_underneath = getBlockStateUnderneathEntity(pTarget);
        LOGGER.info(blockIsPowerable(blockstate_underneath));
        if (blockIsPowerable(blockstate_underneath))
        {
            LOGGER.info("POWERED Status for Block underneath:"+ blockstate_underneath.getValue(BlockStateProperties.POWERED));
            blockstate_underneath.setValue(BlockStateProperties.POWERED,true);
            pTarget.getLevel().setBlockAndUpdate(pTarget.getOnPos(),blockstate_underneath);
        }
        if (blockIsLightable(blockstate_underneath))
        {
            blockstate_underneath.setValue(BlockStateProperties.LIT,true);
            pTarget.getLevel().setBlockAndUpdate(pTarget.getOnPos(),blockstate_underneath);
        }
        if (blockIsExtendable(blockstate_underneath))
        {
            blockstate_underneath.setValue(BlockStateProperties.EXTENDED,true);
            pTarget.getLevel().setBlockAndUpdate(pTarget.getOnPos(),blockstate_underneath);
        }

    }

}
