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

import java.util.ArrayList;
import java.util.Collection;

import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * @author Michael Kühweg
 */
public class LevelTargets {

	private final Collection<Target> targets;
	private final Score score;
	private Pane parent;

	// make sure only one finish event per level is going to be fired
	private FinishEvent finishEvent;

	public LevelTargets(final Collection<Target> targets, final Score score) {
		this.targets = new ArrayList<>(targets);
		this.score = score;
	}

	public void attachToPane(final Pane parent) {
		attachEventHandling();
		for (final Target target : targets) {
			if (target.getScreenRepresentation() != null) {
				parent.getChildren().add(target.getScreenRepresentation());
			}
			if (target.getTransition() != null) {
				target.getTransition().play();
			}
		}
		this.parent = parent;
	}

	public void scoreEvent(final MouseEvent event) {
		final Node node = (Node) event.getSource();
		for (final Target target : targets) {
			if (target.getScreenRepresentation() != null && target.getScreenRepresentation().equals(node)) {
				target.setScored(true);
				score.scoreTarget(target);
				removeFromScene(target);
				// TODO >>>
				// if (advanceAheadOfTime()) {
				// score.scoreBonus();
				// }
				// TODO <<<
				event.consume();
			}
		}
		finishEventHandling();
	}

	public void timeoutEvent(final ActionEvent event) {
		final Transition transition = (Transition) event.getSource();
		for (final Target target : targets) {
			if (target.getTransition() != null && target.getTransition().equals(transition)) {
				event.consume();
			}
		}
		finishEventHandling();
	}

	private void attachEventHandling() {
		for (final Target target : targets) {
			if (target.getScreenRepresentation() != null) {
				target.getScreenRepresentation().setOnMousePressed((event) -> scoreEvent(event));
			}
			if (target.getTransition() != null) {
				target.getTransition().setOnFinished((event) -> timeoutEvent(event));
			}
		}
	}

	private void finishEventHandling() {
		if (finishEvent == null) {
			if (score.isEpicFail()) {
				finishEvent = new FinishEvent(FinishEvent.GAME_OVER);
			} else if (passivityKills()) {
				finishEvent = new FinishEvent(FinishEvent.PASSIVITY_KILLS);
			} else if (advanceAheadOfTime() || qualifiedForNextLevel()) {
				finishEvent = new FinishEvent(FinishEvent.LEVEL_UP);
			}
			if (parent != null && finishEvent != null) {
				parent.fireEvent(finishEvent);
			}
		}
	}

	private void removeFromScene(final Target targetToRemoveFromScene) {
		if (targetToRemoveFromScene.getScreenRepresentation() != null) {
			final Pane parent = (Pane) targetToRemoveFromScene.getScreenRepresentation().getParent();
			if (parent != null) {
				parent.getChildren().remove(targetToRemoveFromScene.getScreenRepresentation());
			}
		}
		if (targetToRemoveFromScene.getTransition() != null) {
			targetToRemoveFromScene.getTransition().stop();
		}
	}

	private boolean allFriendliesScored() {
		// TODO >>>
		// for (final Target target : targets) {
		// if (!target.isDangerous() && !target.isScored()) {
		// return false;
		// }
		// }
		// return true;
		// TODO <<<
		return false;
	}

	private boolean allTimedOut() {
		// TODO >>>
		// for (final Target target : targets) {
		// if (!target.isTimedOut()) {
		// return false;
		// }
		// }
		// return true;
		// TODO <<<
		return false;
	}

	private boolean scoredAtLeastOnce() {
		// TODO >>>
		// for (final Target target : targets) {
		// if (target.isScored()) {
		// return true;
		// }
		// }
		// return false;
		// TODO <<<
		return true;
	}

	private boolean advanceAheadOfTime() {
		// TODO >>>
		// return allFriendliesScored();
		// TODO <<<
		return false;
	}

	private boolean passivityKills() {
		// TODO >>>
		// return allTimedOut() && !scoredAtLeastOnce();
		// TODO <<<
		return false;
	}

	private boolean qualifiedForNextLevel() {
		// TODO >>>
		// return scoredAtLeastOnce() && allTimedOut();
		// TODO <<<
		return false;
	}
}
