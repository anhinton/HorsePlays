library(tidyverse)

textScript = read_lines(file = "awholebigsuckingthing.txt")

## remove blank lines
textScript = textScript[-grep(pattern = "^$", x = textScript)]

## remove stage directions
textScript = gsub(pattern = "\\(.+\\)", replacement = "", x = textScript)
## trim white space
textScript = gsub(pattern = "[[:space:]]{2,}", replacement = " ", 
                  x = textScript)
## remove stage direction lines
textScript = textScript[grep(pattern = "^(Buffy|Giles):", x = textScript)]

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

playscript = c(
  "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>",
  "<playscript title=\"A Whole Big Sucking Thing\">",
  characterNode,
  startKnot,
  "</playscript>")

write_lines(x = playscript, path = "awholebigsuckingthing.xml")
