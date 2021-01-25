# todo.md
    
  + Fix CLOSING triggering !animating when bomb ending plays (based on
    whether a curtain or horse is currently moving)
    
  + Do something smart to make fonts look good on different device size
    (abandon if I don't care about iOS)
    
  + write a play
      - Then write another (it's horse *plays*, guy)
      - Scully meets Mulder
      - Dawson and Joey talk about sex stuff
      - Buffy and Angel talk about ...?
      - Wayne and Cassandra/Garth ...?
    
  + Write a "save progress" function that keeps track of which knot you are 
    up to in a PlayScript
      - NB: Not which line in a knot. Start of knot is good enough for me.
      - Saves out when returning to title menu
      - If one exists when in title menu present Resume and Start buttons
      - Otherwise just Start Button
      - Clears progess when a PlayScript completes
  
  + write music
  
  ## Done

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
      