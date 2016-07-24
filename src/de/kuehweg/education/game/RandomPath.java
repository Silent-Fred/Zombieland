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

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;

/**
 * @author Michael Kühweg
 */
public class RandomPath {

	private final Random randomGenerator = new Random();

	public Path nextPath(final Scene scene, final Node node) {
		final Path path = new Path();
		path.getElements().add(randomMoveTo(scene, node));
		path.getElements().add(randomQuadCurveTo(scene, node));
		return path;
	}

	private MoveTo randomMoveTo(final Scene scene, final Node node) {
		final double leftX = -pushOutside(node);
		MoveTo moveTo;
		moveTo = new MoveTo();
		moveTo.setX(leftX);
		moveTo.setY(randomYInStartRange(scene, node));
		return moveTo;
	}

	private QuadCurveTo randomQuadCurveTo(final Scene scene, final Node node) {
		final double rightX = scene.getWidth() + pushOutside(node);
		final QuadCurveTo quadTo = new QuadCurveTo();
		quadTo.setControlX(randomControlX(scene));
		quadTo.setControlY(randomControlY(scene));
		quadTo.setX(rightX);
		quadTo.setY(randomYInStartRange(scene, node));
		return quadTo;
	}

	private double pushOutside(final Node node) {
		return Math.max(node.getBoundsInLocal().getWidth(), node.getBoundsInLocal().getHeight());
	}

	private double randomYInStartRange(final Scene scene, final Node node) {
		return -randomGenerator.nextDouble() * startRangeHeight(scene) + node.getBoundsInLocal().getHeight();
	}

	private double startRangeHeight(final Scene scene) {
		return scene.getHeight() / 3.0f;
	}

	private double randomControlX(final Scene scene) {
		return controlRangeLeft(scene) + randomGenerator.nextDouble() * controlRangeWidth(scene) / 2;
	}

	private double randomControlY(final Scene scene) {
		return controlRangeTop(scene) + randomGenerator.nextDouble() * controlRangeHeight(scene);
	}

	private double controlRangeLeft(final Scene scene) {
		return (scene.getWidth() - controlRangeWidth(scene)) / 2.0f;
	}

	private double controlRangeTop(final Scene scene) {
		return -scene.getHeight() * 1.5f;
	}

	private double controlRangeWidth(final Scene scene) {
		return scene.getWidth() / 2.0f;
	}

	private double controlRangeHeight(final Scene scene) {
		return scene.getHeight() / 4.0f;
	}

}
