package com.fusionflux.gravity_api.util.updatednetworking.packet;

import com.fusionflux.gravity_api.util.Gravity;
import com.fusionflux.gravity_api.util.GravityComponent;
import com.fusionflux.gravity_api.util.networking.ClientboundAndServerboundPacketPayload;
import com.fusionflux.gravity_api.util.updatednetworking.GravityChangerNetworkingConstants;
import com.fusionflux.gravity_api.util.updatednetworking.PacketWithEntityId;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record UpdateGravityPacket(int entityId, Gravity gravity, boolean initialGravity) implements CustomPayload, ClientboundAndServerboundPacketPayload, PacketWithEntityId
{
    public static final Id<UpdateGravityPacket> ID = new Id<>(GravityChangerNetworkingConstants.UPDATE_GRAVITY_ID);

    public static final PacketCodec<RegistryByteBuf, UpdateGravityPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, UpdateGravityPacket::entityId,
            Gravity.STREAM_CODEC, UpdateGravityPacket::gravity,
            PacketCodecs.BOOL, UpdateGravityPacket::initialGravity,
            UpdateGravityPacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId()
    {
        return ID;
    }

    @Override
    public void handleBoth(PlayerEntity player, GravityComponent gc)
    {
        gc.addGravity(gravity, initialGravity);
    }

    @Override
    public int getEntityId()
    {
        return entityId;
    }
}