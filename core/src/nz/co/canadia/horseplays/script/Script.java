package nz.co.canadia.horseplays.script;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;

import java.io.IOException;

/**
 * The play script contains all the horse actor dialog and controls what happens on the Stage
 */

public class Script {
    Array<ScriptKnot> scriptKnots;
    ScriptKnot currentKnot;

    public Script() {

        XmlReader xmlReader = new XmlReader();
        XmlReader.Element root;
        Array<XmlReader.Element> knots;
        try {
            root = xmlReader.parse(Gdx.files.internal("scripts/script.xml"));
            knots = root.getChildrenByName("knot");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
