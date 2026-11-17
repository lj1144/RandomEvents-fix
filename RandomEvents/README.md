# RandomEvents Fix

A modernized build of the open-source RandomEvents Minecraft plugin.

## Target server

- Paper 26.3
- Java 25
- Maven 3.9 or newer

This project currently builds against Paper 26.3. It has not yet been verified in a live server here because the Paper launcher could not reach Mojang's server-file download host.

## Build

Open a terminal in the `RandomEvents` directory and run:

```sh
mvn clean package
```

The plugin jar is written to `target/RandomEvents-2.9.5.jar`. Install that file in the server's `plugins` directory, then restart Paper.

## Optional integrations

RandomEvents runs without these plugins. Their integrations activate only when the matching optional plugin is installed and enabled: CrackShot, PlaceholderAPI, WorldEdit, WorldGuard, Multiworld, Multiverse-Core, NametagEdit, LibsDisguises, NoteBlockAPI, and Citizens. Paper API and the shaded HikariCP, SLF4J, and Commons Lang dependencies are handled by Maven.

## Configuration notes

The first server launch creates the plugin data folder and default configuration. Back up existing RandomEvents data before replacing an installed jar or changing server versions. MySQL remains disabled unless enabled in the configuration.

## License

The RandomEvents project is distributed under the MIT License in `LICENSE.txt`.
