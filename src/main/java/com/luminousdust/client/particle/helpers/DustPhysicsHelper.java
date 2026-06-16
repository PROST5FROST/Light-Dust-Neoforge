package com.luminousdust.client.particle.helpers;

import com.luminousdust.client.particle.DustParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class DustPhysicsHelper {
    public static void applyAmbientDrift(DustParticle particle, ClientLevel level) {
        float seed = (float) (particle.getX() + particle.getY() + particle.getZ());
        float time = (float) ((particle.getAge() + seed) * 0.15);

        double sinX = Mth.sin(time * 0.8f + seed);
        double cosZ = Mth.cos(time * 1.1f + seed);

        double driftDown = 0.00002 + (level.random.nextDouble() * 0.00005);
        double microTurbulence = (level.random.nextDouble() - 0.5) * 0.00012;

        particle.setXd(particle.getXd() + sinX * 0.0001 + microTurbulence);
        particle.setZd(particle.getZd() + cosZ * 0.0001 + (microTurbulence * 0.5));

        particle.setYd(particle.getYd() - (driftDown + (sinX * 0.00005)));
    }

    public static void applyThermalUpdraft(DustParticle particle, ClientLevel level) {
        if ((level.getGameTime() + particle.hashCode()) % 4L != 0L) return;

        BlockPos basePos = BlockPos.containing(particle.getX(), particle.getY(), particle.getZ());

        for (int i = 1; i <= 5; i++) {
            BlockPos checkPos = basePos.below(i);
            BlockState state = level.getBlockState(checkPos);

            double updraftForce = 0.0;

            if (state.is(Blocks.LAVA) || level.getFluidState(checkPos).is(FluidTags.LAVA)) {
                updraftForce = 0.02 / (i + 1);
            } else if (state.is(Blocks.CAMPFIRE) || state.is(Blocks.SOUL_CAMPFIRE)) {
                updraftForce = 0.01 / (i + 1);
            } else if (state.is(Blocks.FIRE) || state.is(Blocks.SOUL_FIRE) || state.is(Blocks.MAGMA_BLOCK)) {
                updraftForce = 0.01 / (i + 1);
            } else if (state.is(Blocks.TORCH) || state.is(Blocks.SOUL_TORCH) || state.is(Blocks.WALL_TORCH) || state.is(Blocks.SOUL_WALL_TORCH)) {
                updraftForce = 0.008 / (i + 1);
            }

            if (updraftForce > 0.0) {
                particle.setYd(particle.getYd() + updraftForce);
                particle.setXd(particle.getXd() + (level.random.nextDouble() - 0.5) * 0.0005);
                particle.setZd(particle.getZd() + (level.random.nextDouble() - 0.5) * 0.0005);
                break; // Found a heat source and pushing the particle up
            }
        }
    }

    public static void applyPlayerInteraction(DustParticle particle, ClientLevel level, Player player) {
        if (player.distanceToSqr(particle.getX(), particle.getY(), particle.getZ()) >= 4.0) return;

        // A jitter of particle in the air
        double range = 2.0;
        double dx = particle.getX() - player.getX();
        double dy = particle.getY() - (player.getY() + 1.0);
        double dz = particle.getZ() - player.getZ();
        double distSqr = (dx * dx + dy * dy + dz * dz);

        if (distSqr >= 3) return;
        if (distSqr < 0.0001) distSqr = 0.0001;

        double force = 1.0 / distSqr;

        if (force > 10.0) force = 10.0;

        double nx = dx * force;
        double ny = dy * force;
        double nz = dz * force;

        if (nx > 2.0) nx = 2.0; else if (nx < -2.0) nx = -2.0;
        if (ny > 2.0) ny = 2.0; else if (ny < -2.0) ny = -2.0;
        if (nz > 2.0) nz = 2.0; else if (nz < -2.0) nz = -2.0;

        double jitterX = (level.random.nextDouble() - 0.5) * 0.02;
        double jitterY = (level.random.nextDouble() - 0.5) * 0.05;
        double jitterZ = (level.random.nextDouble() - 0.5) * 0.02;

        // A slash with a sword (Or with something else)
        if (player.swingTime > 0) {
            Vec3 look = player.getLookAngle();
            if ((nx * look.x) + (ny * look.y) + (nz * look.z) > 0.5) {
                double slashForce = 0.002;
                particle.setXd(particle.getXd() + look.x * slashForce + (nx * 0.005) + jitterX);
                particle.setYd(particle.getYd() + look.y * slashForce + (ny * 0.005) + jitterY);
                particle.setZd(particle.getZd() + look.z * slashForce + (nz * 0.005) + jitterZ);
            }
        }

        // Well, a shield blocking.
        if (player.isUsingItem() && player.getUseItem().getItem() instanceof ShieldItem) {
            Vec3 look = player.getLookAngle();
            if ((nx * look.x) + (ny * look.y) + (nz * look.z) > 0.3) {

                double shieldPush = 0.02;

                particle.setXd(particle.getXd() + (nx * shieldPush) + jitterX);
                particle.setYd(particle.getYd() + (ny * shieldPush) + jitterY);
                particle.setZd(particle.getZd() + (nz * shieldPush) + jitterZ);
            }
        }

        // Walking through a particle
        Vec3 pVel = player.getDeltaMovement();
        double squaredSpeed = (pVel.x * pVel.x + pVel.z * pVel.z + pVel.y * pVel.y) * 0.5;
        if (squaredSpeed > 0.001) {

            double proximityFactorSqr = (range * range - distSqr) / range * range;

            if (proximityFactorSqr < 0) proximityFactorSqr = 0;

            double pushStrength = squaredSpeed * proximityFactorSqr;

            particle.setXd(particle.getXd() + (nx * pushStrength) + jitterX);
            particle.setZd(particle.getZd() + (nz * pushStrength) + jitterZ);

            if (Math.abs(pVel.y) > 0.5) {

                double verticalPush = pVel.y * proximityFactorSqr * 0.8;

                particle.setYd(particle.getYd() + verticalPush + (ny * 0.02) + jitterY);
            }
            // If the player is NOT moving at y coordinate
            else {
                particle.setYd(particle.getYd() + (pushStrength * 0.1) + jitterY);
            }
        }
    }

    public static void applyBlockBreakInteraction (DustParticle particle, ClientLevel level, Player player) {
        if (level.isClientSide) {
            HitResult hit = Minecraft.getInstance().hitResult;
            if (hit != null && hit.getType() == HitResult.Type.BLOCK) {
                BlockPos breakPos = ((BlockHitResult)hit).getBlockPos();
                if (player != null && player.swingTime > 0 && breakPos.distToCenterSqr(particle.getX(), particle.getY(), particle.getZ()) < 4.0) {
                    double dX = particle.getX() - (breakPos.getX() + 0.5);
                    double dY = particle.getY() - (breakPos.getY() + 0.5);
                    double dZ = particle.getZ() - (breakPos.getZ() + 0.5);

                    double distSqrBreak = dX * dX + dY * dY + dZ * dZ;

                    if (level.getBlockState(breakPos).isAir() && distSqrBreak < 3.0) {
                        if (distSqrBreak < 0.1) distSqrBreak = 0.1;

                        double forceFactor = 0.005 / distSqrBreak;
                        if (forceFactor > 0.2) forceFactor = 0.2;

                        double jitterX = (level.random.nextDouble() - 0.5) * 0.2;
                        double jitterY = (level.random.nextDouble() - 0.5) * 0.2;
                        double jitterZ = (level.random.nextDouble() - 0.5) * 0.2;

                        particle.setXd(particle.getXd() + (dX / forceFactor) + jitterX);
                        particle.setYd(particle.getYd() + (dY / forceFactor) + jitterY);
                        particle.setZd(particle.getZd() + (dZ / forceFactor) + jitterZ);
                    }
                }
            }
        }
    }
}
