package com.fusionflux.gravity_api.mixin;

import com.fusionflux.gravity_api.api.GravityChangerAPI;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(AbstractMinecartEntity.class)
public abstract class AbstractMinecartMixin extends Entity
{
    public AbstractMinecartMixin(EntityType<?> type, World level) {
        super(type, level);
    }

    //ToDo: Check
//    @ModifyArg(
//        method = "tick",
//        at = @At(
//            value = "INVOKE",
//            target = "Lnet/minecraft/util/math/Vec3d;add(DDD)Lnet/minecraft/util/math/Vec3d;"
//        ),
//        index = 1
//    )
//    private double multiplyGravity(double x) {
//        return x * GravityChangerAPI.getGravityStrength(this);
//    }
}
