# Memory App

#### Created in June 2019

The app implements a simple game of Memory where the player has to find matching pairs. There are shuffled cards laying face down on the table. The task is to collect as many matching pairs of cards as possible. 


<p> &nbsp </p>

## How it works

The player can adjust the following settings:
<p> &nbsp </p>

* Difficulty:
   * easy (6 very different cards)
   * intermediate (12 cards, some similar, some different)
   * difficult (16 quite similar cards)
   <p> &nbsp </p>
   
* Topics:
   * fruits
   * animals
   * transport
   <p> &nbsp </p>
   
* Playermode:
   * singleplayer (you play against a timer, the time itself depends on the difficulty)
   * multiplayer (you take turns playing against anonther player, when you find a matching pair you can try again)
   
   <p> &nbsp </p>
   
Additionally there is a highscore for singleplayer, which is saved in a SharedPreference, you get 3 points for a match, otherwise you lose a point. The top three scores are saved

<p> &nbsp </p>

## A few impressions

### MainActivity in singleplayermode:
<img src="/Screenshots/MainActivity.png" height="400">

<p> &nbsp </p>

### MainActivity in multiplayermode:
<img src="/Screenshots/MainActivity_Multiplayer.png" height="400">

<p> &nbsp </p>

### Settings:
<img src="/Screenshots/Settings.png" height="400">

<p> &nbsp </p>

### Highscore:
<img src="/Screenshots/Highscore.png" height="400">


### StartScreen:
<img src="/Screenshots/StartScreen.png" height="400">
