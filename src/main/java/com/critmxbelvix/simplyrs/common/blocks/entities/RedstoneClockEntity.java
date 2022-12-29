package com.critmxbelvix.simplyrs.common.blocks.entities;

import com.critmxbelvix.simplyrs.client.menu.RedstoneClockMenu;
import com.critmxbelvix.simplyrs.common.blocks.RedstoneClock;
import com.critmxbelvix.simplyrs.common.registers.BlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
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

import javax.annotation.Nonnull;

public class RedstoneClockEntity extends BlockEntity implements MenuProvider, IAnimatable {
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private static final Logger LOGGER = LogManager.getLogger();
    public int delay;
    public int duration;
    private int ticksSinceNextPulse;

    private final ItemStackHandler itemHandler = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    public RedstoneClockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityRegister.REDSTONE_CLOCK_ENTITY.get(), pPos, pBlockState);
        this.delay=1;
        this.ticksSinceNextPulse=0;
        this.duration = 10;
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

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }


    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        tag.putInt("delay",this.delay);
        tag.putInt("duration",this.duration);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        this.delay = nbt.getInt("delay");
        this.duration = nbt.getInt("duration");
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, RedstoneClockEntity pBlockEntity) {
        pBlockEntity.ticksSinceNextPulse++;
        boolean powered;
        boolean prevPowered = pState.getValue(RedstoneClock.POWERED);
        if (pBlockEntity.ticksSinceNextPulse < pBlockEntity.delay) {
            powered = false;
        }
        else if (pBlockEntity.ticksSinceNextPulse < pBlockEntity.delay + pBlockEntity.duration) {
            powered = true;
        }
        else {
            pBlockEntity.ticksSinceNextPulse = 0;
            powered = false;
        }
        if (prevPowered != powered) {
            pLevel.setBlockAndUpdate(pPos, pState.setValue(RedstoneClock.POWERED, powered));
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
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
