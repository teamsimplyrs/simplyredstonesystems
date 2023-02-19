package com.critmxbelvix.simplyrs.client.menu;

import com.critmxbelvix.simplyrs.common.blocks.entities.ArithmeticUnit.ArithmeticBlockEntity;
import com.critmxbelvix.simplyrs.common.registers.BlockRegister;
import com.critmxbelvix.simplyrs.common.registers.MenuTypeRegister;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class RedstoneArithmeticUnitMenu extends AbstractContainerMenu implements Supplier<Map<Integer, Slot>> {

    public ArithmeticBlockEntity blockEntity;
    public final static HashMap<String, Object> guiState = new HashMap<>();

    private final Level level;
    public final Player player;
    public RedstoneArithmeticUnitMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId,inv,inv.player.level.getBlockEntity(extraData.readBlockPos()));
    }

    public RedstoneArithmeticUnitMenu(int pContainerId, Inventory inv, BlockEntity entity) {
        super(MenuTypeRegister.REDSTONE_ARITHMETIC_UNIT_MENU.get(), pContainerId);
        checkContainerSize(inv, 4);
        blockEntity = ((ArithmeticBlockEntity) entity);
        this.level = inv.player.level;
        this.player = inv.player;
        this.addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return blockEntity.getOperands();
            }

            @Override
            public void set(int pValue) {
                blockEntity.setOperands(pValue);
            }
        });
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, BlockRegister.REDSTONE_ARITHMETIC_UNIT.get());
    }

    @Override
    public Map<Integer, Slot> get() {
        return null;
    }
}

