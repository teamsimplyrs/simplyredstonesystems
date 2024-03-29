package com.critmxbelvix.simplyrs.common.event;

import com.critmxbelvix.simplyrs.SimplyRedstoneSystems;
import com.critmxbelvix.simplyrs.common.blocks.RedstoneSprite;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
        Level level = breakEvent.getPlayer().getLevel();
        if(!level.isClientSide()){
            boolean toolIsValid = tool instanceof ActivatorAxe || tool instanceof ActivatorPickaxe || tool instanceof ActivatorHoe || tool instanceof ActivatorShovel;
            BlockPos breakPos = breakEvent.getPos();
            if(level.getBlockState(breakPos).getBlock() instanceof RedstoneSprite){
                breakEvent.setCanceled(true);
            }
            else{
                if (toolIsValid)
                {
                    breakEvent.setCanceled(true);
                    level.destroyBlock(breakPos,true);
                    level.setBlockAndUpdate(breakPos, BlockRegister.REDSTONE_SPRITE.get().defaultBlockState());
                }
            }
        }
    }

//    public static void leftClickEvent(BlockEvent. )
//    {
//        Item tool = breakEvent.getPlayer().getMainHandItem().getItem();
//        boolean toolIsValid = tool instanceof ActivatorAxe || tool instanceof ActivatorPickaxe || tool instanceof ActivatorHoe || tool instanceof ActivatorShovel;
//        Level level = breakEvent.getPlayer().getLevel();
//        BlockPos breakPos = breakEvent.getPos();
//        LOGGER.info("Tool: "+tool+"\nIs tool valid?"+toolIsValid);
//        if (toolIsValid)
//        {
//            level.setBlock(breakPos, BlockRegister.REDSTONE_SPRITE.get().defaultBlockState(),2);
//            LOGGER.info("New block in place of broken block: "+level.getBlockState(breakPos));
//        }
//    }

    public static void activatorBootsFallDamageEvent(LivingFallEvent fallEvent)
    {
        ItemStack bootsWorn = fallEvent.getEntityLiving().getArmorSlots().iterator().next();
        BlockPos placePosition = fallEvent.getEntityLiving().blockPosition();
        boolean isAir = fallEvent.getEntityLiving().getLevel().getBlockState(placePosition).getBlock() == Blocks.AIR;
        if (bootsWorn.getItem()==ItemRegister.ACTIVATOR_BOOTS.get() && fallEvent.getEntityLiving().fallDistance>3.3f && isAir)
            fallEvent.getEntityLiving().getLevel().setBlockAndUpdate(fallEvent.getEntityLiving().blockPosition(),BlockRegister.REDSTONE_SPRITE.get().defaultBlockState());
    }
    public static void activatorChestplateDamageEvent(LivingDamageEvent dmgEvent)
    {
        DamageSource source = dmgEvent.getSource();
        Iterable<ItemStack> armorWorn = dmgEvent.getEntityLiving().getArmorSlots();
        boolean isActivatorChestplateFlag = false;
        BlockPos placePosition = dmgEvent.getEntityLiving().blockPosition();
        boolean isAir = dmgEvent.getEntityLiving().getLevel().getBlockState(placePosition).getBlock() == Blocks.AIR;
        for (ItemStack chestplate : armorWorn)
            if (chestplate.is(ItemRegister.ACTIVATOR_CHESPLATE.get())) { isActivatorChestplateFlag = true; break; }
        if (isActivatorChestplateFlag && source!=DamageSource.FALL && isAir)
            dmgEvent.getEntityLiving().getLevel().setBlockAndUpdate(dmgEvent.getEntityLiving().blockPosition(),BlockRegister.REDSTONE_SPRITE.get().defaultBlockState());
    }

    public static void activatorLeggingsJumpEvent(LivingEvent.LivingJumpEvent jumpEvent)
    {
        Iterable<ItemStack> armorWorn = jumpEvent.getEntityLiving().getArmorSlots();
        boolean isActivatorLeggingsFlag = false;
        BlockPos placePosition = jumpEvent.getEntityLiving().blockPosition();
        boolean isAir = jumpEvent.getEntityLiving().getLevel().getBlockState(placePosition).getBlock() == Blocks.AIR;
        for (ItemStack leggings : armorWorn)
            if (leggings.is(ItemRegister.ACTIVATOR_LEGGINGS.get())) { isActivatorLeggingsFlag = true; break; }

        if (isActivatorLeggingsFlag && isAir)
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