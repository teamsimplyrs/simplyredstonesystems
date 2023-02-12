package com.critmxbelvix.simplyrs.common.blocks.entities.FlipFlops;

import com.critmxbelvix.simplyrs.common.blocks.DFlipFlop;
import com.critmxbelvix.simplyrs.common.blocks.TFlipFlop;
import com.critmxbelvix.simplyrs.common.registers.BlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DFlipFlopEntity extends BlockEntity {

    private boolean previous;
    private static final Logger LOGGER = LogManager.getLogger();

    public DFlipFlopEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityRegister.D_FLIPFLOP_ENTITY.get(), pPos, pBlockState);
        this.previous = pBlockState.getValue(TFlipFlop.INPUT_CLK);
    }

    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putBoolean("PreviousSignal", this.previous);
    }

    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.previous = pTag.getBoolean("PreviousSignal");
    }

    public boolean getOutputSignal() {
        return this.previous;
    }

    public void setOutputSignal(boolean pPrev) {
        this.previous = pPrev;
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, DFlipFlopEntity pBlockEntity) {
        boolean current = pState.getValue(DFlipFlop.POWERED);
        boolean clock = pState.getValue(DFlipFlop.INPUT_CLK);
        boolean J_Input = pState.getValue(DFlipFlop.INPUT_1);
        if(!pBlockEntity.previous && clock){
            pLevel.setBlockAndUpdate(pPos, pState.setValue(DFlipFlop.POWERED,J_Input));
        }
        pBlockEntity.previous = clock;
    }

}
