package com.lightdust.compat;

import net.neoforged.fml.ModList;

import java.util.List;

public class DlCompat {
    // LDL - LambDynamicLights
    // SDL - SodiumDynamicLights (Maybe deprecated lil bit)
    private static final String LDL_ID = "lambdynlights";
    private static final String SDL_ID = "sodiumdynamiclights";

    public static final boolean isDlPresent;


    static {
        isDlPresent = ModList.get().isLoaded(LDL_ID) || ModList.get().isLoaded(SDL_ID);
    }
}
