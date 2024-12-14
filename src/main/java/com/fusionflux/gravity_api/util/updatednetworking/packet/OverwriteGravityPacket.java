package com.fusionflux.gravity_api.util.updatednetworking.packet;

import com.fusionflux.gravity_api.api.RotationParameters;
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
import net.minecraft.util.math.Direction;

import java.util.List;

public record OverwriteGravityPacket(int entityId, List<Gravity> gravityList, boolean initialGravity) implements CustomPayload, ClientboundAndServerboundPacketPayload, PacketWithEntityId
{
    public static final Id<OverwriteGravityPacket> ID = new Id<>(GravityChangerNetworkingConstants.OVERWRITE_GRAVITY_ID);

    public static final PacketCodec<RegistryByteBuf, OverwriteGravityPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, OverwriteGravityPacket::entityId,
            Gravity.STREAM_CODEC.collect(PacketCodecs.toList()), OverwriteGravityPacket::gravityList,
            PacketCodecs.BOOL, OverwriteGravityPacket::initialGravity,
            OverwriteGravityPacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId()
    {
        return ID;
    }

    @Override
    public void handleBoth(PlayerEntity player, GravityComponent gc)
    {
        gc.setGravity(gravityList, initialGravity);
    }

    @Override
    public int getEntityId()
    {
        return entityId;
    }
}