# todo.md

  + Check if everything still works. It's been since 2017! Does this still
    make sense? I need to get re-familiar.
      - ~~Switch input detection from Polling to Event Handling~~
          - ~~TitleScreen~~
          - ~~TheatreScreen~~
      - ~~Bump to libGDX version 1.9.13~~
      - Switch to LWJGL3 for Desktop
      - ???

  + add character name to dialogue
      - ~~first line in line button is '<character>:'~~
      - ~~floating about choices is '<character>:'~~
      - ~~declare character names in playscript XML: tag "player"~~
      - ~~refactor to be "character"~~
      - ~~centre choice buttons and character name~~
      - ~~padding on buttons on right and bottom only~~
      
  + allow normal lines to be selected by touching anywhere, not just on
    button
    
  + Move play progression logic from TheatreScreen.touchUp() to somewhere
    in Theatre()?
    
  + Click anywhere to speed through "action" on TheatreScreen
    
  + Do something smart to make fonts look good on different device size
    (abandon if I don't care about iOS)  
    
  + add iOS module  
    
  + write a play
      - Then write another (it's horse *plays*, guy)
  
  + write music
  
  ## Done

  + ~~make choice buttons only select choice when touchUp is over the button~~
  
  + ~~write stage animation for bomb threshold~~
      - ~~other horse walks off~~
      - ~~then player horse walks off~~
      - ~~then curtains close~~
      
  + ~~write logic for finished PlayScript into Theatre~~
      - ~~plays end animation~~
      - ~~sets currentTheatreScene to FINISHED~~
      