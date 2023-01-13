package com.critmxbelvix.simplyrs.common.blocks.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ArithmeticBlockEntity extends BlockEntity {
    private int output;

    public ArithmeticBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityType.COMPARATOR, pPos, pBlockState);
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
}
