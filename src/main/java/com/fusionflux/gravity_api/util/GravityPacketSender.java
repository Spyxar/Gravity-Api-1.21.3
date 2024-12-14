package com.fusionflux.gravity_api.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class GravityPacketSender {
//    @Environment(EnvType.CLIENT)
    public static void sendToServer(CustomPayload payload) {
        ClientPlayNetworking.send(payload);
    }

    public static void sendToClient(ServerPlayerEntity player, CustomPayload payload) {
        ServerPlayNetworking.send(player, payload);
    }

    public static void sendToClients(Iterable<ServerPlayerEntity> players, CustomPayload payload) {
        for (ServerPlayerEntity player : players) {
            sendToClient(player, payload);
        }
    }

    public static void sendToAllClients(MinecraftServer server, CustomPayload payload) {
        Packet<?> packet = ServerPlayNetworking.createS2CPacket(payload);
        server.getPlayerManager().sendToAll(packet);
    }
    
    public static void sendToAllExceptSelf(MinecraftServer server, ServerPlayerEntity self, CustomPayload payload) {
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            if (player != self) {
                sendToClient(player, payload);
            }
        }
    }

    public static void sendToClientsTrackingAndSelf(Entity entity, CustomPayload payload) {
        Packet<?> packet = ServerPlayNetworking.createS2CPacket(payload);
        if (entity.getWorld().getChunkManager() instanceof ServerChunkManager chunkCache) {
            chunkCache.sendToNearbyPlayers(entity, packet);
        } else {
            throw new IllegalStateException("Cannot send clientbound payloads on the client");
        }
    }

    public static void sendToClientsTrackingEntity(Entity entity, CustomPayload payload) {
        Packet<?> packet = ServerPlayNetworking.createS2CPacket(payload);
        if (entity.getWorld().getChunkManager() instanceof ServerChunkManager chunkCache) {
            chunkCache.sendToOtherNearbyPlayers(entity, packet);
        } else {
            throw new IllegalStateException("Cannot send clientbound payloads on the client");
        }
    }

    public static void sendToClientsTrackingChunk(ServerWorld serverLevel, ChunkPos chunk, CustomPayload payload) {
        for (ServerPlayerEntity player : serverLevel.getChunkManager().chunkLoadingManager.getPlayersWatchingChunk(chunk, false)) {
            sendToClient(player, payload);
        }
    }

    public static void sendToClientsAround(ServerWorld serverLevel, Vec3d pos, double radius, CustomPayload payload) {
        Packet<?> packet = ServerPlayNetworking.createS2CPacket(payload);
        //ToDo: Check
        serverLevel.getServer().getPlayerManager().sendToAround(null, pos.x, pos.y, pos.z, radius, serverLevel.getRegistryKey(), packet);
    }

    public static void sendToClientsAround(ServerWorld serverLevel, Vec3i pos, double radius, CustomPayload payload) {
        sendToClientsAround(serverLevel, new Vec3d(pos.getX(), pos.getY(), pos.getZ()), radius, payload);
    }
}