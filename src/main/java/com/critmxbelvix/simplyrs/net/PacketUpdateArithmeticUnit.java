package com.critmxbelvix.simplyrs.net;

import com.critmxbelvix.simplyrs.common.blocks.entities.ArithmeticUnit.ArithmeticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class PacketUpdateArithmeticUnit extends PacketBase{
    private static final Logger LOGGER = LogManager.getLogger();
    private BlockPos pos;
    private int operand;
    private int current;

    public PacketUpdateArithmeticUnit(BlockPos pos, int operand, int current) {
        super();
        this.pos = pos;
        this.operand = operand;
        this.current = current;
    }

    public PacketUpdateArithmeticUnit() {}

    public static void handle(PacketUpdateArithmeticUnit message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            Level world = player.getCommandSenderWorld();
            BlockEntity be = world.getBlockEntity(message.pos);
            if (be instanceof ArithmeticBlockEntity) {
                ArithmeticBlockEntity arithmeticBE = (ArithmeticBlockEntity) be;
                LOGGER.info("packet process");
                int operands = arithmeticBE.getOperands();
                int operand1 = operands & 0b11;
                int operand2 = operands & 0b1100;
                operand2 = operand2 >> 2;
                int operand3 = operands & 0b1100000;
                operand3 = operand3 >> 4;
                if(message.current == 1){
                    int temp = operand1;
                    if(operand2== message.operand){
                        operand2=operand1;
                    }
                    else if(operand3 == message.operand){
                        operand3=operand1;
                    }
                    operand1 = message.operand;
                }
                else if(message.current == 2){
                    int temp = operand2;
                    if(operand1== message.operand){
                        operand1=operand2;
                    }
                    else if(operand3 == message.operand){
                        operand3=operand2;
                    }
                    operand2 = message.operand;
                }
                else if(message.current == 3){
                    int temp = operand3;
                    if(operand1== message.operand){
                        operand1=operand3;
                    }
                    else if(operand2 == message.operand){
                        LOGGER.info("yes");
                        operand2=operand3;
                    }
                    operand3 = message.operand;
                }
                operands = 0;
                operands += operand1;
                int binary_op2 = operand2 << 2;
                int binary_op3 = operand3 << 4;
                operands += binary_op2;
                operands += binary_op3;
                LOGGER.info(Integer.toBinaryString(operands));
                arithmeticBE.setOperands(operands);
                arithmeticBE.setChanged();
            }
        });
        message.done(ctx);
    }

    public static PacketUpdateArithmeticUnit decode(FriendlyByteBuf buf) {
        PacketUpdateArithmeticUnit p = new PacketUpdateArithmeticUnit();
        CompoundTag tags = buf.readNbt();
        p.pos = new BlockPos(tags.getInt("x"), tags.getInt("y"), tags.getInt("z"));
        p.operand = buf.readInt();
        p.current = buf.readInt();
        return p;
    }

    public static void encode(PacketUpdateArithmeticUnit msg, FriendlyByteBuf buf) {
        CompoundTag tags = new CompoundTag();
        tags.putInt("x", msg.pos.getX());
        tags.putInt("y", msg.pos.getY());
        tags.putInt("z", msg.pos.getZ());
        buf.writeNbt(tags);
        buf.writeInt(msg.operand);
        buf.writeInt(msg.current);
    }
}
