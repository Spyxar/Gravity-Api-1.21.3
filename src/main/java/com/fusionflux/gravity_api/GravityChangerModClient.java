package com.fusionflux.gravity_api;

import com.fusionflux.gravity_api.util.networking.ClientboundPacketPayload;
import com.fusionflux.gravity_api.util.updatednetworking.packet.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class GravityChangerModClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        ClientPlayNetworking.registerGlobalReceiver(DefaultGravityPacket.ID, ClientboundPacketPayload::handle);
        ClientPlayNetworking.registerGlobalReceiver(DefaultGravityStrengthPacket.ID, ClientboundPacketPayload::handle);
        ClientPlayNetworking.registerGlobalReceiver(InvertGravityPacket.ID, ClientboundPacketPayload::handle);
        ClientPlayNetworking.registerGlobalReceiver(OverwriteGravityPacket.ID, ClientboundPacketPayload::handle);
        ClientPlayNetworking.registerGlobalReceiver(UpdateGravityPacket.ID, ClientboundPacketPayload::handle);
    }
}