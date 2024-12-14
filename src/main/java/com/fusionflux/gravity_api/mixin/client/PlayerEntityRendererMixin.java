package com.fusionflux.gravity_api.mixin.client;

import com.fusionflux.gravity_api.api.GravityChangerAPI;
import com.fusionflux.gravity_api.util.RotationUtil;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin {

    //ToDo: Check, Important
//    @ModifyVariable(
//            method = "setupTransforms(Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;FF)V",
//            at = @At(
//                    value = "STORE",
//                    target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;getRotationVec(F)Lnet/minecraft/util/math/Vec3d;",
//                    ordinal = 0
//            ),
//            ordinal = 0
//    )
//    private Vec3d modify_setupTransforms_Vec3d_0(Vec3d vec3d, AbstractClientPlayerEntity abstractClientPlayerEntity, MatrixStack matrixStack, float f, float g, float h) {
//        Direction gravityDirection = GravityChangerAPI.getGravityDirection(abstractClientPlayerEntity);
//        if(gravityDirection == Direction.DOWN) {
//            return vec3d;
//        }
//
//        return RotationUtil.vecWorldToPlayer(vec3d, gravityDirection);
//    }
}
