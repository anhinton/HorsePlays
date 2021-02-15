library(tidyverse)

title = "Long Fingers"

textSource = "longfingers.txt"
output = gsub(pattern = "[.].+$", replacement = ".xml", x = textSource)
textScript = read_lines(file = textSource)

## remove blank lines
textScript = textScript[-grep(pattern = "^$", x = textScript)]

## remove lines with no character dialog
textScript = textScript[grepl(pattern = "^.+: ", x = textScript)]

## escape special XML characters
textScript = gsub(pattern = "<", replacement = "&lt;", x = textScript)
textScript = gsub(pattern = ">", replacement = "&gt;", x = textScript)

## remove stage directions
textScript = gsub(pattern = "\\(.+\\)", replacement = "", x = textScript)
## trim white space
textScript = gsub(pattern = "[[:space:]]{2,}", replacement = " ", 
                  x = textScript)

## split character names and line
characterLineSplit.list = strsplit(x = textScript, split = ": ")

characterNames = sapply(X = characterLineSplit.list,
                        FUN = function(x) x[1])
characterNames = toupper(characterNames)

scriptLines = sapply(X = characterLineSplit.list,
                     FUN = function(x) x[2])

startKnot = 
  c("<knot id = \"start\">",
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
  paste0("<playscript title=\"", title, "\">"),
  characterNode,
  "",
  startKnot,
  "",
  bombKnot,
  "</playscript>")

write_lines(x = playscript, path = output)
