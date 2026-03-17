package com.lightdust.init;

import com.lightdust.LightDust;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ParticleInit {
    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(
                    BuiltInRegistries.PARTICLE_TYPE,
                    LightDust.MODID
            );

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> DUST_PARTICLE =
            PARTICLES.register(
                    "dust_particle", () -> new SimpleParticleType(false)
            );

    public static void register(IEventBus eventBus) {
        PARTICLES.register(eventBus);
    }
}