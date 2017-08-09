package nz.co.canadia.horseplays.script;

import com.badlogic.gdx.utils.Array;

/**
 * A single line of dialog from a script
 */

public class ScriptKnot {
    Array<ScriptLine> scriptLines;
    Array<ScriptChoice> scriptChoices;
    ScriptLine currentScriptLine;
    String id;
    String divert;

    ScriptKnot(Array<ScriptLine> scriptLines, Array<ScriptChoice> scriptChoices, String id, String divert) {
        this.scriptLines = scriptLines;
        currentScriptLine = scriptLines.first();
        this.scriptChoices = scriptChoices;
        this.id = id;
        this.divert = divert;
    }
}
