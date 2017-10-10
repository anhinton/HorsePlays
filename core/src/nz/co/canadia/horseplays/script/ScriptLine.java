package nz.co.canadia.horseplays.script;

/**
 * A single line of a script
 */

public class ScriptLine {
    private String character;
    private String text;

    ScriptLine(String character, String text) {
        this.character = character;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getCharacter() {
        return character;
    }
}
