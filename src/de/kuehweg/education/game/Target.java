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

import javafx.animation.Animation.Status;
import javafx.animation.Transition;
import javafx.scene.Node;

/**
 * @author Michael Kühweg
 */
public abstract class Target {

	private Node screenRepresentation;

	private Transition transition;

	private boolean scored;

	public Node getScreenRepresentation() {
		return screenRepresentation;
	}

	public void setScreenRepresentation(final Node screenRepresentation) {
		this.screenRepresentation = screenRepresentation;
	}

	public Transition getTransition() {
		return transition;
	}

	public void setTransition(final Transition transition) {
		this.transition = transition;
	}

	public boolean isScored() {
		return scored;
	}

	public void setScored(final boolean scored) {
		this.scored = scored;
	}

	public boolean isTimedOut() {
		return getTransition() != null && getTransition().getStatus() == Status.STOPPED;
	}

	public abstract boolean isDangerous();

}
