package com.lightdust.config;


import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.ListOption;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import dev.isxander.yacl3.config.ConfigEntry;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.impl.YetAnotherConfigLibImpl;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.NeoForge;

import java.util.Arrays;
import java.util.List;

public class LightDustYACLConfig {
    public static class MyConfig {
        @ConfigEntry public int ambientRadius = 16;
        @ConfigEntry public int ambientHardCap = 200;
        @ConfigEntry public int ambientBlockCap = 20;
        @ConfigEntry public int minBlockLight = 0;
        @ConfigEntry public int daytimeLightDiff = 8;
        @ConfigEntry public int falloffDistance = 12;
        @ConfigEntry public double falloffMultiplier = 0.5;
        @ConfigEntry public boolean enableOcclusionCulling = true;

        // Visuals & Environment
        @ConfigEntry public double ambientDustOpacity = 0.5;
        @ConfigEntry public double particleSize = 1.0;
        @ConfigEntry public int particleLifetime = 600;
        @ConfigEntry public double windSpeedClear = 0.02;
        @ConfigEntry public double windSpeedRain = 0.05;
        @ConfigEntry public double windSpeedThunder = 0.1;
        @ConfigEntry public boolean disableDuringRain = false;
        @ConfigEntry public boolean disableDuringThunder = false;

        // Player Interactions
        @ConfigEntry public double playerInteractRadius = 3.0;
        @ConfigEntry public int breakParticleCount = 10;
        @ConfigEntry public double actionDustGravity = 0.01;

        // Lists
        @ConfigEntry public static final List<String> DEFAULT_HEAT_BLOCKS = Arrays.asList(
                "minecraft:torch=0.015,2,0.4",
                "minecraft:wall_torch=0.015,2,0.4",
                "minecraft:soul_torch=0.015,2,0.4",
                "minecraft:soul_wall_torch=0.015,2,0.4",
                "minecraft:magma_block=0.02,3,0.7",
                "minecraft:campfire=0.035,5,0.5",
                "minecraft:soul_campfire=0.035,5,0.5",
                "minecraft:lava=0.045,5,0.7"
        );
    }

    public static final ConfigClassHandler<MyConfig> HANDLER = ConfigClassHandler.createBuilder(MyConfig.class)
            .id(ResourceLocation.fromNamespaceAndPath("lightdust", "config")) // Для 1.21.1 синтаксис змінився!
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FMLPaths.CONFIGDIR.get().resolve("lightdust.json5")) // Шлях через FMLPaths
                    .setJson5(true)
                    .build())
            .build();
}
