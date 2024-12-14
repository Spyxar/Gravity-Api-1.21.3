package com.fusionflux.gravity_api.util;

import com.fusionflux.gravity_api.GravityChangerMod;
import com.fusionflux.gravity_api.util.networking.BasePacketPayload;
import com.fusionflux.gravity_api.util.networking.ClientboundPacketPayload;
import com.fusionflux.gravity_api.util.networking.ServerboundPacketPayload;
import com.fusionflux.gravity_api.util.packet.DefaultGravityPacket;
import com.fusionflux.gravity_api.util.packet.DefaultGravityStrengthPacket;
import com.fusionflux.gravity_api.util.packet.InvertGravityPacket;
import com.fusionflux.gravity_api.util.packet.OverwriteGravityPacket;
import com.fusionflux.gravity_api.util.packet.UpdateGravityPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

import java.util.Locale;

//public enum GravityPackets implements BasePacketPayload.PacketTypeProvider {
public enum GravityPackets {
    // Client to Server & Server to Client
    DEFAULT_GRAVITY_PACKET(DefaultGravityPacket.class, DefaultGravityPacket.STREAM_CODEC),
    DEFAULT_GRAVITY_STRENGTH_PACKET(DefaultGravityStrengthPacket.class, DefaultGravityStrengthPacket.STREAM_CODEC),
    INVERT_GRAVITY_PACKET(InvertGravityPacket.class, InvertGravityPacket.STREAM_CODEC),
    OVERWRITE_GRAVITY_PACKET(OverwriteGravityPacket.class, OverwriteGravityPacket.STREAM_CODEC),
    UPDATE_GRAVITY_PACKET(UpdateGravityPacket.class, UpdateGravityPacket.STREAM_CODEC)
    ;
    
    private final PacketType<?> type;

    <T extends BasePacketPayload> GravityPackets(Class<T> clazz, PacketCodec<? super RegistryByteBuf, T> codec) {
        String name = this.name().toLowerCase(Locale.ROOT);
//        this.type = new PacketType<>(
//                new CustomPayload.Type<>(GravityChangerMod.asResource(name)),
//                clazz, codec
//        );
        this.type = null;
    }

//    @Override
//    @SuppressWarnings("unchecked")
//    public <T extends CustomPayload> CustomPayload.Type<T> getType() {
//        return (CustomPayload.Type<T>) this.type.type;
//    }

    public static void registerPackets() {
        for (GravityPackets packet : values()) {
            packet.type.register();
        }
    }

//    private record PacketType<T extends BasePacketPayload>(CustomPayload.Type<T> type, Class<T> clazz, PacketCodec<? super RegistryByteBuf, T> codec) {
    private record PacketType<T extends BasePacketPayload>( Class<T> clazz, PacketCodec<? super RegistryByteBuf, T> codec) {
        @SuppressWarnings("unchecked")
        public void register() {
//            boolean clientbound = ClientboundPacketPayload.class.isAssignableFrom(this.clazz);
//            boolean serverbound = ServerboundPacketPayload.class.isAssignableFrom(this.clazz);
//            if (clientbound) {
//                PacketType<ClientboundPacketPayload> casted = (PacketType<ClientboundPacketPayload>) this;
//                PayloadTypeRegistry.playS2C().register(casted.type.id(), casted.codec);
//                ClientPlayNetworking.registerGlobalReceiver(casted.type.id(), ClientboundPacketPayload::handle);
//            }
//            if (serverbound) {
//                PacketType<ServerboundPacketPayload> casted = (PacketType<ServerboundPacketPayload>) this;
//                PayloadTypeRegistry.playC2S().register(casted.type.id(), casted.codec);
//                ServerPlayNetworking.registerGlobalReceiver(casted.type.id(), ServerboundPacketPayload::handle);
//            }
        }
    }
}

