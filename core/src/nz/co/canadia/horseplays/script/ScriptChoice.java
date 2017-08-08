package nz.co.canadia.horseplays.script;

import com.badlogic.gdx.utils.Array;

/**
 * A dialog choice from a Script
 */

public class ScriptChoice {
    String actor;
    Array<String> choices;

    ScriptChoice (String actor, Array<String> choices) {
        this.actor = actor;
        this.choices = choices;
    }
}
