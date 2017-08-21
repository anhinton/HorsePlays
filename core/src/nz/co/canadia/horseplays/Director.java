package nz.co.canadia.horseplays;

import com.badlogic.gdx.utils.Array;

import nz.co.canadia.horseplays.script.PlayScript;
import nz.co.canadia.horseplays.script.ScriptKnot;

/**
 * The director takes the PlayScript and passes directions to the Theatre.
 */

public class Director {
    private PlayScript playScript;
    private ScriptKnot currentKnot;
    private Array<Horse> horses;
    private Theatre theatre;

    public Director(PlayScript playScript, Theatre theatre) {
        this.playScript = playScript;
        this.theatre = theatre;
        horses = theatre.getHorses();
        currentKnot = playScript.getCurrentKnot();
    }
}
