package com.critmxbelvix.simplyrs.common.blocks.entities.JKFlipFlop;

import com.critmxbelvix.simplyrs.common.blocks.TFlipFlop;
import com.critmxbelvix.simplyrs.common.registers.BlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TFlipFlopEntity extends BlockEntity {

    private boolean previous;
    private static final Logger LOGGER = LogManager.getLogger();

    public TFlipFlopEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityRegister.T_FLIPFLOP_ENTITY.get(), pPos, pBlockState);
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

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, TFlipFlopEntity pBlockEntity) {
        LOGGER.info("Ticked");
        boolean current = pState.getValue(TFlipFlop.POWERED);
        boolean clock = pState.getValue(TFlipFlop.INPUT_CLK);
        boolean J_Input = pState.getValue(TFlipFlop.INPUT_1);
        boolean K_Input = pState.getValue(TFlipFlop.INPUT_2);
        if(!pBlockEntity.previous && clock && J_Input){
            pLevel.setBlockAndUpdate(pPos, pState.setValue(TFlipFlop.POWERED,!current));
        }
        pBlockEntity.previous = clock;
    }

}
