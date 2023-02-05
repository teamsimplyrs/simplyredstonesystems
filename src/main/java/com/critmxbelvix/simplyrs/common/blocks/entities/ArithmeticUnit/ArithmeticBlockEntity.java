package com.critmxbelvix.simplyrs.common.blocks.entities.ArithmeticUnit;

import com.critmxbelvix.simplyrs.client.menu.RedstoneArithmeticUnitMenu;
import com.critmxbelvix.simplyrs.common.registers.BlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ArithmeticBlockEntity extends BlockEntity implements MenuProvider {
    private int output;
    private int operation;

    public ArithmeticBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityRegister.REDSTONE_ARITHMETIC_UNIT_ENTITY.get(), pPos, pBlockState);
    }

    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("OutputSignal", this.output);
    }

    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.output = pTag.getInt("OutputSignal");
    }

    public int getOutputSignal() {
        return this.output;
    }

    public void setOutputSignal(int pOutput) {
        this.output = pOutput;
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(int pValue) {
        operation = pValue;
    }

    @Override
    public Component getDisplayName() {
        return new TextComponent("Arithmetic Unit");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new RedstoneArithmeticUnitMenu(pContainerId,pPlayerInventory,this);
    }


}
