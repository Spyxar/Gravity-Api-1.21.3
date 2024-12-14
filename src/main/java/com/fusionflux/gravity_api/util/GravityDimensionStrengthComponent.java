package com.fusionflux.gravity_api.util;


import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

public class GravityDimensionStrengthComponent implements GravityDimensionStrengthInterface {
    double gravityStrength = 1;

    private final World currentLevel;

    public GravityDimensionStrengthComponent(World level) {
        this.currentLevel = level;
    }

    @Override
    public double getDimensionGravityStrength() {
        return gravityStrength;
    }

    @Override
    public void setDimensionGravityStrength(double strength) {
        if (!currentLevel.isClient()) {
            gravityStrength = strength;
            GravityDimensionStrengthWorldRegister.GRAVITY_DIMENSION_STRENGTH_MODIFIER.sync(currentLevel);
        }
    }

    @Override
    public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registries) {
        gravityStrength = tag.getDouble("DimensionGravityStrength");
    }

    @Override
    public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registries) {
        tag.putDouble("DimensionGravityStrength" , gravityStrength);
    }
}
