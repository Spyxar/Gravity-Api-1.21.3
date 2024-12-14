package com.fusionflux.gravity_api.util.networking;

import net.minecraft.network.packet.CustomPayload;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public sealed interface BasePacketPayload extends CustomPayload permits ClientboundPacketPayload, ServerboundPacketPayload {
//	PacketTypeProvider getTypeProvider();
	
//	@Override
//	@ApiStatus.NonExtendable
//	default @NotNull Type<? extends CustomPayload> type() {
//		return this.getTypeProvider().getType();
//	}

//	interface PacketTypeProvider {
//		<T extends CustomPayload> Type<T> getType();
//	}
}
