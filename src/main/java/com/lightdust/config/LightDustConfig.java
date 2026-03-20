package com.lightdust.config; 

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.Arrays;
import java.util.List;

public class LightDustConfig {

    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    // Spawning & Performance
    public static final ModConfigSpec.IntValue AMBIENT_RADIUS;
    public static final ModConfigSpec.IntValue AMBIENT_HARD_CAP;
    public static final ModConfigSpec.IntValue AMBIENT_BLOCK_CAP;
    public static final ModConfigSpec.IntValue MIN_BLOCK_LIGHT;
    public static final ModConfigSpec.IntValue DAYTIME_LIGHT_DIFF;
    public static final ModConfigSpec.IntValue FALLOFF_DISTANCE;
    public static final ModConfigSpec.DoubleValue FALLOFF_MULTIPLIER;
    public static final ModConfigSpec.BooleanValue ENABLE_OCCLUSION_CULLING;

    // Visuals & Environment
    public static final ModConfigSpec.DoubleValue AMBIENT_DUST_OPACITY;
    public static final ModConfigSpec.DoubleValue PARTICLE_SIZE;
    public static final ModConfigSpec.IntValue PARTICLE_LIFETIME;
    public static final ModConfigSpec.DoubleValue WIND_SPEED_CLEAR;
    public static final ModConfigSpec.DoubleValue WIND_SPEED_RAIN;
    public static final ModConfigSpec.DoubleValue WIND_SPEED_THUNDER;
    public static final ModConfigSpec.BooleanValue DISABLE_DURING_RAIN;
    public static final ModConfigSpec.BooleanValue DISABLE_DURING_THUNDER;

    // Player Interactions & World Actions
    public static final ModConfigSpec.DoubleValue PLAYER_INTERACT_RADIUS;
    public static final ModConfigSpec.IntValue BREAK_PARTICLE_COUNT;
    public static final ModConfigSpec.DoubleValue BREAK_PARTICLE_SPEED;
    public static final ModConfigSpec.DoubleValue ACTION_DUST_GRAVITY;
    public static final ModConfigSpec.DoubleValue ACTION_DUST_BOUNCE;
    
    public static final ModConfigSpec.IntValue HEAVY_LANDING_MAX_PARTICLES;
    public static final ModConfigSpec.IntValue HEAVY_LANDING_PARTICLE_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue HEAVY_LANDING_UPWARD_SPEED;
    public static final ModConfigSpec.DoubleValue HEAVY_LANDING_OUTWARD_SPEED;
    public static final ModConfigSpec.DoubleValue HEAVY_LANDING_AMBIENT_PUSH;
    public static final ModConfigSpec.DoubleValue HEAVY_LANDING_AMBIENT_RADIUS;
    
    public static final ModConfigSpec.ConfigValue<List<? extends String>> HEAT_BLOCKS;

    // Experimental Features
    public static final ModConfigSpec.BooleanValue ENABLE_DUST_SETTLING;
    public static final ModConfigSpec.BooleanValue ENABLE_ENTITY_DISTURBANCE;
    public static final ModConfigSpec.DoubleValue ENTITY_PUSH_STRENGTH;

    public static final List<String> DEFAULT_HEAT_BLOCKS = Arrays.asList(
            "minecraft:torch=0.015,2,0.4",
            "minecraft:wall_torch=0.015,2,0.4",
            "minecraft:soul_torch=0.015,2,0.4",
            "minecraft:soul_wall_torch=0.015,2,0.4",
            "minecraft:magma_block=0.02,3,0.7",
            "minecraft:campfire=0.035,5,0.5",
            "minecraft:soul_campfire=0.035,5,0.5",
            "minecraft:lava=0.045,5,0.7"
    );

    static {
        BUILDER.push("Spawning and Performance");
        AMBIENT_RADIUS = BUILDER.comment("Spawn Radius: How far away from the player (in blocks) dust will attempt to spawn.")
                .defineInRange("ambientRadius", 10, 1, 32);
        AMBIENT_HARD_CAP = BUILDER.comment("Hard Cap Radius: The absolute max distance dust can exist.")
                .defineInRange("ambientHardCapRadius", 12, 1, 48);
        AMBIENT_BLOCK_CAP = BUILDER.comment("Max dust particles allowed per single block.")
                .defineInRange("ambientBlockCap", 14, 1, 20);
        MIN_BLOCK_LIGHT = BUILDER.comment("Minimum block light level required for ambient dust to spawn.")
                .defineInRange("minBlockLight", 6, 0, 15);
        DAYTIME_LIGHT_DIFF = BUILDER.comment("The minimum difference between Block Light and Sky Light required for dust to spawn during the day.")
                .defineInRange("daytimeLightDiff", 5, 0, 15);
        FALLOFF_DISTANCE = BUILDER.comment("Distance from the player where dust density starts to rapidly decrease.")
                .defineInRange("falloffDistance", 6, 1, 32);
        FALLOFF_MULTIPLIER = BUILDER.comment("Multiplier applied to the max particle cap beyond the falloff distance.")
                .defineInRange("falloffMultiplier", 0.3, 0.0, 1.0);
        ENABLE_OCCLUSION_CULLING = BUILDER.comment("If true, uses raytracing to prevent dust from spawning behind walls you can't see. Highly recommended for performance.")
                .define("enableOcclusionCulling", true);
        BUILDER.pop();


        BUILDER.push("Visuals and Environment");
        AMBIENT_DUST_OPACITY = BUILDER.comment("Base opacity for ambient dust (0.22 for vanilla, 0.45+ for shaders)")
                .defineInRange("ambientDustOpacity", 0.22, 0.0, 1.0);
        PARTICLE_SIZE = BUILDER.comment("Base size of the dust particles.")
                .defineInRange("particleSize", 0.022, 0.001, 0.1);
        PARTICLE_LIFETIME = BUILDER.comment("Base lifetime of the dust particles in ticks.")
                .defineInRange("particleLifetime", 200, 20, 1000);
        WIND_SPEED_CLEAR = BUILDER.comment("Base wind speed modifier during clear weather.")
                .defineInRange("windSpeedClear", 0.15, 0.0, 1.0);
        WIND_SPEED_RAIN = BUILDER.comment("Base wind speed modifier during rain.")
                .defineInRange("windSpeedRain", 0.25, 0.0, 1.0);
        WIND_SPEED_THUNDER = BUILDER.comment("Base wind speed modifier during thunderstorms.")
                .defineInRange("windSpeedThunder", 0.4, 0.0, 1.0);
        DISABLE_DURING_RAIN = BUILDER.comment("If true, outdoor dust will despawn and stop spawning during rain.")
                .define("disableDuringRain", false);
        DISABLE_DURING_THUNDER = BUILDER.comment("If true, outdoor dust will despawn and stop spawning during thunderstorms.")
                .define("disableDuringThunder", false);
        BUILDER.pop();


        BUILDER.push("Interactions and Actions");
        PLAYER_INTERACT_RADIUS = BUILDER.comment("Radius squared for player interaction (slashing/moving).")
                .defineInRange("playerInteractRadius", 4.0, 0.0, 16.0);
        
        BREAK_PARTICLE_COUNT = BUILDER.comment("Number of dust particles spawned when a block is broken.")
                .defineInRange("breakParticleCount", 12, 0, 50);
        BREAK_PARTICLE_SPEED = BUILDER.comment("How fast the dust shoots out from a broken block.")
                .defineInRange("breakParticleSpeed", 0.1, 0.0, 1.0);
        ACTION_DUST_GRAVITY = BUILDER.comment("How fast block-breaking dust falls downwards over time.")
                .defineInRange("actionDustGravity", 0.002, 0.0, 0.05);
        ACTION_DUST_BOUNCE = BUILDER.comment("How bouncy block-breaking dust is when hitting walls or ceilings. (0.0 = no bounce, 1.0 = super bouncy)")
                .defineInRange("actionDustBounce", 0.2, 0.0, 1.0);

        HEAVY_LANDING_MAX_PARTICLES = BUILDER.comment("The absolute maximum number of dust particles that can spawn from a heavy landing.")
                .defineInRange("heavyLandingMaxParticles", 96, 0, 300);
        HEAVY_LANDING_PARTICLE_MULTIPLIER = BUILDER.comment("Multiplier for landing dust. (Blocks fallen * Multiplier = Particles spawned).")
                .defineInRange("heavyLandingParticleMultiplier", 12, 0, 50);
        HEAVY_LANDING_UPWARD_SPEED = BUILDER.comment("Base upward speed for heavy landing action dust.")
                .defineInRange("heavyLandingUpwardSpeed", 0.2, 0.0, 2.0);
        HEAVY_LANDING_OUTWARD_SPEED = BUILDER.comment("Base outward speed for heavy landing action dust.")
                .defineInRange("heavyLandingOutwardSpeed", 0.12, 0.0, 2.0);
        HEAVY_LANDING_AMBIENT_PUSH = BUILDER.comment("How strongly a heavy landing pushes preexisting ambient dust away.")
                .defineInRange("heavyLandingAmbientPush", 0.001, 0.0, 0.1);
        HEAVY_LANDING_AMBIENT_RADIUS = BUILDER.comment("The radius (in blocks) that ambient dust is pushed away from a heavy landing.")
                .defineInRange("heavyLandingAmbientRadius", 4.0, 1.0, 16.0);

        HEAT_BLOCKS = BUILDER.comment("List of blocks that emit heat, causing dust particles to swirl upwards in a thermal updraft.")
                .defineList("heatBlocks", DEFAULT_HEAT_BLOCKS, obj -> obj instanceof String && ((String) obj).split(",").length == 3);
        BUILDER.pop();

        BUILDER.push("Experimental Features");
        ENABLE_DUST_SETTLING = BUILDER.comment("If true, dust particles will visually settle and rest when hitting the ground.")
                .define("enableDustSettling", true);
        ENABLE_ENTITY_DISTURBANCE = BUILDER.comment("If true, non-player entities (mobs/projectiles) will kick up and disturb dust. (Can be performance heavy in mob farms)")
                .define("enableEntityDisturbance", true);
        ENTITY_PUSH_STRENGTH = BUILDER.comment("How strongly non-player entities push dust when moving through it.")
                .defineInRange("entityPushStrength", 0.05, 0.0, 2.0);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}