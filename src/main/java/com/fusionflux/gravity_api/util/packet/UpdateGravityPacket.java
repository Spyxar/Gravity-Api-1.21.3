package com.fusionflux.gravity_api.util.packet;

import com.fusionflux.gravity_api.GravityChangerMod;
import com.fusionflux.gravity_api.api.RotationParameters;
import com.fusionflux.gravity_api.util.Gravity;
import com.fusionflux.gravity_api.util.GravityComponent;
import com.fusionflux.gravity_api.util.GravityPackets;
import com.fusionflux.gravity_api.util.networking.ClientboundAndServerboundPacketPayload;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public class UpdateGravityPacket extends GravityPacket implements ClientboundAndServerboundPacketPayload {
    public static PacketCodec<ByteBuf, UpdateGravityPacket> STREAM_CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, p -> p.entityId,
            Gravity.STREAM_CODEC, p -> p.gravity,
            PacketCodecs.BOOL, p -> p.initialGravity,
            UpdateGravityPacket::new
    );
    
    public final Gravity gravity;
    public final boolean initialGravity;

    public UpdateGravityPacket(int entityId, Gravity gravity, boolean initialGravity) {
        this.entityId= entityId;
        this.gravity =  gravity;
        this.initialGravity = initialGravity;
    }

    @Override
    public void handleBoth(PlayerEntity player, GravityComponent gc) {
        gc.addGravity(gravity, initialGravity);
    }

    @Override
    public RotationParameters getRotationParameters() {
        return gravity.rotationParameters();
    }

//    @Override
//    public PacketTypeProvider getTypeProvider() {
//        return GravityPackets.UPDATE_GRAVITY_PACKET;
//    }

    public static final CustomPayload.Id<UpdateGravityPacket> ID = new CustomPayload.Id<>(GravityChangerMod.asResource("update_gravity_packet"));
    @Override
    public Id<? extends CustomPayload> getId()
    {
        return ID;
    }
}
