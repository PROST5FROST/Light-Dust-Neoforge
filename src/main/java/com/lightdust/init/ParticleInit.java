package com.lightdust.init;

import com.lightdust.LightDust;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ParticleInit {
    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(
                    BuiltInRegistries.PARTICLE_TYPE,
                    LightDust.MODID
            );

    public static final DeferredHolder<Particle, Particle> DUST_PARTICLE = PARTICLES.register(
            "dust_particle",
            () -> new ParticleTypes();
    );

    public static void register(IEventBus eventBus) {
        PARTICLES.register(eventBus);
    }
}