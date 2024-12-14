package com.fusionflux.gravity_api.util.networking;

import com.fusionflux.gravity_api.util.GravityComponent;
import com.fusionflux.gravity_api.util.NetworkUtil;
import com.fusionflux.gravity_api.util.packet.GravityPacket;
import com.fusionflux.gravity_api.util.updatednetworking.PacketWithEntityId;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.CustomPayload;

public non-sealed interface ClientboundPacketPayload extends BasePacketPayload {
	/**
	 * Called on the main client thread.
	 * Make sure that implementations are also annotated, or else servers may crash.
	 */

//	@Environment(EnvType.CLIENT)
	void handle(ClientPlayerEntity player, GravityComponent gc);

//	@Environment(EnvType.CLIENT)
	static <T extends ClientboundPacketPayload> void handle(T packet, ClientPlayNetworking.Context ctx) {
		ctx.client().execute(() ->
                {
					if (PacketWithEntityId.class.isAssignableFrom(packet.getClass())){
						NetworkUtil.getGravityComponent(ctx.client().world, ((PacketWithEntityId) packet).getEntityId()).ifPresent(gc -> {
							packet.handle(ctx.player(), gc);
						});
					}
                }
		);
	}

	static void handle(CustomPayload customPayload, ClientPlayNetworking.Context context)
	{
		context.client().execute(() ->
				NetworkUtil.getGravityComponent(context.client().world, ((GravityPacket) customPayload).entityId).ifPresent(gc -> {
					if (customPayload instanceof ClientboundPacketPayload){
						((ClientboundPacketPayload) customPayload).handle(context.player(), gc);
					}
				})
		);
	}
}
