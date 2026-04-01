package com.lightdust.client.particle;

import com.lightdust.config.LightDustConfig;
import com.lightdust.config.LightDustColorConfig;
import com.lightdust.config.LightDustExperimentalConfig;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class ActionDustParticle extends TextureSheetParticle {

    private float rotSpeed;
    private final float baseAlpha;

    protected ActionDustParticle(ClientLevel level, double x, double y, double z, double dx, double dy, double dz, SpriteSet sprites) {
        super(level, x, y, z);
        this.xd = dx;
        this.yd = dy;
        this.zd = dz;

        // ЗАХИСТ ВІД КРАШУ: якщо конфіг не завантажений, ставимо дефолт
        if (LightDustConfig.SPEC.isLoaded()) {
            this.quadSize = LightDustConfig.PARTICLE_SIZE.get().floatValue() * 1.5F;
            this.baseAlpha = LightDustConfig.AMBIENT_DUST_OPACITY.get().floatValue() * 1.5F;
        } else {
            this.quadSize = 0.2F;
            this.baseAlpha = 0.5F;
        }

        this.lifetime = 40 + level.random.nextInt(30);
        this.gravity = 0.00F;
        this.hasPhysics = true;

        BlockPos pos = BlockPos.containing(x, y, z);
        float r = 0.8F, g = 0.8F, b = 0.8F;

        // Біомний тінт
        try {
            String biomeName = level.getBiome(pos).unwrapKey().map(key -> key.location().toString()).orElse("minecraft:plains");
            if (LightDustColorConfig.SPEC.isLoaded()) {
                for (String entry : LightDustColorConfig.CUSTOM_BIOME_TINTS.get()) {
                    String[] parts = entry.split("=");
                    if (parts.length == 2 && parts[0].trim().equals(biomeName) && parts[1].contains("#")) {
                        parseHexToFloats(parts[1]);
                        break;
                    }
                }
            }
        } catch (Exception ignored) {}

        // Розрахунок яскравості
        int maxLight = Math.max(level.getBrightness(LightLayer.BLOCK, pos), level.getBrightness(LightLayer.SKY, pos));
        float intensity = Math.max(0f, (maxLight - 4) / 11.0f);
        float baseBrightness = 0.35F + (0.65F * intensity);

        float baseR = r * baseBrightness;
        float baseG = g * baseBrightness;
        float baseB = b * baseBrightness;

        float[] blockTint = findNearbyLightSourceTint(level, pos);

        float strength = LightDustColorConfig.SPEC.isLoaded() ? LightDustColorConfig.TINT_STRENGTH.get().floatValue() : 0.0F;
        float rVar = (level.random.nextFloat() - 0.5F) * 0.1F;
        float gVar = (level.random.nextFloat() - 0.5F) * 0.1F;
        float bVar = (level.random.nextFloat() - 0.5F) * 0.1F;

        if (blockTint != null && strength > 0) {
            this.rCol = Mth.clamp((baseR * (1 - strength) + blockTint[0] * strength) + rVar, 0.0F, 1.0F);
            this.gCol = Mth.clamp((baseG * (1 - strength) + blockTint[1] * strength) + gVar, 0.0F, 1.0F);
            this.bCol = Mth.clamp((baseB * (1 - strength) + blockTint[2] * strength) + bVar, 0.0F, 1.0F);
        } else {
            this.rCol = Mth.clamp(baseR + rVar, 0.0F, 1.0F);
            this.gCol = Mth.clamp(baseG + gVar, 0.0F, 1.0F);
            this.bCol = Mth.clamp(baseB + bVar, 0.0F, 1.0F);
        }

        this.roll = level.random.nextFloat() * Mth.TWO_PI;
        this.oRoll = this.roll;
        this.rotSpeed = (level.random.nextFloat() - 0.5F) * 0.2F;

        this.pickSprite(sprites);
        this.alpha = this.baseAlpha;
    }

    private float[] findNearbyLightSourceTint(ClientLevel level, BlockPos pos) {
        if (!LightDustColorConfig.SPEC.isLoaded()) return null;

        for (BlockPos p : BlockPos.betweenClosed(pos.offset(-2, -2, -2), pos.offset(2, 2, 2))) {
            BlockState state = level.getBlockState(p);
            if (state.getLightEmission(level, p) > 0) {
                String blockName = BuiltInRegistries.BLOCK.getKey(state.getBlock()).toString();
                for (String entry : LightDustColorConfig.CUSTOM_TINTS.get()) {
                    String[] parts = entry.split("=");
                    if (parts.length == 2 && parts[0].trim().equals(blockName) && parts[1].contains("#")) {
                        return parseHexToFloats(parts[1]);
                    }
                }
            }
        }
        return null;
    }

    private float[] parseHexToFloats(String hexPart) {
        String hex = hexPart.substring(hexPart.indexOf("#") + 1).trim();
        if (hex.length() == 6) {
            return new float[] {
                    Integer.parseInt(hex.substring(0, 2), 16) / 255f,
                    Integer.parseInt(hex.substring(2, 4), 16) / 255f,
                    Integer.parseInt(hex.substring(4, 6), 16) / 255f
            };
        }
        return null;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        super.tick(); // Виклик супер-методу для базового руху

        if (this.age > this.lifetime - 15) {
            this.alpha = this.baseAlpha * ((this.lifetime - this.age) / 15.0F);
        }

        if (!this.onGround) {
            float seed = (float)(this.x * 10.0 + this.y * 10.0 + this.z * 10.0);
            float time = (float)(this.age * 0.05F);

            // Використання Mth замість Math для швидкості
            double sinX = Mth.sin(time * 0.8f + seed) * 0.0002;
            double cosZ = Mth.cos(time * 1.1f + seed) * 0.0002;

            double gravityVal = LightDustConfig.SPEC.isLoaded() ? LightDustConfig.ACTION_DUST_GRAVITY.get() : 0.001;
            this.xd += sinX;
            this.zd += cosZ;
            this.yd -= gravityVal;
        }
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;
        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
            return new ActionDustParticle(level, x, y, z, dx, dy, dz, this.sprites);
        }
    }
}