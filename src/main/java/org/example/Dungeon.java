/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2026 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package org.example;

public class Dungeon {

	public static enum LimitedDrops {
		STRENGTH_POTIONS,
		UPGRADE_SCROLLS,
		ARCANE_STYLI,
		ENCH_STONE,
		INT_STONE,
		TRINKET_CATA;

		public int count = 0;

		public boolean dropped() {
			return count != 0;
		}

		public void drop() {
			count = 1;
		}

		public static void reset() {
			for (LimitedDrops lim : values()) {
				lim.count = 0;
			}
		}
	}

	public static int depth;
	public static long seed;
	public static java.util.Random levelSeedGenerator;

	public static void init() {
		LimitedDrops.reset();
		levelSeedGenerator = new java.util.Random(Random.scrambleSeed(seed));
		levelSeedGenerator.nextLong(); // Fire once to skip f0
	}

	public static int newFeeling() {
		int feeling = -1;
		Random.pushGenerator(levelSeedGenerator.nextLong());

		if (depth % 5 != 0) {
			posNeeded();
			souNeeded();
			asNeeded();
			enchStoneNeeded();
			intStoneNeeded();
			trinketCataNeeded();

			if (depth > 1) {
				feeling = Random.Int(14);
			}
		}

		return feeling;
	}

	public static void posNeeded() {
		// 2 POS each floor set
		int posLeftThisSet = 2 - (LimitedDrops.STRENGTH_POTIONS.count - (depth / 5) * 2);
		if (posLeftThisSet <= 0)
			return;

		int floorThisSet = (depth % 5);

		// pos drops every two floors, (numbers 1-2, and 3-4) with a 50% chance for the
		// earlier one each time.
		int targetPOSLeft = 2 - floorThisSet / 2;
		if (floorThisSet % 2 == 1 && Random.Int(2) == 0)
			targetPOSLeft--;

		if (targetPOSLeft < posLeftThisSet)
			LimitedDrops.STRENGTH_POTIONS.count++;
	}

	public static void souNeeded() {
		int souLeftThisSet;
		// 3 SOU each floor set
		souLeftThisSet = 3 - (LimitedDrops.UPGRADE_SCROLLS.count - (depth / 5) * 3);
		if (souLeftThisSet <= 0)
			return;

		int floorThisSet = (depth % 5);
		// chance is floors left / scrolls left
		if (Random.Int(5 - floorThisSet) < souLeftThisSet)
			LimitedDrops.UPGRADE_SCROLLS.count++;
	}

	public static void asNeeded() {
		// 1 AS each floor set
		int asLeftThisSet = 1 - (LimitedDrops.ARCANE_STYLI.count - (depth / 5));
		if (asLeftThisSet <= 0)
			return;

		int floorThisSet = (depth % 5);
		// chance is floors left / scrolls left
		if (Random.Int(5 - floorThisSet) < asLeftThisSet)
			Dungeon.LimitedDrops.ARCANE_STYLI.count++;
	}

	public static void enchStoneNeeded() {
		// 1 enchantment stone, spawns on chapter 2 or 3
		if (!LimitedDrops.ENCH_STONE.dropped()) {
			int region = 1 + depth / 5;
			if (region > 1) {
				int floorsVisited = depth - 5;
				if (floorsVisited > 4)
					floorsVisited--; // skip floor 10
				if (Random.Int(9 - floorsVisited) == 0)
					LimitedDrops.ENCH_STONE.drop();
			}
		}
		return;
	}

	public static void intStoneNeeded() {
		// one stone on floors 1-3
		if (depth < 5 && !LimitedDrops.INT_STONE.dropped() && Random.Int(4 - depth) == 0)
			LimitedDrops.INT_STONE.drop();
	}

	public static void trinketCataNeeded() {
		// one trinket catalyst on floors 1-3
		if (depth < 5 && !LimitedDrops.TRINKET_CATA.dropped() && Random.Int(4 - depth) == 0)
			LimitedDrops.TRINKET_CATA.drop();
	}
}
