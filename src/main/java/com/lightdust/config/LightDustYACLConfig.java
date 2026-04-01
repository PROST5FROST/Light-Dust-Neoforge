package com.lightdust.config;


import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.network.chat.Component;

import java.util.Arrays;
import java.util.List;

public class LightDustYACLConfig {
    public static YetAnotherConfigLib createGui() {
        return YetAnotherConfigLib.createBuilder()
                .title(Component.literal("Light Dust Neoforge"))

                .category(ConfigCategory.createBuilder()
                        .name(Component.literal("General Settings"))
                        .option(Option.<Integer>createBuilder()
                                .name(Component.literal("Dust Spawn Radius"))
                                .binding(
                                        16,
                                        LightDustConfig.AMBIENT_RADIUS,
                                        LightDustConfig.AMBIENT_RADIUS::set
                                )
                                .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                                        .range(0, 32)
                                        .step(1))
                                .build())
                        .build()) // Closing General Category
                // Experimental Category
                .category(ConfigCategory.createBuilder()
                        .name(Component.literal("🧪 Experimental"))

                        .option(Option.<Boolean>createBuilder()
                                .name(Component.literal("Entity Disturbance"))
                                .description(OptionDescription.of(Component.literal("Моби будуть розганяти пил, коли проходять крізь нього.")))
                                .binding(
                                        true,
                                        LightDustExperimentalConfig.ENABLE_ENTITY_DISTURBANCE,
                                        LightDustExperimentalConfig.ENABLE_ENTITY_DISTURBANCE::set
                                )
                                .controller(TickBoxControllerBuilder::create)
                                .build())

                        .option(Option.<Boolean>createBuilder()
                                .name(Component.literal("Dust Settling"))
                                .description(OptionDescription.of(Component.literal("Пил буде осідати на блоки (тестова функція).")))
                                .binding(
                                        true,
                                        LightDustExperimentalConfig.ENABLE_DUST_SETTLING,
                                        LightDustExperimentalConfig.ENABLE_DUST_SETTLING::set
                                )
                                .controller(TickBoxControllerBuilder::create)
                                .build())

                        .option(Option.<Boolean>createBuilder()
                                .name(Component.literal("Dust Settling"))
                                .description(OptionDescription.of(Component.literal("Пил буде осідати на блоки (тестова функція).")))
                                .binding(
                                        true,
                                        LightDustExperimentalConfig.ENABLE_DUST_SETTLING,
                                        LightDustExperimentalConfig.ENABLE_DUST_SETTLING::set
                                )
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .build()) // Experimental category closing

                // Final saving & building
                .save(LightDustConfig.SPEC::save)
                .build();
    }
}