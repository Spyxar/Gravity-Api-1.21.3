package com.fusionflux.gravity_api.util.networking;

import com.fusionflux.gravity_api.util.GravityComponent;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public interface ClientboundAndServerboundPacketPayload extends ClientboundPacketPayload, ServerboundPacketPayload {
    @Override
    default void handle(ClientPlayerEntity player, GravityComponent gc) {
        handleBoth(player, gc);
    }

    @Override
    default void handle(ServerPlayerEntity player, GravityComponent gc) {
        handleBoth(player, gc);
    }
    
    void handleBoth(PlayerEntity player, GravityComponent gc);
}
