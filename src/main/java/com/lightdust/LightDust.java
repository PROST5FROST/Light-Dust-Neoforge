package com.lightdust;

import com.lightdust.client.particle.DustParticle;
import com.lightdust.config.LightDustColorConfig;
import com.lightdust.config.LightDustConfig;
import com.lightdust.config.LightDustExperimentalConfig;
import com.lightdust.config.LightDustYACLConfig;
import com.lightdust.init.ParticleInit;
import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.slf4j.Logger;


@Mod(LightDust.MODID)
public class LightDust {
    public static final String MODID = "lightdust";

    public static final Logger LOGGER = LogUtils.getLogger();


    public LightDust(IEventBus modBus, ModContainer modContainer) {
        ParticleInit.register(modBus);

        modContainer.registerConfig(ModConfig.Type.CLIENT, LightDustConfig.SPEC);
        modContainer.registerConfig(ModConfig.Type.CLIENT, LightDustColorConfig.SPEC, "lightdust-colors.toml");
        modContainer.registerConfig(ModConfig.Type.CLIENT, LightDustExperimentalConfig.SPEC, "lightdust-experemental.toml");

        modContainer.registerExtensionPoint(IConfigScreenFactory.class, (container1, lastScreen) -> {
            return LightDustYACLConfig.createGui().generateScreen(lastScreen);
        });
    ;}
    @EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(ParticleInit.DUST_PARTICLE.get(), DustParticle.Provider::new);
        }
    }
}