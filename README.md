# shpd-dark-floor-finder

[Shattered Pixel Dungeon](https://github.com/00-Evan/shattered-pixel-dungeon)

The goal of this project is to find ShPD seeds with the most dark floors possible

## How to run

```sh
java -jar shpd-dark-floor-finder.jar [STARTING_SEED] [NUMBER_OF_SEEDS_TO_SCAN] [OUTPUT_FILE_NAME]
```

- `STARTING_SEED` - where to start the search. Can be a number or a code (like `ABC-DEF-GHI`). Default is 0.
- `NUMBER_OF_SEEDS_TO_SCAN` - how many seeds to scan before the program terminates. Default is 5429503678976.
- `OUTPUT_FILE_NAME` - if this is specified, the program will write notable seeds to a file.

## How to build

Clone the repo and run

```sh
gradle build
```

.jar file will be generated in `build/libs`

## Approach

I took the levelgen logic from the game and stripped out everything that doesn't directly contribute to generating floor feelings. The resulting program just checks millions of seeds per second with brute force. Due to how the game scrambles the seeds and how floor generation depends on random results from previous floors, that's the best I could come up with.

## Findings

So far I've scanned all seeds from `AAA-AAA-AAA` to `BZZ-ZZZ-ZZZ`, which is 1/13 of all possible seeds. I found several seeds with 13 out of 19 dark floors:

- AGN-SGQ-NHB
- AGX-RNB-BEK
- AIM-FTC-JLS
- ANN-LRN-ZAJ
- ATC-MFS-SMW
- AZO-NPG-PFA
- BEC-ORQ-HWO
- BEP-IXA-JJV
- BIG-DSU-SBL
- BJA-XCP-KMS
- BLK-ILJ-JLB
- BPO-BQI-RYM
- BUZ-NCJ-VJK
- BYC-EGQ-KGL
