# Ore-Terrain-Filter

A configurable terrain-based ore generation filter for the Mindustry map editor.

## Features

- Select individual floors to exclude from ore placement.
- Discovers eligible floors from loaded content, including modded floors.
- Removes only ore overlays on selected floors.
- Preserves terrain, walls, spawn markers, and non-ore overlays.
- Integrates with the native map generation filter list.
- Can be used during map generation or applied to an existing map through the editor's filter workflow.

## Build

Requires JDK 17 and Gradle.

```sh
gradle build
```

The mod JAR is created in `build/libs`.

## Compatibility

Targeted at Mindustry v8 Build 160.7. The mod uses the native `GenerateFilter` API and does not replace the map generator.

## Behavior

For each tile, the filter checks its current floor and overlay. If the overlay is an ore block and the floor is selected, the overlay is changed to air. No floor or wall data is changed.
