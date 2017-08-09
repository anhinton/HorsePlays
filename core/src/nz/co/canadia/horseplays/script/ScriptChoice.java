package nz.co.canadia.horseplays.script;

/**
 * A dialog choice from a Script
 */

public class ScriptChoice {
    String actor;
    String text;
    String divert;

    public ScriptChoice(String actor, String text, String divert) {
        this.actor = actor;
        this.text = text;
        this.divert = divert;
    }
}
