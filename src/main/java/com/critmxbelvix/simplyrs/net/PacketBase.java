package com.critmxbelvix.simplyrs.net;

import net.minecraftforge.network.NetworkEvent.Context;
import java.util.function.Supplier;

public class PacketBase {
    public void done(Supplier<Context> ctx) {
        ctx.get().setPacketHandled(true);
    }
}
