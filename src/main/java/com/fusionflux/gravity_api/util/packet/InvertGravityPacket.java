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

public class InvertGravityPacket extends GravityPacket implements ClientboundAndServerboundPacketPayload {
    public static PacketCodec<ByteBuf, InvertGravityPacket> STREAM_CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, p -> p.entityId,
            PacketCodecs.BOOL, p -> p.inverted,
            RotationParameters.STREAM_CODEC, p -> p.rotationParameters,
            PacketCodecs.BOOL, p -> p.initialGravity,
            InvertGravityPacket::new
    );
    
    public final boolean inverted;
    public final RotationParameters rotationParameters;
    public final boolean initialGravity;

    public InvertGravityPacket(int entityId, boolean inverted, RotationParameters rotationParameters, boolean initialGravity){
        this.entityId = entityId;
        this.inverted = inverted;
        this.rotationParameters = rotationParameters;
        this.initialGravity = initialGravity;
    }

    @Override
    public void handleBoth(PlayerEntity player, GravityComponent gc) {
        gc.invertGravity(inverted, rotationParameters, initialGravity);
    }

    @Override
    public RotationParameters getRotationParameters() {
        return rotationParameters;
    }

//    @Override
//    public PacketTypeProvider getTypeProvider() {
//        return GravityPackets.INVERT_GRAVITY_PACKET;
//    }

    public static final CustomPayload.Id<InvertGravityPacket> ID = new CustomPayload.Id<>(GravityChangerMod.asResource("invert_gravity_packet"));
    @Override
    public Id<? extends CustomPayload> getId()
    {
        return ID;
    }
}
