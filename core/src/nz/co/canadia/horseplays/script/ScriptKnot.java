package nz.co.canadia.horseplays.script;

import com.badlogic.gdx.utils.Array;

/**
 * A single line of dialog from a script
 */

class ScriptKnot {
    private Array<ScriptLine> scriptLines;
    private Array<ScriptChoice> scriptChoices;
    private int currentScriptLine;
    private String divert;

    ScriptKnot(Array<ScriptLine> scriptLines, Array<ScriptChoice> scriptChoices, String id,
               String divert) {
        this.scriptLines = scriptLines;
        currentScriptLine = 0;
        this.scriptChoices = scriptChoices;
        this.divert = divert;
    }

    ScriptLine getCurrentScriptLine() {
        return scriptLines.get(currentScriptLine);
    }

    void nextLine() {
        currentScriptLine += 1;
    }

    boolean hasLine() {
        return currentScriptLine < scriptLines.size;
    }

    boolean hasChoice() {
        return scriptChoices.size > 0;
    }

    Array<ScriptChoice> getChoices() {
        return scriptChoices;
    }

    String getDivert() {
        return divert;
    }
}
