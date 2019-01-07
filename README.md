# DeathWatch

??? TODO: Write a readme.

Forge mod development getting started guide: http://mcforge.readthedocs.io/en/latest/gettingstarted/

## Initial setup

- Windows: `gradlew setupDecompWorkspace`
- Linux/Mac OS: `./gradlew setupDecompWorkspace`

## Build

- Windows: `gradelw build`
- Linux/Mac OS: `./gradelw build`

## Copy/pasted from example README.txt because it's potentially useful

	If you do not care about seeing Minecraft's source code you can replace "setupDecompWorkspace" with one of the following:
	"setupDevWorkspace": Will patch, deobfuscate, and gather required assets to run minecraft, but will not generate human readable source code.
	"setupCIWorkspace": Same as Dev but will not download any assets. This is useful in build servers as it is the fastest because it does the least work.
