package com.critmxbelvix.simplyrs.net;

import com.critmxbelvix.simplyrs.common.blocks.entities.RedstoneClockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketUpdateClock extends PacketBase{
    private BlockPos pos;
    private int change;
    //Delay is 0, Duration is 1
    private int type;

    public PacketUpdateClock(BlockPos pos, int change, int type) {
        super();
        this.pos = pos;
        this.change = change;
        this.type = type;
    }

    public PacketUpdateClock() {}

    public static void handle(PacketUpdateClock message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            Level world = player.getCommandSenderWorld();
            BlockEntity be = world.getBlockEntity(message.pos);
            if (be instanceof RedstoneClockEntity) {
                RedstoneClockEntity clockBE = (RedstoneClockEntity) be;
                if(message.type==0) {
                    if(clockBE.delay+message.change < 1){
                        clockBE.delay=1;
                    }
                    else{
                        clockBE.delay += message.change;
                    }
                }
                else if(message.type==1){
                    if(clockBE.duration+message.change < 1){
                        clockBE.duration=1;
                    }
                    else{
                        clockBE.duration += message.change;
                    }
                }
                clockBE.setChanged();
            }
        });
        message.done(ctx);
    }

    public static PacketUpdateClock decode(FriendlyByteBuf buf) {
        PacketUpdateClock p = new PacketUpdateClock();
        CompoundTag tags = buf.readNbt();
        p.pos = new BlockPos(tags.getInt("x"), tags.getInt("y"), tags.getInt("z"));
        p.change = buf.readInt();
        p.type = buf.readInt();
        return p;
    }

    public static void encode(PacketUpdateClock msg, FriendlyByteBuf buf) {
        CompoundTag tags = new CompoundTag();
        tags.putInt("x", msg.pos.getX());
        tags.putInt("y", msg.pos.getY());
        tags.putInt("z", msg.pos.getZ());
        buf.writeNbt(tags);
        buf.writeInt(msg.change);
        buf.writeInt(msg.type);
    }
}
