package com.fusionflux.gravity_api.util.updatednetworking.packet;

import com.fusionflux.gravity_api.util.GravityComponent;
import com.fusionflux.gravity_api.util.networking.ClientboundAndServerboundPacketPayload;
import com.fusionflux.gravity_api.util.updatednetworking.GravityChangerNetworkingConstants;
import com.fusionflux.gravity_api.util.updatednetworking.PacketWithEntityId;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record DefaultGravityStrengthPacket(int entityId, double strength) implements CustomPayload, ClientboundAndServerboundPacketPayload, PacketWithEntityId
{
    public static final Id<DefaultGravityStrengthPacket> ID = new Id<>(GravityChangerNetworkingConstants.DEFAULT_GRAVITY_STRENGTH_ID);

    public static final PacketCodec<RegistryByteBuf, DefaultGravityStrengthPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, DefaultGravityStrengthPacket::entityId,
            PacketCodecs.DOUBLE, DefaultGravityStrengthPacket::strength,
            DefaultGravityStrengthPacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId()
    {
        return ID;
    }

    @Override
    public void handleBoth(PlayerEntity player, GravityComponent gc)
    {
        gc.setDefaultGravityStrength(strength);
    }

    @Override
    public int getEntityId()
    {
        return entityId;
    }
}