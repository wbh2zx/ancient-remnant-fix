# Ancient Remnant Fix

Minecraft Forge 1.20.1 Mixin mod — prevents the Ancient Remnant (Cataclysm mod) from destroying Ancient Debris blocks when using the charge ability via The Fool (Age of Mythology) morph mod.

## Problem

When using The Fool mod to morph into an Ancient Remnant from L_Ender's Cataclysm, the charge ability destroys Ancient Debris blocks. According to official Cataclysm lore, Ancient Debris should stop the Ancient Remnant's charge.

## Root Cause

The Fool mod's morph system reimplements the Ancient Remnant's charge block-breaking logic in `MorphUtil.ChargeBlockBreaking()` — it does NOT call Cataclysm's original `ChargeBlockBreaking` method. This custom implementation lacks the Ancient Debris immunity check.

## Fix

Mixin injects a pre-scan at the head of `MorphUtil.ChargeBlockBreaking(LivingEntity, double, boolean)`. Before any blocks are destroyed, it scans the entity's bounding box for Ancient Debris. If found, the entity's horizontal velocity is zeroed and the method returns immediately, leaving all blocks intact.

## Compatibility

- Minecraft 1.20.1
- Forge 47.x
- L_Ender's Cataclysm 3.16
- The Fool (Age of Mythology) 0.2.1

## Installation

Place `ancient_remnant_fix-1.0.0.jar` in your `mods` folder.

## Building

```bash
./gradlew build
```

Requires the following jars in `libs/`:
- `L_Enders_Cataclysm-3.16.jar`
- `ageofmythology-0.2.1-all.jar`

## License

MIT
