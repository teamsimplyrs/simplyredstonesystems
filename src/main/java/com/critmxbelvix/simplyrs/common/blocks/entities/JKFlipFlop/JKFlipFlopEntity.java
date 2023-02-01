package com.critmxbelvix.simplyrs.common.blocks.entities.JKFlipFlop;

import com.critmxbelvix.simplyrs.common.blocks.FlipFlopBlock;
import com.critmxbelvix.simplyrs.common.registers.BlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JKFlipFlopEntity extends BlockEntity {
    private boolean previous;
    private static final Logger LOGGER = LogManager.getLogger();

    public JKFlipFlopEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityRegister.JK_FLIPFLOP_ENTITY.get(), pPos, pBlockState);
        this.previous = pBlockState.getValue(FlipFlopBlock.INPUT_CLK);
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

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, JKFlipFlopEntity pBlockEntity) {
        LOGGER.info("Ticked");
        boolean current = pState.getValue(FlipFlopBlock.POWERED);
        boolean clock = pState.getValue(FlipFlopBlock.INPUT_CLK);
        boolean J_Input = pState.getValue(FlipFlopBlock.INPUT_1);
        boolean K_Input = pState.getValue(FlipFlopBlock.INPUT_2);
        if(!pBlockEntity.previous && clock){
            if(J_Input && !K_Input && !current){
                LOGGER.info("first");
                current = true;
            }
            else if(K_Input && !J_Input && current){
                current = false;
                LOGGER.info("second");
            }
            else if(J_Input && K_Input){
                if(current){
                    current = false;
                    LOGGER.info("third");
                }
                else{
                    current = true;
                    LOGGER.info("fourth");
                }
            }
            pLevel.setBlockAndUpdate(pPos, pState.setValue(FlipFlopBlock.POWERED,current));
        }
        pBlockEntity.previous = clock;
    }

}
