package com.fusionflux.gravity_api.mixin.client;

import com.fusionflux.gravity_api.RotationAnimation;
import com.fusionflux.gravity_api.api.GravityChangerAPI;
import com.fusionflux.gravity_api.util.QuaternionUtil;
import com.fusionflux.gravity_api.util.RotationUtil;
import com.fusionflux.gravity_api.util.EntityTags;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin {
    @Shadow @Final private static RenderLayer SHADOW_LAYER;

    @Shadow private boolean renderShadows;

    //ToDo: Check
//    @Shadow private static void drawShadowVertex(MatrixStack.Entry entry, VertexConsumer vertices, float alpha, float x, float y, float z, float u, float v) {}
    @Shadow private static void drawShadowVertex(MatrixStack.Entry entry, VertexConsumer vertices, int i, float x, float y, float z, float u, float v) {}

    @Inject(
            method = "render(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/EntityRenderer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/util/math/MatrixStack;translate(DDD)V",
                    ordinal = 0,
                    shift = At.Shift.AFTER
            )
    )
    private <E extends Entity, S extends EntityRenderState> void inject_render_0(E entity, double x, double y, double z, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, EntityRenderer<? super E, S> renderer, CallbackInfo ci) {
        if(!(entity instanceof ProjectileEntity) && !(entity instanceof ExperienceOrbEntity) && !entity.getType().isIn(EntityTags.FORBIDDEN_ENTITY_RENDERING)) {
            Direction gravityDirection = GravityChangerAPI.getGravityDirection(entity);
            if (!this.renderShadows) return;

            matrices.push();
            Optional<RotationAnimation> animationOptional = GravityChangerAPI.getGravityAnimation(entity);
            if(animationOptional.isEmpty()) return;
            RotationAnimation animation = animationOptional.get();
            long timeMs = entity.getWorld().getTime()*50+(long)(tickDelta*50);
            matrices.multiply(QuaternionUtil.inversed(animation.getCurrentGravityRotation(gravityDirection, timeMs)));


            matrices.pop();
        }
    }

    @Inject(
            method = "render(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/EntityRenderer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/util/math/MatrixStack;translate(DDD)V",
                    ordinal = 1
            )
    )
    private <E extends Entity, S extends EntityRenderState> void inject_render_1(E entity, double x, double y, double z, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, EntityRenderer<? super E, S> renderer, CallbackInfo ci) {
        if(!(entity instanceof ProjectileEntity) && !(entity instanceof ExperienceOrbEntity) && !entity.getType().isIn(EntityTags.FORBIDDEN_ENTITY_RENDERING)) {
            Direction gravityDirection = GravityChangerAPI.getGravityDirection(entity);
            if (!this.renderShadows) return;

            //ToDo: Check
//            matrices.pop();
        }
    }

    @Inject(
            method = "render(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/EntityRenderer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/util/math/MatrixStack;translate(DDD)V",
                    //ToDo: Check
                    ordinal = 2,
                    shift = At.Shift.AFTER
            )
    )
    private <E extends Entity, S extends EntityRenderState> void inject_render_2(E entity, double x, double y, double z, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, EntityRenderer<? super E, S> renderer, CallbackInfo ci) {
        if(!(entity instanceof ProjectileEntity) && !(entity instanceof ExperienceOrbEntity) && !entity.getType().isIn(EntityTags.FORBIDDEN_ENTITY_RENDERING)) {
            Direction gravityDirection = GravityChangerAPI.getGravityDirection(entity);
            if (gravityDirection == Direction.DOWN) return;
            if (!this.renderShadows) return;

            matrices.multiply(RotationUtil.getCameraRotationQuaternion(gravityDirection));
        }
    }

    @Inject(
            method = "renderShadow",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void inject_renderShadow(MatrixStack matrices, VertexConsumerProvider vertexConsumers, EntityRenderState renderState, float opacity, float tickDelta, WorldView world, float radius, CallbackInfo ci) {
        //ToDo:
//        Direction gravityDirection = GravityChangerAPI.getGravityDirection(entity);
        Direction gravityDirection = Direction.DOWN;
//        if(gravityDirection == Direction.DOWN) return;
        if (true) {
            return;
        }

        ci.cancel();

        //ToDo: Check (params were lastrenderX,Y,Z and entity.getX,Y,Z)
        double x = MathHelper.lerp(tickDelta, renderState.x, renderState.x);
        double y = MathHelper.lerp(tickDelta, renderState.y, renderState.y);
        double z = MathHelper.lerp(tickDelta, renderState.z, renderState.z);
        Vec3d minShadowPos = RotationUtil.vecPlayerToWorld((double) -radius, (double) -radius, (double) -radius, gravityDirection).add(x, y, z);
        Vec3d maxShadowPos = RotationUtil.vecPlayerToWorld((double) radius, 0.0D, (double) radius, gravityDirection).add(x, y, z);
        MatrixStack.Entry entry = matrices.peek();
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(SHADOW_LAYER);

        for(BlockPos blockPos : BlockPos.iterate(new BlockPos((int)minShadowPos.x,(int)minShadowPos.y,(int)minShadowPos.z), new BlockPos((int)maxShadowPos.x,(int)maxShadowPos.y,(int)maxShadowPos.z))) {
            gravitychanger$renderShadowPartPlayer(entry, vertexConsumer, world, blockPos, x, y, z, radius, opacity, gravityDirection);
        }
    }

    private static void gravitychanger$renderShadowPartPlayer(MatrixStack.Entry entry, VertexConsumer vertices, WorldView world, BlockPos pos, double x, double y, double z, float radius, float opacity, Direction gravityDirection) {
        BlockPos posBelow = pos.offset(gravityDirection);
        BlockState blockStateBelow = world.getBlockState(posBelow);
        if (blockStateBelow.getRenderType() != BlockRenderType.INVISIBLE && world.getLightLevel(pos) > 3) {
            if (blockStateBelow.isFullCube(world, posBelow)) {
                VoxelShape voxelShape = blockStateBelow.getOutlineShape(world, posBelow);
                if (!voxelShape.isEmpty()) {
                    Vec3d playerPos = RotationUtil.vecWorldToPlayer(x, y, z, gravityDirection);
                    float alpha = (float)(((double)opacity - (playerPos.y - (RotationUtil.vecWorldToPlayer(Vec3d.ofCenter(pos), gravityDirection).y - 0.5D)) / 2.0D) * 0.5D * (double)world.getBrightness(pos));
                    if (alpha >= 0.0F) {
                        if (alpha > 1.0F) {
                            alpha = 1.0F;
                        }

                        Vec3d centerPos = Vec3d.ofCenter(pos);
                        Vec3d playerCenterPos = RotationUtil.vecWorldToPlayer(centerPos, gravityDirection);

                        Vec3d playerRelNN = playerCenterPos.add(-0.5D, -0.5D, -0.5D).subtract(playerPos);
                        Vec3d playerRelPP = playerCenterPos.add( 0.5D, -0.5D,  0.5D).subtract(playerPos);

                        Vec3d relNN = RotationUtil.vecWorldToPlayer(centerPos.add(RotationUtil.vecPlayerToWorld(-0.5D, -0.5D, -0.5D, gravityDirection)).subtract(x, y, z), gravityDirection);
                        Vec3d relNP = RotationUtil.vecWorldToPlayer(centerPos.add(RotationUtil.vecPlayerToWorld(-0.5D, -0.5D,  0.5D, gravityDirection)).subtract(x, y, z), gravityDirection);
                        Vec3d relPN = RotationUtil.vecWorldToPlayer(centerPos.add(RotationUtil.vecPlayerToWorld( 0.5D, -0.5D, -0.5D, gravityDirection)).subtract(x, y, z), gravityDirection);
                        Vec3d relPP = RotationUtil.vecWorldToPlayer(centerPos.add(RotationUtil.vecPlayerToWorld(0.5D, -0.5D,  0.5D, gravityDirection)).subtract(x, y, z), gravityDirection);

                        float minU = -(float) playerRelNN.x / 2.0F / radius + 0.5F;
                        float maxU = -(float) playerRelPP.x / 2.0F / radius + 0.5F;
                        float minV = -(float) playerRelNN.z / 2.0F / radius + 0.5F;
                        float maxV = -(float) playerRelPP.z / 2.0F / radius + 0.5F;

                        //ToDo: Check
                        int i = ColorHelper.getArgb(MathHelper.floor(alpha * 255.0F), 255, 255, 255);
                        drawShadowVertex(entry, vertices, i, (float) relNN.x, (float) relNN.y, (float) relNN.z, minU, minV);
                        drawShadowVertex(entry, vertices, i, (float) relNP.x, (float) relNP.y, (float) relNP.z, minU, maxV);
                        drawShadowVertex(entry, vertices, i, (float) relPP.x, (float) relPP.y, (float) relPP.z, maxU, maxV);
                        drawShadowVertex(entry, vertices, i, (float) relPN.x, (float) relPN.y, (float) relPN.z, maxU, minV);
                    }
                }
            }
        }
    }

    //ToDo: Check
//    @ModifyVariable(
//            method = "renderHitbox",
//            at = @At(
//                    value = "INVOKE_ASSIGN",
//                    target = "Lnet/minecraft/util/math/Box;offset(DDD)Lnet/minecraft/util/math/Box;",
//                    ordinal = 0
//            ),
//            ordinal = 0
//    )
//    private static Box modify_renderHitbox_Box_0(Box box, MatrixStack matrices, VertexConsumer vertices, Entity entity, float tickDelta) {
//        Direction gravityDirection = GravityChangerAPI.getGravityDirection(entity);
//        if(gravityDirection == Direction.DOWN) {
//            return box;
//        }
//
//        return RotationUtil.boxWorldToPlayer(box, gravityDirection);
//    }
    @ModifyVariable(
            method = "renderHitbox",
            at = @At(
                    value = "STORE",
                    ordinal = 0
            ),
            ordinal = 0
    )
    private static Box modify_renderHitbox_Box_0(Box box, MatrixStack matrices, VertexConsumer vertices, Entity entity, float tickDelta, float red, float green, float blue) {
        Direction gravityDirection = GravityChangerAPI.getGravityDirection(entity);
        if(gravityDirection == Direction.DOWN) {
            return box;
        }

        return RotationUtil.boxWorldToPlayer(box, gravityDirection);
    }

//    @ModifyVariable(
//            method = "renderHitbox",
//            at = @At(
//                    value = "INVOKE_ASSIGN",
//                    target = "Lnet/minecraft/entity/Entity;getRotationVec(F)Lnet/minecraft/util/math/Vec3d;",
//                    ordinal = 0
//            ),
//            ordinal = 0
//    )
//    private static Vec3d modify_renderHitbox_Vec3d_0(Vec3d vec3d, MatrixStack matrices, VertexConsumer vertices, Entity entity, float tickDelta, float red, float green, float blue) {
//        Direction gravityDirection = GravityChangerAPI.getGravityDirection(entity);
//        if(gravityDirection == Direction.DOWN) {
//            return vec3d;
//        }
//
//        return RotationUtil.vecWorldToPlayer(vec3d, gravityDirection);
//    }
}
