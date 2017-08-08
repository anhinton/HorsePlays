package nz.co.canadia.horseplays.script;

/**
 * A single line of dialog from a script
 */

public class ScriptKnot {
    String actor;
    String scriptLine;

    ScriptKnot(String actor, String scriptLine) {
        this.actor = actor;
        this.scriptLine = scriptLine;
    }
}
