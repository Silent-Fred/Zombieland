# Zombieland

This project is intended for use in an introductory course on Java.
At the end of the course, students should be able to add attributes
to classes and implement simple methods. By extending an already
existing project instead of writing simple classes completely on their own, the
final program should - hopefully - give them a sense of accomplishment in a playful manner.

The project contains a full implementation with those parts that students should
add or modify, marked and commented out like:

`// TODO >>>`  
`// return somethingWeWillImplement();`  
`// TODO <<<`  

To see how things are meant to work in the full version of the game,
you can use these bits of code. But be sure to
remove them completely before handing over the code to the students.
The remaining parts still constitute an executable application with only some
functionality missing.

Additionally, you might "destroy" some parts, adding syntax errors, to request students
to correct errors in a first step (see below).

The intended exercises are as follows:

1. Setup the project in the IDE. Correct all syntax errors and run the program. 
2. Implement the method `allTimedOut` in the class `LevelTargets` to check if
_all targets_ have come to the end of their animation.
3. Implement the method `scoredAtLeastOnce` in the class `LevelTargets` to check if 
_at least one target_ has been scored.
4. Implement the method `qualifiedForNextLevel` in the class `LevelTargets` to check if
_at least one object that is not dangerous_ has been scored and 
_all targets_ have come to the end of their animation.
(i.e. allowing to proceed to the next level)
5. Implement the method `passivityKills` in the class `LevelTargets` to check if 
_no target_ has been scored and 
_all targets_ have come to the end of their animation.
(i.e. the level is finished without any successful user action)
6. Implement the method `allFriendliesScored` in the class `LevelTargets` to check if 
_all targets that are not dangerous_ have been scored.
7. Implement the method `advanceAheadOfTime` in the class `LevelTargets` to check if the level is finished by
having scored all targets, that are not dangerous.
8. Extend the class `Level` to provide a _bonus_. The _bonus_ should be five points on the
first level and increase by five for each level.
9. Add logic to add the _bonus_ in case the user advances to the next level by scoring all
relevant objects before all objects have come to the end of their animation. (i.e. in case
of `advanceAheadOfTime`)

The exercise to implement `LevelTargets.advanceAheadOfTime()` should foster discussion about code duplication
(if implemented separately), naming methods, wrapping logic, abstractions,... (if calling `allFriendliesScored`)

The several places where negation is used to work on objects that are _not dangerous_ should foster
discussion about maybe introducing a new method to the abstract class `Target`.

### You don't like zombies?

If you prefer to have a different story for your game, just change it:

* In `dictionary.properties` you can change screen labels and the storytelling.
* Change `/resources/images/background.jpg` to whatever background image you like.
* Drop the images for your preferred objects to click (or avoid) in the folders
 `/resources/images/objects/friendly/` or `/resources/images/objects/dangerous/` respectively.
 Name the image files `image_N.png`, where `N`
 are consecutive numbers starting from 1 **for each type**
* You may change the styles in `/resources/css/zombie.css`
* You may use a different font, dropped in at `/resources/fonts/`
* Changes to fonts or renaming the css file have to be reflected in
 `Zombieland.initFonts()` and `Zombieland.createAndSetupScene()`
 
 A note on images: Although it should speak for itself, images with a transparent background
 are highly recommended.

 Some ideas:
 
* _Sports theme_  
 Sports motif as a background and different types of balls as objects.
 (baseballs, basketballs, tennisballs, golfballs...) Could for example be categorized in
 typical European sports and typical American sports.
* _Nutritional theme_  
 Different foods, might be categorized in healthy and unhealthy. (actually the opposite
 of the original, politically incorrect story)
* _French Revolution theme_  
 Different portraits of people. The morbid way: Click those who lost their head.
 The more friendly way: Click those who played a role in the French Revolution.
 Adding portraits of George Washington, Elvis Presley, Albert Einstein,... as portraits
 that are meant _not_ to be clicked.
 