package com.critmxbelvix.simplyrs.common.registers;

import com.critmxbelvix.simplyrs.SimplyRedstoneSystems;
import com.critmxbelvix.simplyrs.common.blocks.entities.ClockBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityRegister {

    public static final DeferredRegister<BlockEntityType<?>> BLOCKENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, SimplyRedstoneSystems.MOD_ID);

    public static final RegistryObject<BlockEntityType<ClockBlockEntity>> CLOCK_BLOCK_ENTITY =
            BLOCKENTITIES.register("clock_block_entity", () ->
                BlockEntityType.Builder.of(ClockBlockEntity::new,BlockRegister.CLOCK_BLOCK.get()).build(null));


    public static void register(IEventBus eventBus)
    {
        BLOCKENTITIES.register(eventBus);
    }
}
