package com.critmxbelvix.simplyrs.common.event;

import com.critmxbelvix.simplyrs.SimplyRedstoneSystems;
import com.critmxbelvix.simplyrs.common.blocks.entities.RedstoneClock.RedstoneClockRenderer;
import com.critmxbelvix.simplyrs.common.blocks.entities.RedstoneValve.RedstoneValveRenderer;
import com.critmxbelvix.simplyrs.common.items.srsarmory.ActivatorAxe;
import com.critmxbelvix.simplyrs.common.items.srsarmory.ActivatorHoe;
import com.critmxbelvix.simplyrs.common.items.srsarmory.ActivatorPickaxe;
import com.critmxbelvix.simplyrs.common.items.srsarmory.ActivatorShovel;
import com.critmxbelvix.simplyrs.common.registers.BlockEntityRegister;
import com.critmxbelvix.simplyrs.common.registers.BlockRegister;
import com.critmxbelvix.simplyrs.common.registers.ItemRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.IModBusEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.shadowed.eliotlash.mclib.math.functions.limit.Min;

@Mod.EventBusSubscriber(modid = SimplyRedstoneSystems.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class SRSEvents {

    private static final Logger LOGGER = LogManager.getLogger();
    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlockEntityRegister.REDSTONE_CLOCK_ENTITY.get(), RedstoneClockRenderer::new);
        event.registerBlockEntityRenderer(BlockEntityRegister.REDSTONE_VALVE_ENTITY.get(), RedstoneValveRenderer::new);
    }

    public static void activatorMineSummonSpriteEvent(BlockEvent.BreakEvent breakEvent)
    {
        Item tool = breakEvent.getPlayer().getMainHandItem().getItem();
        boolean toolIsValid = tool instanceof ActivatorAxe || tool instanceof ActivatorPickaxe || tool instanceof ActivatorHoe || tool instanceof ActivatorShovel;
        Level level = breakEvent.getPlayer().getLevel();
        BlockPos breakPos = breakEvent.getPos();
        LOGGER.info("Tool: "+tool+"\nIs tool valid?"+toolIsValid);
        if (toolIsValid)
        {
            level.setBlock(breakPos, BlockRegister.REDSTONE_SPRITE.get().defaultBlockState(),2);
            LOGGER.info("New block in place of broken block: "+level.getBlockState(breakPos));
        }
    }

    public static void activatorBootsFallDamageEvent(LivingFallEvent fallEvent)
    {
        LOGGER.info(fallEvent.getEntityLiving()+" has taken fall damage!");
        ItemStack bootsWorn = fallEvent.getEntityLiving().getArmorSlots().iterator().next();
        if (bootsWorn.getItem()==ItemRegister.ACTIVATOR_BOOTS.get() && fallEvent.getEntityLiving().fallDistance>3.3f)
            fallEvent.getEntityLiving().getLevel().setBlockAndUpdate(fallEvent.getEntityLiving().blockPosition(),BlockRegister.REDSTONE_SPRITE.get().defaultBlockState());
    }
    public static void activatorChestplateDamageEvent(LivingDamageEvent dmgEvent)
    {
        DamageSource source = dmgEvent.getSource();
        Iterable<ItemStack> armorWorn = dmgEvent.getEntityLiving().getArmorSlots();
        boolean isActivatorChestplateFlag = false;
        for (ItemStack chestplate : armorWorn)
            if (chestplate.is(ItemRegister.ACTIVATOR_CHESPLATE.get())) { isActivatorChestplateFlag = true; break; }
        if (isActivatorChestplateFlag && source!=DamageSource.FALL)
            dmgEvent.getEntityLiving().getLevel().setBlockAndUpdate(dmgEvent.getEntityLiving().blockPosition(),BlockRegister.REDSTONE_SPRITE.get().defaultBlockState());
    }

    public static void activatorLeggingsJumpEvent(LivingEvent.LivingJumpEvent jumpEvent)
    {
        Iterable<ItemStack> armorWorn = jumpEvent.getEntityLiving().getArmorSlots();
        boolean isActivatorLeggingsFlag = false;
        for (ItemStack leggings : armorWorn)
            if (leggings.is(ItemRegister.ACTIVATOR_LEGGINGS.get())) { isActivatorLeggingsFlag = true; break; }

        if (isActivatorLeggingsFlag)
            jumpEvent.getEntityLiving().getLevel().setBlockAndUpdate(jumpEvent.getEntityLiving().blockPosition(),BlockRegister.REDSTONE_SPRITE.get().defaultBlockState());
    }
    public static void registerEvents() {
        MinecraftForge.EVENT_BUS.register(SRSEvents.class);
        MinecraftForge.EVENT_BUS.addListener(SRSEvents::activatorMineSummonSpriteEvent);
        MinecraftForge.EVENT_BUS.addListener(SRSEvents::activatorBootsFallDamageEvent);
        MinecraftForge.EVENT_BUS.addListener(SRSEvents::activatorChestplateDamageEvent);
        MinecraftForge.EVENT_BUS.addListener(SRSEvents::activatorLeggingsJumpEvent);
    }
}