library(tidyverse)

textSource = "nobigtimecontest.txt"
output = gsub(pattern = "[.].+$", replacement = ".xml", x = textSource)
textScript = read_lines(file = textSource)

## escape special XML characters
textScript = gsub(pattern = "<", replacement = "&lt;", x = textScript)
textScript = gsub(pattern = ">", replacement = "&gt;", x = textScript)

## split character names and line
characterLineSplit.list = strsplit(x = textScript, split = ": ")

characterNames = sapply(X = characterLineSplit.list,
                        FUN = function(x) x[1])
characterNames = toupper(characterNames)

scriptLines = sapply(X = characterLineSplit.list,
                     FUN = function(x) x[2])

startKnot = 
  c("<knot id=\"start\">",
    paste0("<line character=\"", characterNames, "\">", scriptLines,
       "</line>"),
    "</knot>")

characterNode = c(
  "<characters>",
  paste0("<character name=\"", unique(characterNames), "\"/>"),
  "</characters>")

bombKnot = 
  c("<knot id=\"bomb\" threshold=\"100\">",
    paste0("<line character=\"", characterNames[1], "\">Bugger this.</line>"),
    "</knot>")

playscript = c(
  "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>",
  "<playscript title=\"This is no big time contest\">",
  characterNode,
  startKnot,
  bombKnot,
  "</playscript>")

write_lines(x = playscript, path = output)
