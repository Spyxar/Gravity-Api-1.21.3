package com.fusionflux.gravity_api.mixin.client;


import com.fusionflux.gravity_api.api.GravityChangerAPI;
import com.fusionflux.gravity_api.util.RotationUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.FishingBobberEntityRenderer;
import net.minecraft.client.render.entity.state.FishingBobberEntityState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.math.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
@Mixin(FishingBobberEntityRenderer.class)
public abstract class FishingBobberEntityRendererMixin extends EntityRenderer<FishingBobberEntity, FishingBobberEntityState> {
    @Shadow @Final private static RenderLayer LAYER;

    //ToDo: Check
//    @Shadow private static void vertex(VertexConsumer buffer, Matrix4f matrix, Matrix3f normalMatrix, int light, float x, int y, int u, int v) {}
    @Shadow private static void vertex(VertexConsumer buffer, MatrixStack.Entry matrix, int light, float x, int y, int u, int v) {}

    @Shadow private static float percentage(int value, int max) { return 0.0F; }

    //ToDo: Check
    @Shadow
//    private static void drawArcSection(float x, float y, float z, VertexConsumer buffer, MatrixStack.Entry normal, float startPercent, float endPercent) {
    private static void renderFishingLine(float x, float y, float z, VertexConsumer buffer, MatrixStack.Entry matrices, float segmentStart, float segmentEnd) {
    }

    protected FishingBobberEntityRendererMixin(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Inject(
            method = "render(Lnet/minecraft/client/render/entity/state/FishingBobberEntityState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
            at = @At("HEAD"),
            cancellable = true
    )
    public void inject_render(FishingBobberEntityState fishingBobberEntityState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, CallbackInfo ci) {
        //ToDo:
//        PlayerEntity playerEntity = fishingBobberEntity.getPlayerOwner();
        PlayerEntity playerEntity = null;
        if(playerEntity == null) return;

        Direction gravityDirection = GravityChangerAPI.getGravityDirection(playerEntity);
        if(gravityDirection == Direction.DOWN) return;

        ci.cancel();

        matrixStack.push();
        matrixStack.push();
        matrixStack.scale(0.5F, 0.5F, 0.5F);
        matrixStack.multiply(this.dispatcher.getRotation());
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F));
        MatrixStack.Entry entry = matrixStack.peek();
//        Matrix4f matrix4f = entry.getModel();
//        Matrix3f matrix3f = entry.getNormal();
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(LAYER);
//        vertex(vertexConsumer, matrix4f, matrix3f, light, 0.0F, 0, 0, 1);
//        vertex(vertexConsumer, matrix4f, matrix3f, light, 1.0F, 0, 1, 1);
//        vertex(vertexConsumer, matrix4f, matrix3f, light, 1.0F, 1, 1, 0);
//        vertex(vertexConsumer, matrix4f, matrix3f, light, 0.0F, 1, 0, 0);
        vertex(vertexConsumer, entry, light, 0.0F, 0, 0, 1);
        vertex(vertexConsumer, entry, light, 1.0F, 0, 1, 1);
        vertex(vertexConsumer, entry, light, 1.0F, 1, 1, 0);
        vertex(vertexConsumer, entry, light, 0.0F, 1, 0, 0);
        matrixStack.pop();
        int armOffset = playerEntity.getMainArm() == Arm.RIGHT ? 1 : -1;
        ItemStack itemStack = playerEntity.getMainHandStack();
        if (!itemStack.isOf(Items.FISHING_ROD)) {
            armOffset = -armOffset;
        }
        //ToDo: Check
        float tickDelta = fishingBobberEntityState.age;
        float handSwingProgress = playerEntity.getHandSwingProgress(tickDelta);
        float sinHandSwingProgress = MathHelper.sin(MathHelper.sqrt(handSwingProgress) * 3.1415927F);
        float radBodyYaw = MathHelper.lerp(tickDelta, playerEntity.prevBodyYaw, playerEntity.bodyYaw) * 0.017453292F;
        double sinBodyYaw = MathHelper.sin(radBodyYaw);
        double cosBodyYaw = MathHelper.cos(radBodyYaw);
        double scaledArmOffset = (double) armOffset * 0.35D;
        Vec3d lineStart;
        if ((this.dispatcher.gameOptions == null || this.dispatcher.gameOptions.getPerspective().isFirstPerson()) && playerEntity == MinecraftClient.getInstance().player) {
            Vec3d lineOffset = RotationUtil.vecWorldToPlayer(this.dispatcher.camera.getProjection().getPosition((float) armOffset * 0.525F, -0.1F), gravityDirection);
            lineOffset = lineOffset.multiply(960.0D / this.dispatcher.gameOptions.getFov().getValue());
            lineOffset = lineOffset.rotateY(sinHandSwingProgress * 0.5F);
            lineOffset = lineOffset.rotateX(-sinHandSwingProgress * 0.7F);
            lineStart = new Vec3d(
                    MathHelper.lerp(tickDelta, playerEntity.prevX, playerEntity.getX()),
                    MathHelper.lerp(tickDelta, playerEntity.prevY, playerEntity.getY()),
                    MathHelper.lerp(tickDelta, playerEntity.prevZ, playerEntity.getZ())
            ).add(RotationUtil.vecPlayerToWorld(lineOffset.add(0.0D, playerEntity.getStandingEyeHeight(), 0.0D), gravityDirection));
        } else {
            lineStart = new Vec3d(
                    MathHelper.lerp(tickDelta, playerEntity.prevX, playerEntity.getX()),
                    playerEntity.prevY + (playerEntity.getY() - playerEntity.prevY) * tickDelta,
                    MathHelper.lerp(tickDelta, playerEntity.prevZ, playerEntity.getZ())
            ).add(RotationUtil.vecPlayerToWorld(
                    -cosBodyYaw * scaledArmOffset - sinBodyYaw * 0.8D,
                    playerEntity.getStandingEyeHeight() + (playerEntity.isInSneakingPose() ? -0.1875D : 0.0D) - 0.45D,
                    -sinBodyYaw * scaledArmOffset + cosBodyYaw * 0.8D,
                    gravityDirection
            ));
        }

        //ToDo: Check (first param was prevX,Y,Z)
        double bobberX = MathHelper.lerp(tickDelta, fishingBobberEntityState.x, fishingBobberEntityState.pos.x);
        double bobberY = MathHelper.lerp(tickDelta, fishingBobberEntityState.y, fishingBobberEntityState.pos.y) + 0.25D;
        double bobberZ = MathHelper.lerp(tickDelta, fishingBobberEntityState.z, fishingBobberEntityState.pos.z);
        float relX = (float)(lineStart.x - bobberX);
        float relY = (float)(lineStart.y - bobberY);
        float relZ = (float)(lineStart.z - bobberZ);
        VertexConsumer vertexConsumer2 = vertexConsumerProvider.getBuffer(RenderLayer.getLineStrip());
        MatrixStack.Entry entry2 = matrixStack.peek();

        for(int i = 0; i <= 16; ++i) {
            renderFishingLine(relX, relY, relZ, vertexConsumer2, entry2, percentage(i, 16), percentage(i + 1, 16));
        }

        matrixStack.pop();
        super.render(fishingBobberEntityState, matrixStack, vertexConsumerProvider, light);
    }
}
