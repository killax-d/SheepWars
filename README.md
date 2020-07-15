# SheepWars

SheepWars is a minecraft minigame with exploding sheep's in which two teams have to fight with sheep launched. The winner is the last team alive. The sheeps have several abilities. For winning, players can choose a kit and during the war, some boosters will randomly come up.

## About

This SheepWars version comes with 8 kits:
- **Archer**
  - *Additional arrow knockback & damage*
    - lvl 1. Knockback I
    - lvl 2. Knockback I
    - lvl 3. Knockback I, Damage I
    - lvl 4. Knockback I, Damage I
    - lvl 5. Knockback II, Damage I
  - **8 ♥** *+1 ♥ per level*
  - **x125% speed** *+25% speed per level*

- **BonusSheep**
  - **Additional sheep in bonus** *+10% chance per level*
  - **8 ♥** *+1 ♥ per level*

- **HardnessSheep**
  - **115% sheep life** *+15% life per level*
  - **10 ♥** *+1 ♥ per level*

- **Mobility**
  - *Additional fall protection*
    - lvl 1. Feather Falling I
    - lvl 2. Feather Falling II
    - lvl 3. Feather Falling III
    - lvl 4. Feather Falling IV
    - lvl 5. Feather Falling IV
  - **8 ♥** *+1 ♥ per level*
  - **x107.5% speed** *+7.5% speed per level*

- **Pyroman**
  - *Additional protection and ability*
    - lvl 1. Fire Protection I
    - lvl 2. Fire Protection I, Fire Aspect I
    - lvl 3. Fire Protection II, Fire Aspect I
    - lvl 4. Fire Protection II, Fire Aspect I, Fire Arrow I
    - lvl 5. Fire Protection III, Fire Aspect II, Fire Arrow I
  - **10 ♥** *+1 ♥ per level*
  - **Fire Sheep** on start

- **Support**
  - **8 ♥** *+1 ♥ per level*
  - **Heal Sheep** on start

- **SwordsMan**
  - *Additional damage and material*
    - lvl 1. Wooden Sword, Damage I
    - lvl 2. Wooden Sword, Damage I, Knockback I
    - lvl 3. Wooden Sword, Damage II, Knockback I
    - lvl 4. Stone Sword, Knockback I
    - lvl 5. Stone Sword, Knockback II
  - **10 ♥** *+1 ♥ per level*
  - **x102.5% speed** *+2.5% speed per level*

## Installation

This plugin has been devloped for PaperMC 1.14.4, it require a `mapsettings.json` directly in the world folder.

```json
{
  "name": "SheepWars MAP",
  "nbPlayers": 18,
  "minPlayers": 2,
  "gameDuration": 1,
  "bonusCooldown": 75,
  "sheepGive": 5,
  "mapAuthors": [
    "Killax"
  ],
  "spawn": {
    "x": 270.5,
    "y": 91.0,
    "z": 80.5,
    "yaw": 90.0,
    "pitch": 0.0
  },
  "bonusSpawn": [
    {
      "x": 176.5,
      "y": 56.5,
      "z": 78.5,
      "yaw": 0.0,
      "pitch": 0.0
    },
    {
      ...
    }
  ],
  "teams": [
    {
      "name": "Red",
      "nbPlayers": 9,
      "spawnPoints": [
        {
          "x": 156.5,
          "y": 46.0,
          "z": 59.5,
          "yaw": 0.0,
          "pitch": 0.0
        },
        {
          ...
        }
      ]
    },
    {
      "name": "Blue",
      "nbPlayers": 9,
      "spawnPoints": [
        {
          "x": 135.5,
          "y": 46.0,
          "z": 115.5,
          "yaw": -180.0,
          "pitch": 0.0
        },
        {
          ...
        }
      ]
    }
  ]
}

```

## Usage

Simply put the jar file into the plugin folder and upload a world with a `mapsettings.json` inside
- `/!\ Map is destructive, be sure to make a backup of the world before running a game`
- This minigame is compatible with `InfiniteGame` extension, see [InfiniteGame](https://github.com/killax-d/InfiniteGame)

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License
[GPLv3](https://choosealicense.com/licenses/gpl-3.0/), see `LICENSE`