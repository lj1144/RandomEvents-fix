# RandomEvents Fix

This repository contains the RandomEvents plugin and its companion RandomEvents Quests plugin. The maintained Paper 26.3 source is in [`RandomEvents/`](RandomEvents/).

## Build RandomEvents

Requirements: Java 25 and Maven 3.9 or newer.

```sh
cd RandomEvents
mvn clean package
```

The jar is created at `RandomEvents/target/RandomEvents-2.9.5.jar`. Copy it into your Paper server's `plugins` directory and restart the server.

This source compiles against Paper 26.3. Live startup validation is still pending because the build environment could not reach Mojang's server-file download host. Optional plugin integrations are listed in `RandomEvents/README.md`.

License: MIT (`LICENSE.txt`).
