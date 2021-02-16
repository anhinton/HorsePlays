# todo.md

+ Prepare for release
    - ~~Set launch version~~
        - ~~Project build.gradle~~
        - ~~Android build.gradle~~
        - ~~iOS robovm.properties~~
        - ~~Add release tag to repo~~
    - Package for release
        - ~~Desktop~~
            - ~~jar file~~
            - ~~Windows packed~~
            - ~~Linux packed~~
        - Android APK
            - Problems with signed release, so check this works via Google Play
            - Use debug APK for itch.io because this seems to install fine
        - ~~Html directory~~
        - iOS IPA
    - https://horseplays.canadia.co.nz/
        - Set up webserver
        - Create site content
    - itch.io
    - Google Play Store
    - Apple App Store
    - Blog post
        - Website
        - Twitter
        - Ko-fi.com
        - Patreon.com
  
## Done

+ ~~Do something smart to make fonts look good on different device sizes~~
    - ~~Create a FontLoader interface~~
    - ~~Implement for Desktop, Html, Ios~~
    - ~~Implement for Android using `Gdx freetype` extension~~
    - ~~Set Ui to use full screen size~~
    - ~~Adjust menu icon size depending on screen size~~
    - ~~Actually properly implement for Ios when I'm on a Mac and I've already done the Android
      screen size work~~
        - ~~This needs testing~~
    
+ ~~Create App icons~~
    - ~~Desktop~~
    - ~~Android~~
    - ~~Html~~
    - ~~Ios~~
    
+ ~~Add music and sound effects~~
    - ~~Write music~~
    - ~~Record music~~
    - ~~Mix music~~
    - ~~Add music to game (play on load except Web, then play on click)~~
    - ~~Cough sound effect for low bomb choice~~
    - ~~"Ooooooooh" sound effect for high bomb choice~~
    
+ ~~Pack textures with TexturePacker~~
    
+ ~~Add bomb visual prompts~~
    - ~~Screen shake on bomb~~
    - ~~"You win/lose" depending on outcome~~

+ ~~Fix UI button widths on mobile devices - not calculated using uiWidth/Height values~~
    
+ ~~Fix credits UI width - not filling screen on iOS~~

+ ~~Add Sound Volume setting~~
        
+ ~~In-game menu to quit on mobile~~
    - ~~Quit~~
    - ~~Volume control can just happen in the Menu - we have autosave after all!~~
    - ~~Sort out menu image~~  

+ ~~Make a Title screen menu~~
    - ~~'Continue' button when a play has autosaved~~
    - ~~List of plays under 'New'~~
    - ~~Volume settings~~
    - ~~Make a credits screen~~
        - ~~Make sure all characters in credits are available in font.
          Two things were preventing the character 'á' from displaying:~~
            - ~~On Windows `credits.txt` was not being read in as UTF-8~~
            - ~~In general, the font used did not have any character beyond the standard 26-letter
              capital and lowercase letters. Now I have a full "extended" font.~~          

+ ~~Write a "save progress" function that keeps track of which knot you are
  up to in a PlayScript~~
    - ~~NB: Not which line in a knot. Start of knot is good enough for me.~~
    - ~~Saves out when returning to title menu~~
    - ~~If one exists when in title menu present Continue and New buttons~~
    - ~~Otherwise just New Button~~
    - ~~Clears progress when a PlayScript completes~~
    
+ ~~Fix play titles on start of play (make sure it fits on screen)~~

+ ~~Add numbers to start of choice to match keyboard selection~~

+ ~~Fix text wrap on long script choice~~

+ ~~write a play~~
    - ~~Then write another (it's horse *plays*, guy)~~
    - ~~Scully meets Mulder~~
    - ~~Buffy and Giles discuss being Buffy being the Slayer~~
    - ~~Leaving it at two plays because I can't find any other scenes I like enough~~

+ ~~Fix CLOSING triggering !animating when bomb ending plays (based on
  whether a curtain or horse is currently moving)~~

+ ~~Use numkeys 1, 2, 3, etc to select choices~~

+ ~~Move play progression logic from TheatreScreen.touchUp() to somewhere
  in Theatre()?~~
    - ~~Also handle PlayScript and SpeechUI in Theatre~~

+ ~~allow normal lines to be selected by touching anywhere, not just on
  button~~

  + ~~Check if everything still works. It's been since 2017! Does this still
    make sense? I need to get re-familiar.
    I guess I'm as familiar as I'll ever be? Let's just make this game!~~
      - ~~Switch input detection from Polling to Event Handling~~
          - ~~TitleScreen~~
          - ~~TheatreScreen~~
      - ~~Bump to libGDX version 1.9.13~~
      - ~~add iOS module~~  
      - ~~Switch to LWJGL3 for Desktop~~

  + ~~add character name to dialogue~~
      - ~~first line in line button is '<character>:'~~
      - ~~floating about choices is '<character>:'~~
      - ~~declare character names in playscript XML: tag "player"~~
      - ~~refactor to be "character"~~
      - ~~centre choice buttons and character name~~
      - ~~padding on buttons on right and bottom only~~

  + ~~make choice buttons only select choice when touchUp is over the button~~
  
  + ~~write stage animation for bomb threshold~~
      - ~~other horse walks off~~
      - ~~then player horse walks off~~
      - ~~then curtains close~~
      
  + ~~write logic for finished PlayScript into Theatre~~
      - ~~plays end animation~~
      - ~~sets currentTheatreScene to FINISHED~~
      