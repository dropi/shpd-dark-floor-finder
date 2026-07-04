package org.example;

public class SeedFinder {

	private int mostMatchingFloors = 10; // Ignore everything below 10

	public SeedFinder(long startingSeed, long maxSeedsToScan, String outputFileName) {
		System.out.println("\n==============================================================");
		System.out.println("Dark floor finder");
		System.out.println("==============================================================\n");

		long seed = startingSeed;
		FileWriter file = new FileWriter(outputFileName);

		long startTime = System.currentTimeMillis();
		long checkInTime = startTime;

		for (long seedsChecked = 0; seedsChecked <= maxSeedsToScan; seedsChecked++) {
			int matchingFloors = testSeedForFeeling(seed);

			if (matchingFloors == mostMatchingFloors) {
				System.out.println("Matched previous best (" + mostMatchingFloors + "): " + DungeonSeed.convertToCode(seed));
				file.println(DungeonSeed.convertToCode(seed) + " " + matchingFloors);
			} else if (matchingFloors > mostMatchingFloors) {
				mostMatchingFloors = matchingFloors;
				System.out.println("New best: " + mostMatchingFloors + " Seed: " + DungeonSeed.convertToCode(seed));
				file.println(DungeonSeed.convertToCode(seed) + " " + matchingFloors);
			}

			seed++;

			long now = System.currentTimeMillis();
			if (now - checkInTime >= 60_000) {
				long runtime = now - startTime;
				System.out.println(String.format("Checked %s seeds (%.2f%%) in %.2f seconds (%.2fM seed/min)",
						seedsChecked,
						seedsChecked * 100f / maxSeedsToScan,
						runtime / 1000f,
						seedsChecked / 1_000_000f / (runtime / 60_000f)));
				checkInTime = now;
			}
		}
		System.out.println(String.format("Search done in %.2f seconds", (System.currentTimeMillis() - startTime) / 1000f));
	}

	private int testSeedForFeeling(long seed) {
		int feelingCount = 0;
		Dungeon.seed = seed;
		Dungeon.init();

		for (Dungeon.depth = 1; Dungeon.depth < 25; Dungeon.depth++) {
			int f = Dungeon.newFeeling();
			if (f == 3) // 3 = Enemies moving in the darkness
				feelingCount++;

			// Finish early if the number of floors remaining is not enough to match previous best
			// This noticably speeds up the search
			if ((24 - Dungeon.depth) - ((24 - Dungeon.depth) / 5) < mostMatchingFloors - feelingCount)
				return feelingCount;
		}

		return feelingCount;
	}
}
