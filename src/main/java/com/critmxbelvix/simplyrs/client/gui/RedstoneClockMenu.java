package com.critmxbelvix.simplyrs.client.gui;

import com.critmxbelvix.simplyrs.common.blocks.entities.RedstoneClockEntity;
import com.critmxbelvix.simplyrs.common.registers.BlockRegister;
import com.critmxbelvix.simplyrs.common.registers.MenuTypeRegister;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class RedstoneClockMenu extends AbstractContainerMenu implements Supplier<Map<Integer,Slot>> {

    public RedstoneClockEntity blockEntity;
    public final static HashMap<String, Object> guiState = new HashMap<>();

    private boolean bound = false;
    private IItemHandler internal;
    private final Level level;
    public final Player player;
    public final DataSlot delaySlot;
    public final DataSlot durationSlot;

    public RedstoneClockMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId,inv,inv.player.level.getBlockEntity(extraData.readBlockPos()));
    }

    public RedstoneClockMenu(int pContainerId, Inventory inv, BlockEntity entity) {
        super(MenuTypeRegister.REDSTONE_CLOCK_MENU.get(), pContainerId);
        checkContainerSize(inv, 4);
        blockEntity = ((RedstoneClockEntity) entity);
        this.level = inv.player.level;
        this.player = inv.player;
        this.internal = new ItemStackHandler(0);

        delaySlot = new DataSlot() {
            @Override
            public int get() {
                return RedstoneClockMenu.this.blockEntity.delay;
            }

            @Override
            public void set(int pValue) {
                RedstoneClockMenu.this.blockEntity.delay = pValue;
            }
        };

        durationSlot = new DataSlot() {
            @Override
            public int get() {
                return RedstoneClockMenu.this.blockEntity.duration;
            }

            @Override
            public void set(int pValue) {
                RedstoneClockMenu.this.blockEntity.duration = pValue;
            }
        };
        this.addDataSlot(delaySlot);
        this.addDataSlot(durationSlot);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        return sourceStack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, BlockRegister.REDSTONE_CLOCK.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 86 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 144));
        }
    }


    @Override
    public boolean clickMenuButton(Player pPlayer, int pId) {
        if(pId==0){
            this.blockEntity.delay++;
            this.blockEntity.setChanged();
            return true;
        }
        else if(pId==1 && this.blockEntity.delay!=1){
            this.blockEntity.delay--;
            this.blockEntity.setChanged();
            return true;
        }
        else if(pId==2){
            this.blockEntity.duration++;
            this.blockEntity.setChanged();
            return true;
        }
        else if(pId==3 && this.blockEntity.duration!=1){
            this.blockEntity.duration--;
            this.blockEntity.setChanged();
            return true;
        }
        else return false;
    }

    @Override
    public Map<Integer, Slot> get() {
        return null;
    }
}
