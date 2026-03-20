package com.lightdust.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.ModList;

public final class DustConditions {
    private static final String LDL_ID = "lambdynlights";
    private static final String SDL_ID = "sodiumdynlights";

    public static boolean isHoldingLightSource(Player player) {
        return isLight(player.getMainHandItem()) || isLight(player.getOffhandItem());
    }

    private static boolean isLight(ItemStack stack) {
        if (stack.isEmpty()) return false;
            // Checking if the item has light
            BlockState state = net.minecraft.world.level.block.Block.byItem(stack.getItem()).defaultBlockState();
            return state.getLightEmission() > 0;
    }

    public static boolean isDLPresent() {
        // Checking if DynamicLight mods is loaded
        return ModList.get().isLoaded(LDL_ID) || ModList.get().isLoaded(SDL_ID);
    }


}
