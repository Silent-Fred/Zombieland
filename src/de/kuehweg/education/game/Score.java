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

import javafx.beans.property.SimpleStringProperty;

/**
 * @author Michael Kühweg
 */
public class Score {

	private final Level level;

	private int objectCount;
	private int dangerousObjectsHit;

	private boolean epicFail;

	private final SimpleStringProperty objectCountProperty = new SimpleStringProperty();
	private final SimpleStringProperty dangerCountdownProperty = new SimpleStringProperty();

	public Score(final Level level) {
		this.level = level;
	}

	public void scoreTarget(final Target target) {
		if (!epicFail) {
			if (!target.isDangerous()) {
				objectCount++;
				objectCountToProperty();
			} else {
				dangerousObjectsHit++;
				dangerCountdownToProperty();
			}
		}
		epicFail = dangerousObjectsHit >= level.getDangerousObjectsTolerance();
	}

	// TODO >>>
	// public void scoreBonus() {
	// objectCount += level.getBonus();
	// objectCountToProperty();
	// }
	// TODO <<<

	public void reset() {
		objectCount = 0;
		objectCountToProperty();
		dangerousObjectsHit = 0;
		dangerCountdownToProperty();
		epicFail = false;
	}

	private void objectCountToProperty() {
		objectCountProperty.setValue(Integer.toString(objectCount));
	}

	private void dangerCountdownToProperty() {
		dangerCountdownProperty.set("" + (level.getDangerousObjectsTolerance() - dangerousObjectsHit));
	}

	public SimpleStringProperty getObjectCountProperty() {
		return objectCountProperty;
	}

	public SimpleStringProperty getDangerCountdownProperty() {
		return dangerCountdownProperty;
	}

	public boolean isEpicFail() {
		return epicFail;
	}
}
