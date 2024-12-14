package com.fusionflux.gravity_api.util.packet;

import com.fusionflux.gravity_api.GravityChangerMod;
import com.fusionflux.gravity_api.api.RotationParameters;
import com.fusionflux.gravity_api.util.GravityComponent;
import com.fusionflux.gravity_api.util.GravityPackets;
import com.fusionflux.gravity_api.util.networking.ClientboundAndServerboundPacketPayload;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.Direction;

public class DefaultGravityPacket extends GravityPacket implements ClientboundAndServerboundPacketPayload {
    public static PacketCodec<ByteBuf, DefaultGravityPacket> STREAM_CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, p -> p.entityId,
            Direction.PACKET_CODEC, p -> p.direction,
            RotationParameters.STREAM_CODEC, DefaultGravityPacket::getRotationParameters,
            PacketCodecs.BOOL, p -> p.initialGravity,
            DefaultGravityPacket::new
    );
    
    public final Direction direction;
    public final RotationParameters rotationParameters;
    public final boolean initialGravity;

    public DefaultGravityPacket(int entityId, Direction direction, RotationParameters rotationParameters, boolean initialGravity) {
        this.entityId = entityId;
        this.direction = direction;
        this.rotationParameters = rotationParameters;
        this.initialGravity = initialGravity;
    }

    @Override
    public void handleBoth(PlayerEntity player, GravityComponent gc) {
        gc.setDefaultGravityDirection(direction, rotationParameters, initialGravity);
    }

    @Override
    public RotationParameters getRotationParameters() {
        return rotationParameters;
    }
    
//    @Override
//    public PacketTypeProvider getTypeProvider() {
//        return GravityPackets.DEFAULT_GRAVITY_PACKET;
//    }

    public static final CustomPayload.Id<DefaultGravityPacket> ID = new CustomPayload.Id<>(GravityChangerMod.asResource("default_gravity_packet"));
    @Override
    public Id<? extends CustomPayload> getId()
    {
        return ID;
    }
}