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

public class DefaultGravityStrengthPacket extends GravityPacket implements ClientboundAndServerboundPacketPayload {
    public static PacketCodec<ByteBuf, DefaultGravityStrengthPacket> STREAM_CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, p -> p.entityId,
            PacketCodecs.DOUBLE, p -> p.strength,
            DefaultGravityStrengthPacket::new
    );
    
    public final double strength;

    public DefaultGravityStrengthPacket(int entityId, double strength) {
        this.entityId = entityId;
        this.strength = strength;
    }

    @Override
    public void handleBoth(PlayerEntity player, GravityComponent gc) {
        gc.setDefaultGravityStrength(strength);
    }

    @Override
    public RotationParameters getRotationParameters() {
        return null;
    }

//    @Override
//    public PacketTypeProvider getTypeProvider() {
//        return GravityPackets.DEFAULT_GRAVITY_STRENGTH_PACKET;
//    }

    public static final CustomPayload.Id<DefaultGravityStrengthPacket> ID = new CustomPayload.Id<>(GravityChangerMod.asResource("default_gravity_strength_packet"));
    @Override
    public Id<? extends CustomPayload> getId()
    {
        return ID;
    }
}