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
import net.minecraft.util.Uuids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public record DefaultGravityPacket(int entityId, Direction direction, RotationParameters rotationParameters, boolean initialGravity) implements CustomPayload, ClientboundAndServerboundPacketPayload, PacketWithEntityId
{
    public static final Id<DefaultGravityPacket> ID = new Id<>(GravityChangerNetworkingConstants.DEFAULT_GRAVITY_PACKET_ID);

    public static final PacketCodec<RegistryByteBuf, DefaultGravityPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, DefaultGravityPacket::entityId,
            Direction.PACKET_CODEC, DefaultGravityPacket::direction,
            RotationParameters.STREAM_CODEC, DefaultGravityPacket::getRotationParameters,
            PacketCodecs.BOOL, DefaultGravityPacket::initialGravity,
            DefaultGravityPacket::new
    );

    public RotationParameters getRotationParameters() {
        return rotationParameters;
    }

    @Override
    public Id<? extends CustomPayload> getId()
    {
        return ID;
    }

    @Override
    public void handleBoth(PlayerEntity player, GravityComponent gc)
    {
        gc.setDefaultGravityDirection(direction, rotationParameters, initialGravity);
    }

    @Override
    public int getEntityId()
    {
        return entityId;
    }
}