/*
 * Copyright (c) 2016, Michael Kühweg
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package de.kuehweg.education.game;

/**
 * @author Michael Kühweg
 */
public class Level {

	private static final int DANGEROUS_OBJECTS_TOLERANCE = 3;

	private static final double MIN_PERCENTAGE_OF_DANGEROUS_OBJECTS = 0.25f;
	private static final double MAX_PERCENTAGE_OF_DANGEROUS_OBJECTS = 0.5f;
	private static final double INCREASE_PERCENTAGE_OF_DANGEROUS_OBJECTS = 0.1f;

	private static final long MIN_AVERAGE_TRANSITION_DURATION_IN_MILLISECONDS = 4000;
	private static final long MAX_AVERAGE_TRANSITION_DURATION_IN_MILLISECONDS = 8000;
	private static final double MODIFICATION_FACTOR_AVERAGE_TRANSITION_DURATION = 0.75f;

	private static final int MIN_CYCLECOUNT = 4;
	private static final int MAX_CYCLECOUNT = 8;
	private static final int INCREASE_CYCLECOUNT = -1;

	private static final int MIN_OBJECTS_AVAILABLE = 20;
	private static final int MAX_OBJECTS_AVAILABLE = 40;
	private static final int INCREASE_OBJECTS_AVAILABLE = 2;

	// TODO >>>
	// private static final int START_BONUS = 5;
	// private static final int STEP_BONUS = 5;
	// TODO <<<

	private double percentageOfDangerousObjects;

	private long averageTransitionDurationInMilliseconds;

	private int cycleCount;

	private int objectsAvailable;

	// TODO >>>
	// private int bonus;
	// TODO <<<

	public Level() {
		reset();
	}

	public void reset() {
		percentageOfDangerousObjects = MIN_PERCENTAGE_OF_DANGEROUS_OBJECTS;
		averageTransitionDurationInMilliseconds = MAX_AVERAGE_TRANSITION_DURATION_IN_MILLISECONDS;
		cycleCount = MAX_CYCLECOUNT;
		objectsAvailable = MIN_OBJECTS_AVAILABLE;

		// TODO >>>
		// bonus = START_BONUS;
		// TODO <<<
	}

	public void increase() {
		percentageOfDangerousObjects += INCREASE_PERCENTAGE_OF_DANGEROUS_OBJECTS;
		averageTransitionDurationInMilliseconds = Math
				.round(averageTransitionDurationInMilliseconds * MODIFICATION_FACTOR_AVERAGE_TRANSITION_DURATION);
		cycleCount += INCREASE_CYCLECOUNT;
		objectsAvailable += INCREASE_OBJECTS_AVAILABLE;

		// TODO >>>
		// bonus += STEP_BONUS;
		// TODO <<<

		assertMinAndMaxLevels();
	}

	private void assertMinAndMaxLevels() {
		if (percentageOfDangerousObjects > MAX_PERCENTAGE_OF_DANGEROUS_OBJECTS) {
			percentageOfDangerousObjects = MAX_PERCENTAGE_OF_DANGEROUS_OBJECTS;
		}
		if (averageTransitionDurationInMilliseconds < MIN_AVERAGE_TRANSITION_DURATION_IN_MILLISECONDS) {
			averageTransitionDurationInMilliseconds = MIN_AVERAGE_TRANSITION_DURATION_IN_MILLISECONDS;
		}
		if (cycleCount < MIN_CYCLECOUNT) {
			cycleCount = MIN_CYCLECOUNT;
		}
		if (objectsAvailable > MAX_OBJECTS_AVAILABLE) {
			objectsAvailable = MAX_OBJECTS_AVAILABLE;
		}

		// TODO >>>
		// bonus may go up and up and up...
		// TODO <<<
	}

	public double getPercentageOfDangerousObjects() {
		return percentageOfDangerousObjects;
	}

	public long getAverageTransitionDurationInMilliseconds() {
		return averageTransitionDurationInMilliseconds;
	}

	public int getDangerousObjectsTolerance() {
		return DANGEROUS_OBJECTS_TOLERANCE;
	}

	public int getCycleCount() {
		return cycleCount;
	}

	public int getObjectsAvailable() {
		return objectsAvailable;
	}

	// TODO >>>
	// public int getBonus() {
	// return bonus;
	// }
	// TODO <<<
}
