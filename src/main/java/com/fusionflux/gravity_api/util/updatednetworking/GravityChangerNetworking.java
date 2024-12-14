package com.fusionflux.gravity_api.util.updatednetworking;

import com.fusionflux.gravity_api.util.networking.ServerboundPacketPayload;
import com.fusionflux.gravity_api.util.updatednetworking.packet.*;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class GravityChangerNetworking
{
    public static void init()
    {
        //Currently, all the packets are both client and serverbound
        PayloadTypeRegistry.playS2C().register(DefaultGravityPacket.ID, DefaultGravityPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(DefaultGravityPacket.ID, DefaultGravityPacket.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(DefaultGravityPacket.ID, ServerboundPacketPayload::handle);

        PayloadTypeRegistry.playS2C().register(DefaultGravityStrengthPacket.ID, DefaultGravityStrengthPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(DefaultGravityStrengthPacket.ID, DefaultGravityStrengthPacket.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(DefaultGravityStrengthPacket.ID, ServerboundPacketPayload::handle);

        PayloadTypeRegistry.playS2C().register(InvertGravityPacket.ID, InvertGravityPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(InvertGravityPacket.ID, InvertGravityPacket.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(InvertGravityPacket.ID, ServerboundPacketPayload::handle);

        PayloadTypeRegistry.playS2C().register(OverwriteGravityPacket.ID, OverwriteGravityPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(OverwriteGravityPacket.ID, OverwriteGravityPacket.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(OverwriteGravityPacket.ID, ServerboundPacketPayload::handle);

        PayloadTypeRegistry.playS2C().register(UpdateGravityPacket.ID, UpdateGravityPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(UpdateGravityPacket.ID, UpdateGravityPacket.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(UpdateGravityPacket.ID, ServerboundPacketPayload::handle);
    }
}
