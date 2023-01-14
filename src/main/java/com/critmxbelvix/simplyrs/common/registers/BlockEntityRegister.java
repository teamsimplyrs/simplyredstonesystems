package com.critmxbelvix.simplyrs.common.registers;

import com.critmxbelvix.simplyrs.SimplyRedstoneSystems;
import com.critmxbelvix.simplyrs.common.blocks.entities.ArithmeticUnit.ArithmeticBlockEntity;
import com.critmxbelvix.simplyrs.common.blocks.entities.RedstoneClock.RedstoneClockEntity;
import com.critmxbelvix.simplyrs.common.blocks.entities.RedstoneValve.RedstoneValveEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityRegister {

    public static final DeferredRegister<BlockEntityType<?>> BLOCKENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, SimplyRedstoneSystems.MOD_ID);

    public static final RegistryObject<BlockEntityType<RedstoneClockEntity>> REDSTONE_CLOCK_ENTITY =
            BLOCKENTITIES.register("redstone_clock_entity", () ->
                BlockEntityType.Builder.of(RedstoneClockEntity::new,BlockRegister.REDSTONE_CLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<ArithmeticBlockEntity>> REDSTONE_ARITHMETIC_UNIT_ENTITY =
            BLOCKENTITIES.register("redstone_arithmetic_unit_entity", () ->
                    BlockEntityType.Builder.of(ArithmeticBlockEntity::new,BlockRegister.REDSTONE_ARITHMETIC_UNIT.get()).build(null));

    public static final RegistryObject<BlockEntityType<RedstoneValveEntity>> REDSTONE_VALVE_ENTITY =
            BLOCKENTITIES.register("redstone_valve_entity", () ->
                    BlockEntityType.Builder.of(RedstoneValveEntity::new,BlockRegister.REDSTONE_VALVE.get()).build(null));

    public static void register(IEventBus eventBus)
    {
        BLOCKENTITIES.register(eventBus);
    }
}                                                                                                                                                                                                       
