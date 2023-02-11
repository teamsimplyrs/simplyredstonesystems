package com.critmxbelvix.simplyrs.common.blocks.entities.RedstoneClock;

import com.critmxbelvix.simplyrs.client.menu.RedstoneClockMenu;
import com.critmxbelvix.simplyrs.common.blocks.RedstoneClock;
import com.critmxbelvix.simplyrs.common.registers.BlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class RedstoneClockEntity extends BlockEntity implements MenuProvider, IAnimatable {
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private static final Logger LOGGER = LogManager.getLogger();
    public int delay;
    public int duration;
    public boolean redstone_needed;
    private int ticksSinceNextPulse;

    public RedstoneClockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityRegister.REDSTONE_CLOCK_ENTITY.get(), pPos, pBlockState);
        this.delay=1;
        this.ticksSinceNextPulse=0;
        this.duration = 10;
        this.redstone_needed = false;
    }

    @Override
    public Component getDisplayName() {
        return new TextComponent("Redstone Clock");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new RedstoneClockMenu(pContainerId,pPlayerInventory,this);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.putInt("delay",this.delay);
        tag.putInt("duration",this.duration);
        tag.putBoolean("toggle",this.redstone_needed);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.delay = nbt.getInt("delay");
        this.duration = nbt.getInt("duration");
        this.redstone_needed = nbt.getBoolean("toggle");
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.redstone_needed = pkt.getTag().getBoolean("toggle");
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("toggle",this.redstone_needed);
        return tag;
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, RedstoneClockEntity pBlockEntity) {

        pBlockEntity.ticksSinceNextPulse++;
        boolean powered;
        boolean prevPowered = pState.getValue(RedstoneClock.POWERED);
        if (RedstoneClock.shouldTurnOn(pLevel,pPos,pState)) {
            if (pBlockEntity.ticksSinceNextPulse < pBlockEntity.delay) {
                powered = false;
            } else if (pBlockEntity.ticksSinceNextPulse < pBlockEntity.delay + pBlockEntity.duration) {
                powered = true;
            } else {
                pBlockEntity.ticksSinceNextPulse = 0;
                powered = false;
            }
            if (prevPowered != powered) {
                pLevel.setBlockAndUpdate(pPos, pState.setValue(RedstoneClock.POWERED, powered));
            }
        }
        if (pState.getValue(RedstoneClock.POWERED) && !RedstoneClock.shouldTurnOn(pLevel,pPos,pState))
        {
            pLevel.setBlockAndUpdate(pPos,pState.setValue(RedstoneClock.POWERED, false));
        }
    }

    /* Geckolib */

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<RedstoneClockEntity>
                (this, "controller", 0, this::predicate));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("new", ILoopType.EDefaultLoopTypes.LOOP));
        event.getController().setAnimationSpeed(Math.max((5.0d/delay),0.5));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
