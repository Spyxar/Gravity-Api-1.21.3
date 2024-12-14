package com.fusionflux.gravity_api.util.updatednetworking;

import com.fusionflux.gravity_api.GravityChangerMod;
import net.minecraft.util.Identifier;

public class GravityChangerNetworkingConstants
{
    public static final Identifier DEFAULT_GRAVITY_PACKET_ID = Identifier.of(GravityChangerMod.MOD_ID, "default_gravity");
    public static final Identifier DEFAULT_GRAVITY_STRENGTH_ID = Identifier.of(GravityChangerMod.MOD_ID, "default_gravity_strength");
    public static final Identifier INVERT_GRAVITY_ID = Identifier.of(GravityChangerMod.MOD_ID, "invert_gravity");
    public static final Identifier OVERWRITE_GRAVITY_ID = Identifier.of(GravityChangerMod.MOD_ID, "overwrite_gravity");
    public static final Identifier UPDATE_GRAVITY_ID = Identifier.of(GravityChangerMod.MOD_ID, "update_gravity");
}