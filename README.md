# RandomEvents Fix

A community-maintained modernization of the open-source RandomEvents Minecraft plugin.

## What's in 2.9.6

- Maven builds aimed at Paper 1.18.2+ and Paper 26.3.
- Compatibility code that resolves renamed Minecraft API features, including potion effects, entities, attributes, item tooltip flags, and particles.
- An internal replacement API; Lib1711 is no longer required.
- Optional integrations with other plugins. RandomEvents can compile without those plugins installed.

## Build targets

Run each command from the `RandomEvents/` directory. Each command creates the jar named in its row.

| Paper server | Build command | Java | Output |
|---|---|---:|---|
| 1.18.2+ compatibility build | `mvn clean package` | 17+ | `target/RandomEvents-2.9.6-paper-1.18.2-plus.jar` |
| 26.3 native build | `mvn -Ppaper-26.3 clean package` | 25+ | `target/RandomEvents-2.9.6-paper-26.3.jar` |

Pick the row matching your Paper server. The 1.18.2 build compiles against Paper API 1.18.2; API compilation checks also passed against 1.19.4, 1.20.4, and 26.3.

## Install

1. Back up `plugins/RandomEvents` and stop the server.
2. Place the matching jar in the server's `plugins/` directory.
3. Restart Paper and check the console log.

Keep the existing `plugins/RandomEvents` data folder so saved configuration and minigame files remain available.

## Optional integrations

CrackShot, PlaceholderAPI, WorldEdit, WorldGuard, Multiworld, Multiverse-Core, NametagEdit, LibsDisguises, NoteBlockAPI, and Citizens remain optional. Their features require the corresponding plugin.

## Companion project

`RandomEvents_Quests/` is included as a separate companion plugin. It was not changed in this compatibility update.

## License

MIT License; see [`LICENSE.txt`](LICENSE.txt).
