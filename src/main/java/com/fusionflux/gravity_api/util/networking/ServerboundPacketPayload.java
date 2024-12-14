package com.fusionflux.gravity_api.util.networking;

import com.fusionflux.gravity_api.util.GravityComponent;
import com.fusionflux.gravity_api.util.GravityPacketSender;
import com.fusionflux.gravity_api.util.NetworkUtil;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;

public non-sealed interface ServerboundPacketPayload extends BasePacketPayload {
	/**
	 * Called on the main client thread.
	 */
	void handle(ServerPlayerEntity player, GravityComponent gc);

	static <T extends ServerboundPacketPayload> void handle(T packet, ServerPlayNetworking.Context ctx) {
		ServerPlayerEntity player = ctx.player();
		ctx.server().execute(() -> 
			NetworkUtil.getGravityComponent(player).ifPresent(gc -> {
				packet.handle(player, gc);
				GravityPacketSender.sendToAllExceptSelf(ctx.server(), player, packet);
			})
		);
	}

	static void handle(CustomPayload customPayload, ServerPlayNetworking.Context context)
	{
		ServerPlayerEntity player = context.player();
		context.server().execute(() ->
				NetworkUtil.getGravityComponent(player).ifPresent(gc -> {
					if (customPayload instanceof ServerboundPacketPayload) {
						((ServerboundPacketPayload) customPayload).handle(player, gc);
						GravityPacketSender.sendToAllExceptSelf(context.server(), player, customPayload);
					}
				})
		);
	}
}
