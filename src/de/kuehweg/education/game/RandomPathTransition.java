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

import java.util.Random;

import javafx.animation.PathTransition;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.shape.Path;
import javafx.util.Duration;

/**
 * @author Michael Kühweg
 */
public class RandomPathTransition {

	private final static double DURATION_VARIANCE = 0.2f;

	private final Random randomGenerator = new Random();

	private final RandomPath randomPath = new RandomPath();

	private final Level level;

	public RandomPathTransition(final Level level) {
		this.level = level;
	}

	public PathTransition nextPathTransition(final Scene scene, final Node node) {
		final Path path = randomPath.nextPath(scene, node);
		final PathTransition pathTransition = new PathTransition();
		pathTransition.setDuration(Duration.millis(randomDuration()));
		pathTransition.setPath(path);
		pathTransition.setNode(node);
		pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
		pathTransition.setCycleCount(level.getCycleCount());
		pathTransition.setAutoReverse(true);
		return pathTransition;
	}

	private long randomDuration() {
		final double average = level.getAverageTransitionDurationInMilliseconds();
		final double variance = 2.0 * randomGenerator.nextDouble() - 1.0;
		final double timeDifferenceByVariance = variance * DURATION_VARIANCE * average;
		final long duration = (long) (average + timeDifferenceByVariance);
		return duration;
	}
}
