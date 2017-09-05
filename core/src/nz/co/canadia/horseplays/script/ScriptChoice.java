package nz.co.canadia.horseplays.script;

/**
 * A dialog choice from a PlayScript
 */

public class ScriptChoice {
    private String actor;
    private String text;
    private String divert;
    private int bomb;

    ScriptChoice(String actor, String text, String divert, int bomb) {
        this.actor = actor;
        this.text = text;
        this.divert = divert;
        this.bomb = bomb;
    }

    public String getText() {
        return text;
    }

    public String getDivert() {
        return divert;
    }

    public int getBomb() {
        return bomb;
    }

    public String getActor() {
        return actor;
    }
}
