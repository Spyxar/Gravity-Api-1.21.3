package com.fusionflux.gravity_api.util;

import com.fusionflux.gravity_api.GravityChangerMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class EntityTags {
    public static final TagKey<EntityType<?>> FORBIDDEN_ENTITIES = TagKey.of(RegistryKeys.ENTITY_TYPE, GravityChangerMod.asResource("forbidden_entities"));
    public static final TagKey<EntityType<?>> FORBIDDEN_ENTITY_RENDERING = TagKey.of(RegistryKeys.ENTITY_TYPE, GravityChangerMod.asResource("forbidden_entity_rendering"));

    public static boolean canChangeGravity(Entity entity) {
            return !entity.getType().isIn(FORBIDDEN_ENTITIES);
    }
}
