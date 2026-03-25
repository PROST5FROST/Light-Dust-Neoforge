

# Light Dust

Light Dust is a lightweight client side mod that adds floating dust to your world. Built with interactive physics, dust reacts to light, weather, and the exact biome you are exploring.

if you want a more in depth guide as to how to configure this mod check out the new [wiki!](https://github.com/Swiss8191/Light-Dust/wiki).

## Features

-   **Biome Based Dust:** Dust naturally tints and changes behavior based on the biome you are in.<br><br>
-   **Dynamic Dust Tints:** Ambient dust gets tinted by the color of nearby light sources. Fast-moving action dust from breaking blocks or heavy landings also dynamically inherits these local colors.<br><br>
-   **Compatible with dynamic lights:** Ambient dust reacts to handheld light sources. Holding a torch, lantern, or other luminous item will spawn an area of dust around you. Fully compatible with items equipped in Curios and Accessories slots. _(Note: You still need to install a dynamic lights mod to actually emit light. This mod only handles the dust)._<br><br>
-  **Advanced Wind Aerodynamics:** The weather wind system simulates fluid dynamics. causes dust to swarm and sweep when out in the open. Wind exposure uses a sky light gradient, allowing drafts to naturally work on dust under trees and overhangs.<br><br>
-   **Physics Interactions:** Dust reacts to movement, combat, and items. Swinging a sword, sprinting, or raising a shield pushes particles away within a configurable interaction radius.<br>
-   **Dust Glinting:** Dust particles subtly glint when you get close to them. In dark environments, dust near light sources softly illuminates and fades as it drifts away.<br><br>
-   **Thermal Updrafts & Shockwaves:** Particles catch thermal updrafts to swirl upwards over heat sources, and get pushed away by shockwaves from loud noises (explosions).<br><br>
-   **Heavy Landings:** Falling 3 or more blocks kicks up a scaling ring of dust upon impact, pushing preexisting ambient dust away from the landing zone.<br><br>
-   **Configurable:** Almost everything is adjustable, with settings for performance, physics, and visuals.
    
----------

## Configuration

All values are adjustable in the `config/lightdust/` folder (split into `main.toml`, `colors.toml`, and `experimental.toml`). The `main.toml` is divided into categories:

### Spawning & Performance

-   `ambientRadius` (Default: `10`): How far away from the player (in blocks) dust will attempt to spawn. Keep this lower than the Hard Cap.<br>
-   `ambientHardCapRadius` (Default: `12`): The absolute maximum distance dust can exist. Particles further than this are deleted instantly to prevent buildup.<br>
-   `ambientBlockCap` (Default: `14`): The maximum density of dust allowed in a single block.<br>
-   `minBlockLight` (Default: `6`): The minimum block light level required for ambient dust to spawn.<br>
-   `daytimeLightDiff` (Default: `5`): The minimum difference between Block Light and Sky Light required for dust to spawn during the day.<br>
-   `falloffDistance` (Default: `6`): Distance from the player where dust density starts to decrease.<br>
-   `falloffMultiplier` (Default: `0.3`): Multiplier applied to the max particle cap beyond the falloff distance.<br>
-   `enableOcclusionCulling` (Default: `true`): Uses LOS raytracing to prevent dust from spawning behind solid walls or in unseen caves. Highly recommended for performance.<br>
    

### Visuals & Environment

-   `ambientDustOpacity` (Default: `0.22`): Base opacity for ambient dust. (Recommended: `0.22` for Vanilla, `0.45+` if using Shaders).<br>
-   `particleSize` (Default: `0.022`): Adjust the visual size of the ambient dust particles.<br>
-   `particleLifetime` (Default: `200`): Control exactly how long (in ticks) dust particles stick around before fading away.<br>
-   `windSpeedClear` / `windSpeedRain` / `windSpeedThunder`: Adjusts the speed of the global wind drifts based on current weather.<br>
-   `disableDuringRain` / `disableDuringThunder`: Toggles to prevent outdoor dust from spawning or existing during specific weather events.<br>

### Interactions & Actions

-   `playerInteractRadius` (Default: `4.0`): The radius at which player movements physically interact with and push dust particles.<br>
-   `breakParticleCount` / `breakParticleSpeed`: Controls the amount and burst speed of dust when breaking blocks.<br>
-   `actionDustGravity` (Default: `0.002`): How fast block breaking dust falls downwards over time.<br>
-   `actionDustBounce` (Default: `0.2`): How bouncy block breaking dust is when ricocheting off walls or ceilings.<br>
-   `heavyLandingMaxParticles` / `heavyLandingParticleMultiplier`: Controls how many particles spawn when taking fall damage.<br>
-   `heavyLandingUpwardSpeed` / `heavyLandingOutwardSpeed`: Controls the speed and shape of the heavy landing dust ring.<br>
-   `heavyLandingAmbientPush` / `heavyLandingAmbientRadius`: Controls the shockwave that pushes ambient dust away when you land.<br>
-   `heatBlocks`: List of blocks that emit heat, causing dust particles to swirl upwards in a thermal updraft. Format: `modid:block_name=speed,vertical_reach,horizontal_radius`.<br>
    

### Dynamic Lights Compat

-   `enableHandheldLights` (Default: `false`): If true, items held in your hand will act as fake light sources, allowing dust to spawn and coloring the dust.<br>
-   `handheldLightItems`: List of items/tags and their light properties. Format: `'modid:item_name=radius,#HEXCOLOR'` or `'#modid:tag=radius,#HEXCOLOR'`.<br>

### Experimental Features (`experimental.toml`)

-   `enableAdvancedWindMath` (Default: `true`): Enable complex spatial turbulence and sweeping wind physics. Disable for an FPS boost on low-end CPUs.<br>
-   `enableWindDeflection` (Default: `true`): Allows wind to realistically blow dust down tunnels and bounce off obstacles. Disable for better performance.<br>
-   `enableDustSettling` (Default: `true`): Allows dust particles to visually settle and rest when hitting the ground.<br>
-   `enableEntityDisturbance` (Default: `true`): Allows non-player entities (mobs/projectiles) to kick up and disturb dust. This is hard  capped to only track the 6 closest moving entities.<br>
-   `entityPushStrength` (Default: `0.05`): How strongly non-player entities push dust when moving through it.<br>
    

### Color & Tinting Settings (`colors.toml`)

-   `tintStrength` (Default: `0.45`): Adjusts how strongly colored lights (like soul fire) tint the dust. `0.0` = no tint, `1.0` = full color.<br>
-   `customTints`: A list of blocks and their hex colors for dust tinting. Format: `modid:block_name=#RRGGBB`. Note: The block must actually emit light for the tint to apply.<br>
-   `customBiomeTints`: Define base ambient colors for any vanilla or modded biome using hex codes.<br>
-   `caveBiomeTriggers`: Map specific blocks (like modded ores or plants) to a hex color. The mod will automatically change the cave's dust to that color when enough of those blocks are nearby underground.
    

----------

## Q&A

**Q: Does this work with true dark mods?**

**A:** Yes. It should work with them. However, you may need to change the opacity of the dust.

**Q: Does this work with Shaders?**

**A:** Yes. However, shaders often change how transparency renders. If the dust looks too invisible, increase `ambientDustOpacity` in the config (try `0.45` or higher).

**Q: Will this cause lag in big modpacks?**

**A:** It is designed specifically to be lightweight. The mod has a ton of optimization options such as the newly added LOS and Hemisphere system aswell as Occlusion Culling to skip particle rendering behind solid walls. If you still drop frames, you can disable `enableAdvancedWindMath` or lower the particle caps in the configs.

**Q: Can I change the color of the dust?**

**A:** Yes! You can change the tint of light sources (`customTints`), the base color of any biome (`customBiomeTints`), and trigger colors underground using specific blocks (`caveBiomeTriggers`) in the `colors.toml` config.

**Q: Does this work with modded biomes and caves?**

**A:** Yep. Just add the modded biome ID or the modded blocks to the respective lists in the config, assign a hex color, and it should work just fine.

**Q: How do I disable the Block Break particles?**

**A:** Set `breakParticleCount` to `0` in the config.
