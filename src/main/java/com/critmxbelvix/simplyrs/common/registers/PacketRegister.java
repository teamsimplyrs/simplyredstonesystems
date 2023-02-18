package com.critmxbelvix.simplyrs.common.registers;

import com.critmxbelvix.simplyrs.SimplyRedstoneSystems;
import com.critmxbelvix.simplyrs.net.PacketUpdateArithmeticUnit;
import com.critmxbelvix.simplyrs.net.PacketUpdateClock;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketRegister {
    private static final String PROTOCOL_VERSION = Integer.toString(1);
    public static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(SimplyRedstoneSystems.MOD_ID, "main_channel"))
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .simpleChannel();

    public static void setup() {
        int id=0;
        INSTANCE.registerMessage(id++, PacketUpdateClock.class, PacketUpdateClock::encode, PacketUpdateClock::decode,
                PacketUpdateClock::handle);
        INSTANCE.registerMessage(id++, PacketUpdateArithmeticUnit.class, PacketUpdateArithmeticUnit::encode, PacketUpdateArithmeticUnit::decode,
                PacketUpdateArithmeticUnit::handle);
    }

}
