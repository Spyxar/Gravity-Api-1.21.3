package com.fusionflux.gravity_api.item;

import com.fusionflux.gravity_api.api.GravityChangerAPI;
import com.fusionflux.gravity_api.api.RotationParameters;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.List;

public class GravityChangerItemAOE extends Item {
    public final Direction gravityDirection;

    public GravityChangerItemAOE(Item.Settings settings, Direction _gravityDirection) {
        super(settings);
        gravityDirection = _gravityDirection;
    }

    @Override
    public ActionResult use(World level, PlayerEntity player, Hand usedHand) {
        if(!level.isClient()) {
            Box box = player.getBoundingBox().expand(3);
            List<Entity> list = level.getEntitiesByClass(Entity.class, box, e -> !(e instanceof PlayerEntity));
            for (Entity entity : list) {
                GravityChangerAPI.setDefaultGravityDirection(entity, gravityDirection, new RotationParameters());
            }
        }
        return ActionResult.SUCCESS;
    }
}
