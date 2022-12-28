package com.critmxbelvix.simplyrs.net;

import com.critmxbelvix.simplyrs.client.screen.RedstoneClockScreen;
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
    private int increase;

    public PacketUpdateClock(RedstoneClockScreen screen, BlockPos pos, int increase) {
        super();
        this.pos = pos;
        this.increase = increase;
    }

    public PacketUpdateClock() {}

    public static void handle(PacketUpdateClock message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            Level world = player.getCommandSenderWorld();
            BlockEntity be = world.getBlockEntity(message.pos);
            if (be instanceof RedstoneClockEntity) {
                RedstoneClockEntity clockBE = (RedstoneClockEntity) be;
                clockBE.delay += message.increase;
                clockBE.setChanged();
            }
        });
        message.done(ctx);
    }

    public static PacketUpdateClock decode(FriendlyByteBuf buf) {
        PacketUpdateClock p = new PacketUpdateClock();
        CompoundTag tags = buf.readNbt();
        p.pos = new BlockPos(tags.getInt("x"), tags.getInt("y"), tags.getInt("z"));
        p.increase = buf.readInt();
        return p;
    }

    public static void encode(PacketUpdateClock msg, FriendlyByteBuf buf) {
        CompoundTag tags = new CompoundTag();
        tags.putInt("x", msg.pos.getX());
        tags.putInt("y", msg.pos.getY());
        tags.putInt("z", msg.pos.getZ());
        buf.writeNbt(tags);
        buf.writeInt(msg.increase);
    }
}
