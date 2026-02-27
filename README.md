# Light Dust

Light Dust is a lightweight, client-side mod that adds floating dust to illuminated areas. Light Dust is built with interactive physics and high-performance optimization in mind.

## Features

*   **Atmospheric Lighting:** Dust particles naturally spawn in illuminated areas (configurable, defaults to a light level of 6 or higher).
*   **Dynamic Dust Tints:** Ambient dust gets tinted by the color of nearby light sources.
*   **Physics Interactions:** Dust reacts to player movement, combat, and items. Swinging a sword or raising a shield pushes particles away within a configurable interaction radius.
*   **Dust Movement:** Particles have smooth fade-in/out animations, turbulence, and settling physics to look 'natural'.
*   **Configurable:** You can configure most of the things in this mod, expect more config options to come.

***

## Configuration

All values are adjustable in `config/lightdust-client.toml`.

### General Settings

*   `ambientRadius` (Default: `10`): How far away from the player (in blocks) dust will attempt to spawn. Keep this lower than the Hard Cap.
*   `ambientHardCapRadius` (Default: `13`): The absolute maximum distance dust can exist. Particles further than this are deleted instantly to prevent buildup.
*   `ambientBlockCap` (Default: `14`): The maximum density of dust allowed in a single block.
*   `ambientDustOpacity` (Default: `0.22`): Base opacity for ambient dust. (Recommended: `0.22` for Vanilla, `0.45+` if using Shaders).
*   `minBlockLight` (Default: `6`): The minimum block light level required for ambient dust to spawn.
*   `daytimeLightDiff` (Default: `5`): The minimum difference between Block Light and Sky Light required for dust to spawn during the day (helps prevent dust from spawning in fully sun-lit houses).

### Color & Tinting Settings

*   `tintStrength` (Default: `0.6`): Adjusts how strongly colored lights (like soul fire) tint the dust. `0.0` = no tint, `1.0` = full color.
*   `customTints`: A list of blocks and their hex colors for dust tinting. Format: `modid:block_name=#RRGGBB`. Note: The block must actually emit light for the tint to apply.

### Particle & Physics Settings

*   `particleSize` (Default: `0.022`): Adjust the visual size of the ambient dust particles.
*   `particleLifetime` (Default: `200`): Control exactly how long (in ticks) dust particles stick around before fading away.
*   `playerInteractRadius` (Default: `4.0`): The radius at which player movements (swinging weapons, raising shields, or sprinting) physically interact with and push the dust particles.
*   `breakParticleCount` (Default: `12`): Number of extra dust particles spawned when a block is broken.
*   `breakParticleSpeed` (Default: `0.1`): How fast the dust shoots out from a broken block.

## Q&A

**Q: Does this work with true dark mods?**

**A:** Yes. It should work with them. However, you may need to change the opacity of the dust.

**Q: Does this work with Shaders?**

**A:** Yes. However, shaders often change how transparency renders. If the dust looks too invisible, increase `ambientDustOpacity` in the config (try `0.45` or higher).

**Q: Will this cause lag in big modpacks?**

**A:** It is designed specifically to not cause lag. In the case that it does, the configs have some options to make it less intense.

**Q: Can I change the color of the dust?**

**A:** Yes! The dust will be tinted the color that is defined in the configs. You can also assign custom hex colors to any modded block via the `customTints` setting in the config and adjust the strength of the tinting.

**Q: How do I disable the Block Break particles?**

**A:** Set `breakParticleCount` to `0` in the config.
