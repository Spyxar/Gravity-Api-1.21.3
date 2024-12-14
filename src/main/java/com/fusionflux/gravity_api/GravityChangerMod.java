package com.fusionflux.gravity_api;

import com.fusionflux.gravity_api.command.GravityCommand;
import com.fusionflux.gravity_api.config.GravityChangerConfig;
import com.fusionflux.gravity_api.item.ModItems;
import com.fusionflux.gravity_api.util.GravityComponent;
import com.fusionflux.gravity_api.util.updatednetworking.GravityChangerNetworking;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;

public class GravityChangerMod implements ModInitializer {
    public static final String MOD_ID = "gravity_api";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static GravityChangerConfig config;

    public static final ItemGroup GravityChangerGroup = FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.GRAVITY_CHANGER_UP)).displayName(Text.translatable("itemGroup.gravity_api.general")).entries((params, collector) -> {
        collector.add(ModItems.GRAVITY_CHANGER_DOWN);
        collector.add(ModItems.GRAVITY_CHANGER_UP);
        collector.add(ModItems.GRAVITY_CHANGER_NORTH);
        collector.add(ModItems.GRAVITY_CHANGER_SOUTH);
        collector.add(ModItems.GRAVITY_CHANGER_EAST);
        collector.add(ModItems.GRAVITY_CHANGER_WEST);
        collector.add(ModItems.GRAVITY_CHANGER_DOWN_AOE);
        collector.add(ModItems.GRAVITY_CHANGER_UP_AOE);
        collector.add(ModItems.GRAVITY_CHANGER_NORTH_AOE);
        collector.add(ModItems.GRAVITY_CHANGER_SOUTH_AOE);
        collector.add(ModItems.GRAVITY_CHANGER_EAST_AOE);
        collector.add(ModItems.GRAVITY_CHANGER_WEST_AOE);
    }).build();

    public static final ComponentKey<GravityComponent> GRAVITY_COMPONENT =
            ComponentRegistry.getOrCreate(asResource("gravity_direction"), GravityComponent.class);

    @Override
    public void onInitialize() {
        //ToDo: Check
//        GravityChannel.DEFAULT_GRAVITY.getVerifierRegistry().register(Verifier.FIELD_GRAVITY_SOURCE, Verifier::check);

        MidnightConfig.init("gravity_api", GravityChangerConfig.class);
        ModItems.init();
        Registry.register(Registries.ITEM_GROUP, asResource("general"), GravityChangerGroup);

        GravityChangerNetworking.init();

//        GravityPackets.registerPackets();

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> GravityCommand.register(dispatcher));
    }

    public static Identifier asResource(String path) {
        return Identifier.of(MOD_ID, path);
    }
}
