package com.fusionflux.gravity_api.util.updatednetworking.packet;

import com.fusionflux.gravity_api.api.RotationParameters;
import com.fusionflux.gravity_api.util.GravityComponent;
import com.fusionflux.gravity_api.util.networking.ClientboundAndServerboundPacketPayload;
import com.fusionflux.gravity_api.util.updatednetworking.GravityChangerNetworkingConstants;
import com.fusionflux.gravity_api.util.updatednetworking.PacketWithEntityId;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.Direction;

public record InvertGravityPacket(int entityId, boolean inverted, RotationParameters rotationParameters, boolean initialGravity) implements CustomPayload, ClientboundAndServerboundPacketPayload, PacketWithEntityId
{
    public static final Id<InvertGravityPacket> ID = new Id<>(GravityChangerNetworkingConstants.INVERT_GRAVITY_ID);

    public static final PacketCodec<RegistryByteBuf, InvertGravityPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, InvertGravityPacket::entityId,
            PacketCodecs.BOOL, InvertGravityPacket::inverted,
            RotationParameters.STREAM_CODEC, InvertGravityPacket::rotationParameters,
            PacketCodecs.BOOL, InvertGravityPacket::initialGravity,
            InvertGravityPacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId()
    {
        return ID;
    }

    @Override
    public void handleBoth(PlayerEntity player, GravityComponent gc)
    {
        gc.invertGravity(inverted, rotationParameters, initialGravity);
    }

    @Override
    public int getEntityId()
    {
        return entityId;
    }
}