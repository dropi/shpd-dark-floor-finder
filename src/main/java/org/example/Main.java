package org.example;

public class Main {

    public static void main(String[] args) {
        long startingSeed = 0;
        long numberOfSeedsToScan = DungeonSeed.TOTAL_SEEDS;
        String outputFileName = null;

        try {
            if (args.length >= 1)
                startingSeed = DungeonSeed.convertFromText(args[0]);
            if (args.length >= 2)
                numberOfSeedsToScan = Long.parseLong(args[1]);
            if (args.length >= 3)
                outputFileName = args[2];

        } catch (Exception e) {
            System.out.println("Usage:");
            System.out.println("shpd-dark-floor-finder.jar [STARTING_SEED] [NUMBER_OF_SEEDS_TO_SCAN] [OUTPUT_FILE_NAME]");
            return;
        }

        new SeedFinder(startingSeed, numberOfSeedsToScan, outputFileName);
    }
}
