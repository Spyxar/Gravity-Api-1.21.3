package com.fusionflux.gravity_api.item;

import com.fusionflux.gravity_api.api.GravityChangerAPI;
import com.fusionflux.gravity_api.api.RotationParameters;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class GravityChangerItem extends Item {
    public final Direction gravityDirection;

    public GravityChangerItem(Item.Settings settings, Direction _gravityDirection) {
        super(settings);
        gravityDirection = _gravityDirection;
    }

    @Override
    public ActionResult use(World world, PlayerEntity player, Hand hand)
    {
        if (world.isClient()) {
            if (!player.isSneaking()) {
                GravityChangerAPI.setDefaultGravityDirectionClient((ClientPlayerEntity) player, gravityDirection, new RotationParameters(), Verifier.FIELD_GRAVITY_SOURCE, Verifier.packInfo(player.getBlockPos()));
            } else {
                GravityChangerAPI.setDefaultGravityDirectionClient((ClientPlayerEntity) player, Direction.DOWN, new RotationParameters(), Verifier.FIELD_GRAVITY_SOURCE, Verifier.packInfo(player.getBlockPos()));
            }
        }
        return ActionResult.SUCCESS;
    }
}
