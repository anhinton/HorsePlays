library(tidyverse)

textScript = read_lines(file = "fbimostunwanted.txt")

## remove blank lines
textScript = textScript[-grep(pattern = "^$", x = textScript)]

## remove stage directions
textScript = textScript[-grep(pattern = "^\\(.+\\)$", x = textScript)]

characterNames = textScript[c(TRUE, FALSE)]
characterNames = gsub(pattern = ":$", replacement = "", x = characterNames)

scriptLines = textScript[c(FALSE, TRUE)]

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
  "<playscript title=\"The FBI's Most Unwanted\">",
  characterNode,
  startKnot,
  "</playscript>")

write_lines(x = playscript, path = "fbimostunwanted.xml")
