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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

public class ArithmeticBlockEntity extends BlockEntity implements MenuProvider {

    private static final Logger LOGGER = LogManager.getLogger();
    private int output;
    private int operand1;
    private int operand2;
    private int operand3;
    private int operands;

    public ArithmeticBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityRegister.REDSTONE_ARITHMETIC_UNIT_ENTITY.get(), pPos, pBlockState);
        operand1=0;
        operand2=1;
        operand3=2;
        this.operands = this.getOperands();
    }

    protected void saveAdditional(CompoundTag pTag) {
        pTag.putInt("OutputSignal", this.output);
        super.saveAdditional(pTag);
    }

    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.output = pTag.getInt("OutputSignal");
    }

//    @Nullable
//    @Override
//    public Packet<ClientGamePacketListener> getUpdatePacket() {
//        return ClientboundBlockEntityDataPacket.create(this);
//    }
//
//    @Override
//    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
//        this.setOperands(pkt.getTag().getInt("operands"));
//    }
//
//    @Override
//    public CompoundTag getUpdateTag() {
//        CompoundTag tag = new CompoundTag();
//        tag.putInt("operands",this.getOperands());
//        return tag;
//    }

    public int getOutputSignal() {
        return this.output;
    }

    public void setOutputSignal(int pOutput) {
        this.output = pOutput;
    }

    @Override
    public Component getDisplayName() {
        return new TextComponent("Arithmetic Unit");
    }

    public int getOperands(){
        int operands = 0;
        operands += operand1;
        int binary_op2 = operand2 << 2;
        int binary_op3 = operand3 << 4;
        operands += binary_op2;
        operands += binary_op3;
        LOGGER.info(Integer.toBinaryString(operands));
        LOGGER.info(operand1 + " " + operand2 + " " +operand3);
        return operands;
    }

    public void setOperands(int operands){
        operand1 = operands & 0b11;
        operand2 = operands & 0b1100;
        operand2 = operand2 >> 2;
        operand3 = operands & 0b110000;
        operand3 = operand3 >> 4;
        LOGGER.info(operand1 + " " + operand2 + " " +operand3);
    }


    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new RedstoneArithmeticUnitMenu(pContainerId,pPlayerInventory,this);
    }


}
