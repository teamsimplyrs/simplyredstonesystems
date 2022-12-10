package com.critmxbelvix.simplyrs.common.registers;

import com.critmxbelvix.simplyrs.SimplyRedstoneSystems;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockEntityRegister {

    public static final DeferredRegister<BlockEntityType<?>> BLOCKENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, SimplyRedstoneSystems.MOD_ID);




    public static void register(IEventBus eventBus)
    {
        BLOCKENTITIES.register(eventBus);
    }
}
