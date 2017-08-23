package nz.co.canadia.horseplays.script;

/**
 * A single line of a script
 */

public class ScriptLine {
    private String actor;
    private String text;

    ScriptLine(String actor, String text) {
        this.actor = actor;
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
