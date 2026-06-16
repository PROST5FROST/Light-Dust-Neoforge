package com.luminousdust.config;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.DoubleSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class LumDustYACL {
    public static Screen createConfigScreen(Screen parentScreen) {
        return YetAnotherConfigLib.createBuilder()
                .title(Component.translatable("luminousdust.config.title"))

                // === CATEGORY: SPAWNING ===
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("luminousdust.config.category.spawning"))

                        .option(Option.<Integer>createBuilder()
                                .name(Component.translatable("luminousdust.config.spawning.ambientRadius"))
                                .description(OptionDescription.of(Component.translatable("luminousdust.config.spawning.ambientRadius.desc")))
                                .binding(10,
                                        LumDustConf.AMBIENT_RADIUS,
                                        LumDustConf.AMBIENT_RADIUS::set)
                                .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(1, 32).step(1))
                                .build())

                        .option(Option.<Integer>createBuilder()
                                .name(Component.translatable("luminousdust.config.spawning.ambientHardCapRadius"))
                                .description(OptionDescription.of(Component.translatable("luminousdust.config.spawning.ambientHardCapRadius.desc")))
                                .binding(12,
                                        LumDustConf.AMBIENT_HARD_CAP,
                                        LumDustConf.AMBIENT_HARD_CAP::set)
                                .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(1, 48).step(1))
                                .build())

                        .option(Option.<Integer>createBuilder()
                                .name(Component.translatable("luminousdust.config.spawning.ambientBlockCap"))
                                .description(OptionDescription.of(Component.translatable("luminousdust.config.spawning.ambientBlockCap.desc")))
                                .binding(14,
                                        LumDustConf.AMBIENT_BLOCK_CAP,
                                        LumDustConf.AMBIENT_BLOCK_CAP::set)
                                .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(1, 20).step(1))
                                .build())

                        .option(Option.<Integer>createBuilder()
                                .name(Component.translatable("luminousdust.config.spawning.minBlockLight"))
                                .description(OptionDescription.of(Component.translatable("luminousdust.config.spawning.minBlockLight.desc")))
                                .binding(6,
                                        LumDustConf.MIN_BLOCK_LIGHT,
                                        LumDustConf.MIN_BLOCK_LIGHT::set)
                                .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(0, 15).step(1))
                                .build())

                        .option(Option.<Integer>createBuilder()
                                .name(Component.translatable("luminousdust.config.spawning.daytimeLightDiff"))
                                .description(OptionDescription.of(Component.translatable("luminousdust.config.spawning.daytimeLightDiff.desc")))
                                .binding(5,
                                        LumDustConf.DAYTIME_LIGHT_DIFF,
                                        LumDustConf.DAYTIME_LIGHT_DIFF::set)
                                .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(0, 15).step(1))
                                .build())
                        .build())

                // === CATEGORY: VISUALS ===
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("luminousdust.config.category.visuals"))

                        .option(Option.<Double>createBuilder()
                                .name(Component.translatable("luminousdust.config.visuals.ambientDustOpacity"))
                                .description(OptionDescription.of(Component.translatable("luminousdust.config.visuals.ambientDustOpacity.desc")))
                                .binding(0.22,
                                        LumDustConf.AMBIENT_DUST_OPACITY,
                                        LumDustConf.AMBIENT_DUST_OPACITY::set)
                                .controller(opt -> DoubleSliderControllerBuilder.create(opt).range(0.0, 1.0).step(0.05))
                                .build())

                        .option(Option.<Double>createBuilder()
                                .name(Component.translatable("luminousdust.config.visuals.particleSize"))
                                .description(OptionDescription.of(Component.translatable("luminousdust.config.visuals.particleSize.desc")))
                                .binding(0.44,
                                        LumDustConf.PARTICLE_SIZE,
                                        LumDustConf.PARTICLE_SIZE::set)
                                .controller(opt -> DoubleSliderControllerBuilder.create(opt).range(0.01, 1.0).step(0.01))
                                .build())

                        .option(Option.<Integer>createBuilder()
                                .name(Component.translatable("luminousdust.config.visuals.particleLifetime"))
                                .description(OptionDescription.of(Component.translatable("luminousdust.config.visuals.particleLifetime.desc")))
                                .binding(200,
                                        LumDustConf.PARTICLE_LIFETIME,
                                        LumDustConf.PARTICLE_LIFETIME::set)
                                .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(20, 1000).step(10))
                                .build())
                        .build())

                // === CATEGORY: COLOR AND TINTING ===
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("luminousdust.config.category.color"))

                        .option(Option.<Double>createBuilder()
                                .name(Component.translatable("luminousdust.config.color.tintStrength"))
                                .description(OptionDescription.of(Component.translatable("luminousdust.config.color.tintStrength.desc")))
                                .binding(0.6,
                                        LumDustConf.TINT_STRENGTH,
                                        LumDustConf.TINT_STRENGTH::set)
                                .controller(opt -> DoubleSliderControllerBuilder.create(opt).range(0.0, 1.0).step(0.05))
                                .build())
                        .build())

                // === CATEGORY: INTERACTION AND PHYSICS ===
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("luminousdust.config.category.interaction"))

                        .option(Option.<Double>createBuilder()
                                .name(Component.translatable("luminousdust.config.interaction.playerInteractRadius"))
                                .description(OptionDescription.of(Component.translatable("luminousdust.config.interaction.playerInteractRadius.desc")))
                                .binding(4.0,
                                        LumDustConf.PLAYER_INTERACT_RADIUS,
                                        LumDustConf.PLAYER_INTERACT_RADIUS::set)
                                .controller(opt -> DoubleSliderControllerBuilder.create(opt).range(0.0, 16.0).step(0.1))
                                .build())

                        .option(Option.<Integer>createBuilder()
                                .name(Component.translatable("luminousdust.config.interaction.breakParticleCount"))
                                .description(OptionDescription.of(Component.translatable("luminousdust.config.interaction.breakParticleCount.desc")))
                                .binding(12,
                                        LumDustConf.BREAK_PARTICLE_COUNT,
                                        LumDustConf.BREAK_PARTICLE_COUNT::set)
                                .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(0, 50).step(1))
                                .build())

                        .option(Option.<Double>createBuilder()
                                .name(Component.translatable("luminousdust.config.interaction.breakParticleSpeed"))
                                .description(OptionDescription.of(Component.translatable("luminousdust.config.interaction.breakParticleSpeed.desc")))
                                .binding(0.1,
                                        LumDustConf.BREAK_PARTICLE_SPEED,
                                        LumDustConf.BREAK_PARTICLE_SPEED::set)
                                .controller(opt -> DoubleSliderControllerBuilder.create(opt).range(0.0, 1.0).step(0.05))
                                .build())
                        .build())

                .build()
                .generateScreen(parentScreen);
    }
}