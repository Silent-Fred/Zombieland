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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * @author Michael Kühweg
 */
public class Zombieland extends Application {

	private Level level;
	private RandomPathTransition randomPathTransition;

	private final Pane root = new StackPane();

	private Score score;
	private Pane scoreDisplay;

	private List<Image> dangerousImages;
	private List<Image> friendlyImages;

	private ResourceBundle dictionary;

	@Override
	public void start(final Stage primaryStage) {
		((StackPane) root).setAlignment(Pos.BOTTOM_LEFT);
		initialiseGame();
		primaryStage.setTitle(dictionary.getString("labelTitle"));
		primaryStage.setResizable(false);
		primaryStage.setScene(createAndSetUpScene());
		primaryStage.show();

		addLevelEventHandling(root);

		score.reset();
		help();
	}

	@Override
	public final void init() throws Exception {
		super.init();
		initFonts();
		dictionary = ResourceBundle.getBundle("dictionary");
		dangerousImages = new ArrayList<>(importDangerousImages());
		friendlyImages = new ArrayList<>(importFriendlyImages());
	}

	public static void main(final String[] args) {
		launch(args);
	}

	private void initFonts() {
		Font.loadFont(getClass().getResource("/resources/fonts/ZOMBIE.TTF").toExternalForm(), 12);
	}

	private void addLevelEventHandling(final Pane root) {
		root.addEventHandler(FinishEvent.LEVEL_UP, (event) -> {
			event.consume();
			nextLevel();
		});
		root.addEventHandler(FinishEvent.GAME_OVER, (event) -> {
			event.consume();
			gameOverTooManyDangerousObjects();
		});
		root.addEventHandler(FinishEvent.PASSIVITY_KILLS, (event) -> {
			event.consume();
			gameOverTooPassive();
		});
		root.getScene().addEventHandler(KeyEvent.KEY_PRESSED, (event) -> {
			if (event.getCode() == KeyCode.SPACE) {
				event.consume();
				newGame();
			} else if (event.getCode() == KeyCode.H) {
				event.consume();
				help();
			}
		});
	}

	private void initialiseGame() {
		level = new Level();
		randomPathTransition = new RandomPathTransition(level);
		score = new Score(level);
	}

	private Scene createAndSetUpScene() {
		final BackgroundImage background = new BackgroundImage(backgroundImage(), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		final Rectangle2D size = calculateSize(background);
		final Scene scene = new Scene(root, size.getWidth(), size.getHeight());
		scene.setCursor(Cursor.CROSSHAIR);
		root.setBackground(new Background(background));
		root.getStylesheets().add(getClass().getResource("/resources/css/zombie.css").toExternalForm());
		createScoreDisplay();
		return scene;
	}

	private void createScoreDisplay() {
		scoreDisplay = new VBox();
		scoreDisplay.setPadding(new Insets(12.0));
		((VBox) scoreDisplay).setAlignment(Pos.TOP_LEFT);
		final Label scoreTitle = new Label(dictionary.getString("labelScore"));
		scoreTitle.setId("scoreTitle");
		final Label scoreValue = new Label();
		scoreValue.setId("scoreValue");
		scoreValue.textProperty().bind(score.getObjectCountProperty());
		final Label dangerTitle = new Label(dictionary.getString("labelDangerHitsLeft"));
		dangerTitle.setId("dangerTitle");
		final Label dangerCountdown = new Label();
		dangerCountdown.setId("dangerCountdown");
		dangerCountdown.textProperty().bind(score.getDangerCountdownProperty());
		scoreDisplay.getChildren().addAll(scoreTitle, scoreValue, dangerTitle, dangerCountdown);
	}

	private Pane createDangerWarning() {
		final VBox objectsAndWarning = new VBox();
		objectsAndWarning.setAlignment(Pos.BOTTOM_CENTER);
		final HBox dangerousObjects = new HBox();
		dangerousObjects.setAlignment(Pos.BOTTOM_CENTER);
		dangerousObjects.setSpacing(12.0f);
		for (final Image image : dangerousImages) {
			dangerousObjects.getChildren().add(new ImageView(image));
		}
		final Label warningLabel = new Label(dictionary.getString("labelDanger"));
		warningLabel.setId("dangerWarning");
		objectsAndWarning.getChildren().addAll(dangerousObjects, warningLabel);
		return objectsAndWarning;
	}

	private void newGame() {
		level.reset();
		score.reset();
		startLevel();
	}

	private void nextLevel() {
		level.increase();
		startLevel();
	}

	private void startLevel() {
		root.getChildren().clear();
		root.getChildren().add(scoreDisplay);
		final Pane warning = createDangerWarning();
		StackPane.setAlignment(warning, Pos.BOTTOM_CENTER);
		root.getChildren().add(warning);
		final LevelTargets levelTargets = new LevelTargets(randomTargets(), score);
		levelTargets.attachToPane(root);
	}

	private void gameOverTooManyDangerousObjects() {
		root.getChildren().clear();
		root.getChildren().addAll(scoreDisplay, gameOver("labelGameOverZombie"));
	}

	private void gameOverTooPassive() {
		root.getChildren().clear();
		root.getChildren().addAll(scoreDisplay, gameOver("labelGameOverPassivityKills"));
	}

	private void help() {
		root.getChildren().clear();
		root.getChildren().addAll(scoreDisplay, helpDescription());
	}

	private Pane gameOver(final String resourceKey) {
		final VBox gameOverBox = new VBox();
		gameOverBox.setSpacing(12.0f);
		gameOverBox.setAlignment(Pos.CENTER);
		final Label gameOver = new Label(dictionary.getString(resourceKey));
		gameOver.setId("gameOver");
		gameOver.setTextAlignment(TextAlignment.CENTER);
		final Label newGame = new Label(dictionary.getString("labelNewGame"));
		newGame.setId("newGame");
		newGame.setTextAlignment(TextAlignment.CENTER);
		gameOverBox.getChildren().addAll(gameOver, newGame);
		StackPane.setAlignment(gameOverBox, Pos.CENTER);
		return gameOverBox;
	}

	private Pane helpDescription() {
		final VBox helpBox = new VBox();
		helpBox.setSpacing(12.0f);
		helpBox.setAlignment(Pos.CENTER);
		final Label help = new Label(helpTextFormatted());
		help.setId("help");
		help.setTextAlignment(TextAlignment.CENTER);
		final Label newGame = new Label(dictionary.getString("labelNewGame"));
		newGame.setId("newGame");
		newGame.setTextAlignment(TextAlignment.CENTER);
		helpBox.getChildren().addAll(help, newGame);
		StackPane.setAlignment(helpBox, Pos.CENTER);
		return helpBox;
	}

	private String helpTextFormatted() {
		return MessageFormat.format(dictionary.getString("labelHelp"),
				String.valueOf(new GregorianCalendar().get(Calendar.YEAR) + 2));
	}

	private Collection<Target> randomTargets() {
		final Collection<Target> targets = new ArrayList<>();
		int dangerousImageIndex = 0;
		int friendlyImageIndex = 0;
		final long howManyDangerousObjects = Math
				.round(level.getObjectsAvailable() * level.getPercentageOfDangerousObjects());
		final long dangerousSteps = level.getObjectsAvailable() / howManyDangerousObjects;
		for (int objectCount = 0; objectCount < level.getObjectsAvailable(); objectCount++) {
			final boolean dangerousObject = objectCount % dangerousSteps == 0;
			final ImageView nodeForPathTransition = new ImageView(
					dangerousObject ? dangerousImages.get(dangerousImageIndex % dangerousImages.size())
							: friendlyImages.get(friendlyImageIndex % friendlyImages.size()));
			nodeForPathTransition.setTranslateX(root.getScene().getWidth());
			nodeForPathTransition.setTranslateY(root.getScene().getHeight());
			if (dangerousObject) {
				dangerousImageIndex++;
			} else {
				friendlyImageIndex++;
			}
			final Target target = dangerousObject ? new DangerousTarget() : new FriendlyTarget();
			target.setScreenRepresentation(nodeForPathTransition);
			target.setTransition(randomPathTransition.nextPathTransition(root.getScene(), nodeForPathTransition));
			targets.add(target);
		}
		return targets;
	}

	private Image backgroundImage() {
		return new Image(getClass().getResourceAsStream("/resources/images/background.jpg"));
	}

	private Collection<Image> importDangerousImages() {
		return importObjectImages("dangerous");
	}

	private Collection<Image> importFriendlyImages() {
		return importObjectImages("friendly");
	}

	private Collection<Image> importObjectImages(final String folder) {
		final Collection<Image> objectImages = new ArrayList<>(4);
		int index = 1;
		try {
			while (true) {
				objectImages.add(new Image(getClass()
						.getResourceAsStream("/resources/images/objects/" + folder + "/image_" + index++ + ".png")));
			}
		} catch (final NullPointerException e) {
		}
		return objectImages;
	}

	private Rectangle2D calculateSize(final BackgroundImage backgroundImage) {
		final double width = Math.min(Screen.getPrimary().getVisualBounds().getWidth(),
				backgroundImage.getImage().getWidth());
		final double height = Math.min(Screen.getPrimary().getVisualBounds().getHeight(),
				backgroundImage.getImage().getHeight());
		return calculateSizeWithMargins(new Rectangle2D(0, 0, width, height));
	}

	private Rectangle2D calculateSizeWithMargins(final Rectangle2D bounds) {
		final double horizontalMargin = calculateHorizontalMargin(bounds);
		final double verticalMargin = calculateVerticalMargin(bounds);
		return new Rectangle2D(bounds.getMinX() + horizontalMargin / 2, bounds.getMinY() + verticalMargin,
				bounds.getWidth() - horizontalMargin, bounds.getHeight() - verticalMargin);
	}

	private double calculateHorizontalMargin(final Rectangle2D bounds) {
		final double preferredMargin = 64;
		if (preferredMargin * 2 >= bounds.getWidth()) {
			return 10;
		}
		return preferredMargin;
	}

	private double calculateVerticalMargin(final Rectangle2D bounds) {
		final double preferredMargin = 64;
		if (preferredMargin * 2 >= bounds.getWidth()) {
			return 10;
		}
		return preferredMargin;
	}
}