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

import java.util.ArrayDeque;

public class Random {

	private static ArrayDeque<java.util.Random> generators;
	static {
		generators = new ArrayDeque<>();
	}

	public static void resetGenerators() {
		generators.clear();
	}

	public static void pushGenerator(long seed) {
		generators.push(new java.util.Random(scrambleSeed(seed)));
	}

	// scrambles a given seed, this helps eliminate patterns between the outputs of
	// similar seeds
	// Algorithm used is MX3 by Jon Maiga (jonkagstrom.com), CC0 license.
	public static long scrambleSeed(long seed) {
		seed ^= seed >>> 32;
		seed *= 0xbea225f9eb34556dL;
		seed ^= seed >>> 29;
		seed *= 0xbea225f9eb34556dL;
		seed ^= seed >>> 32;
		seed *= 0xbea225f9eb34556dL;
		seed ^= seed >>> 29;
		return seed;
	}

	public static void popGenerator() {
		generators.pop();
	}

	// returns a uniformly distributed int in the range [0, max)
	public static int Int(int max) {
		return generators.peekFirst().nextInt(max);
	}

	// returns a uniformly distributed long in the range [-2^63, 2^63)
	public static long Long() {
		return generators.peekFirst().nextLong();
	}

	// returns a mostly uniformly distributed long in the range [0, max)
	public static long Long(long max) {
		long result = Long();
		if (result < 0)
			result += Long.MAX_VALUE;
		// modulo isn't perfect, but as long as max is reasonably below 2^63 it's close
		// enough
		return result % max;
	}
}
