# RandomEvents 2.9.6

## Build

Requirements: Maven 3.9+ and Java 17+. Run these commands from this directory:

```sh
mvn clean package
```

This creates `target/RandomEvents-2.9.6-paper-1.18.2-plus.jar`, compiled against Paper API 1.18.2.

A native Paper 26.3 build uses Java 25+; run:

```sh
mvn -Ppaper-26.3 clean package
```

This creates `target/RandomEvents-2.9.6-paper-26.3.jar`.

## Compatibility and testing

Source compilation was checked against Paper APIs 1.18.2, 1.19.4, 1.20.4, and 26.3. Older-version live-server checks and full minigame testing remain incomplete. The 2.9.5 build loaded on a Paper 26.3 server; this does not verify 2.9.6 runtime behavior.

Back up `plugins/RandomEvents` before replacing the jar. Lib1711 is not required. Optional plugin integrations are listed in the repository's main README.

MIT License; see `../LICENSE.txt`.
